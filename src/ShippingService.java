import java.util.List;

public class ShippingService {
    // Base fee applied to all physical deliveries
    private static final double BASE_FEE = 50.0;
    // Additional fee applied per kilogram of total order weight
    private static final double FEE_PER_KG = 5.0;

    public double calculateShippingFees(List<Shippable> itemsToShip) {

        if (itemsToShip == null || itemsToShip.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;

        for (Shippable item : itemsToShip) {
            totalWeight += item.getWeight();
        }

        return BASE_FEE + (totalWeight * FEE_PER_KG);
    }
}