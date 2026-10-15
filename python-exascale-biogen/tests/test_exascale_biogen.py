from aethelgard_ai.engines import (
    ExascaleMoERouter,
    FederatedHomomorphicAggregator,
    CrisprGenomicAligner
)

def test_layer12_moe_router_selects_genomics_experts():
    router = ExascaleMoERouter()
    res = router.route_tokens_to_experts("GENOMICS", 512)
    assert "expert-crispr-04" in res["selected_experts"]
    assert res["tensor_parallel_degree"] == 8

def test_layer13_federated_aggregator_clips_outlier_gradients():
    agg = FederatedHomomorphicAggregator()
    res = agg.aggregate_clipped_gradients([0.5, 99.0, -99.0, 0.5], clip_norm=1.0)
    assert res["global_weight_delta"] == 0.25
    assert res["differential_privacy_guaranteed"] is True

def test_layer14_crispr_genomic_aligner_scores_guide_rna():
    aligner = CrisprGenomicAligner()
    res = aligner.evaluate_guide_rna("ATGCATGCATGC", "AUGCAUGCAUGC")
    assert res["exact_match"] is True
    assert res["gc_content_ratio"] == 0.5
    assert res["optimal_pam_window"] is True
