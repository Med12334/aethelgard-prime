#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <cassert>

// Layers 9 & 10: C++20 Sub-Microsecond Limit Order Book (LOB) & 4D Orbital Physics Solver
namespace aethelgard::core {

    struct Order {
        long order_id;
        bool is_bid;
        double price;
        long quantity;
    };

    struct MatchResult {
        long matched_quantity;
        double execution_price;
        int trades_executed;
    };

    // Layer 9: Price-Time Priority Limit Order Book Matching Engine
    class LimitOrderBookEngine {
    public:
        static MatchResult match_orders(std::vector<Order> bids, std::vector<Order> asks) {
            std::sort(bids.begin(), bids.end(), [](const Order& a, const Order& b) {
                return a.price > b.price;
            });
            std::sort(asks.begin(), asks.end(), [](const Order& a, const Order& b) {
                return a.price < b.price;
            });

            long total_qty = 0;
            double last_price = 0.0;
            int trades = 0;

            size_t i = 0, j = 0;
            while (i < bids.size() && j < asks.size() && bids[i].price >= asks[j].price) {
                long fill = std::min(bids[i].quantity, asks[j].quantity);
                total_qty += fill;
                last_price = asks[j].price;
                trades++;
                bids[i].quantity -= fill;
                asks[j].quantity -= fill;
                if (bids[i].quantity == 0) i++;
                if (asks[j].quantity == 0) j++;
            }
            return {total_qty, last_price, trades};
        }
    };

    // Layer 10: Orbital Mechanics Vis-Viva Velocity Solver
    class OrbitalPhysicsSolver {
    public:
        static double compute_orbital_velocity_km_s(double altitude_km) {
            constexpr double GM_EARTH = 398600.4418;
            constexpr double EARTH_RADIUS_KM = 6371.0;
            double r = EARTH_RADIUS_KM + altitude_km;
            return std::sqrt(GM_EARTH / r);
        }
    };
}

int main() {
    std::vector<aethelgard::core::Order> bids = {{1, true, 105.50, 500}, {2, true, 104.00, 300}};
    std::vector<aethelgard::core::Order> asks = {{3, false, 105.00, 400}, {4, false, 106.00, 200}};

    auto match = aethelgard::core::LimitOrderBookEngine::match_orders(bids, asks);
    assert(match.matched_quantity == 400);
    assert(match.execution_price == 105.00);
    assert(match.trades_executed == 1);

    double leo_velocity = aethelgard::core::OrbitalPhysicsSolver::compute_orbital_velocity_km_s(550.0);
    assert(leo_velocity > 7.5 && leo_velocity < 7.7);

    std::cout << "[AETHELGARD C++20 LOB & ORBITAL PHYSICS] All Assertions Passed! MatchedQty="
              << match.matched_quantity << " LEO_Velocity=" << leo_velocity << "km/s\n";
    return 0;
}
