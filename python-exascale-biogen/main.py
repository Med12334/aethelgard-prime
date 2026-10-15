from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
from aethelgard_ai.engines import (
    ExascaleMoERouter,
    FederatedHomomorphicAggregator,
    CrisprGenomicAligner
)

app = FastAPI(title="Aethelgard-Prime Exascale MoE, Federated ML & CRISPR Engine", version="1.0.0")

moe = ExascaleMoERouter()
fed = FederatedHomomorphicAggregator()
crispr = CrisprGenomicAligner()

class MoERequest(BaseModel):
    prompt_domain: str
    token_count: int

class FederatedRequest(BaseModel):
    gradients: List[float]
    clip_norm: float = 1.0

class CrisprRequest(BaseModel):
    target_dna: str
    guide_rna: str

@app.get("/health")
def health():
    return {"status": "UP", "layers": ["L12-MoE", "L13-Federated-ML", "L14-CRISPR-Genomics"]}

@app.post("/api/v1/moe/route")
def route_moe(req: MoERequest):
    return moe.route_tokens_to_experts(req.prompt_domain, req.token_count)

@app.post("/api/v1/federated/aggregate")
def aggregate_gradients(req: FederatedRequest):
    return fed.aggregate_clipped_gradients(req.gradients, req.clip_norm)

@app.post("/api/v1/genomics/align")
def align_crispr(req: CrisprRequest):
    return crispr.evaluate_guide_rna(req.target_dna, req.guide_rna)
