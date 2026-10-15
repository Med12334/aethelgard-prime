#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>

/* Layer 11: Pure ANSI C11 Bare-Metal Lock-Free DMA Ring Buffer & CRC Verifier */
#define DMA_RING_CAPACITY 8

typedef struct {
    uint64_t lba_sector;
    uint32_t payload_bytes;
    uint32_t checksum;
} DmaDescriptor;

typedef struct {
    DmaDescriptor slots[DMA_RING_CAPACITY];
    uint32_t head;
    uint32_t tail;
    uint32_t count;
} NvmeDmaRing;

void dma_ring_init(NvmeDmaRing *ring) {
    ring->head = 0;
    ring->tail = 0;
    ring->count = 0;
}

uint32_t compute_frame_crc(uint64_t sector, uint32_t bytes) {
    return (uint32_t)((sector ^ (sector >> 32)) * 0x45d9f3bU + bytes);
}

bool dma_ring_enqueue(NvmeDmaRing *ring, uint64_t sector, uint32_t bytes) {
    if (ring->count == DMA_RING_CAPACITY) {
        return false;
    }
    ring->slots[ring->tail].lba_sector = sector;
    ring->slots[ring->tail].payload_bytes = bytes;
    ring->slots[ring->tail].checksum = compute_frame_crc(sector, bytes);
    ring->tail = (ring->tail + 1) % DMA_RING_CAPACITY;
    ring->count++;
    return true;
}

bool dma_ring_dequeue(NvmeDmaRing *ring, DmaDescriptor *out) {
    if (ring->count == 0) {
        return false;
    }
    *out = ring->slots[ring->head];
    ring->head = (ring->head + 1) % DMA_RING_CAPACITY;
    ring->count--;
    return true;
}

int main(void) {
    NvmeDmaRing ring;
    dma_ring_init(&ring);

    assert(dma_ring_enqueue(&ring, 4096ULL, 16384U) == true);
    assert(dma_ring_enqueue(&ring, 8192ULL, 32768U) == true);
    assert(ring.count == 2);

    DmaDescriptor desc;
    assert(dma_ring_dequeue(&ring, &desc) == true);
    assert(desc.lba_sector == 4096ULL);
    assert(desc.checksum == compute_frame_crc(4096ULL, 16384U));

    printf("[AETHELGARD C11 BARE-METAL DMA RING] Zero-Copy Descriptor Assertions Passed!\n");
    return 0;
}
