package io.aethelgard.prime.rtgs.controller;

import io.aethelgard.prime.contracts.SovereignSettlementEvent;
import io.aethelgard.prime.rtgs.service.CbdcRtgsClearingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rtgs")
@CrossOrigin(origins = "*")
public class CbdcRtgsController {

    private final CbdcRtgsClearingService clearingService;

    public CbdcRtgsController(CbdcRtgsClearingService clearingService) {
        this.clearingService = clearingService;
    }

    public record RtgsRequest(String centralBankBic, String counterpartyBic, String currencyIso,
                              long amountMinorUnits, String pqcDilithiumSig) {}

    @PostMapping("/settle")
    public SovereignSettlementEvent settle(@RequestBody RtgsRequest req) throws Exception {
        return clearingService.executeRtgsSettlement(
            req.centralBankBic(), req.counterpartyBic(), req.currencyIso(),
            req.amountMinorUnits(), req.pqcDilithiumSig()
        );
    }
}
