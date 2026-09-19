import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cartItems;

    public Cart() {
        this.cartItems = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than 0.");
            return;
        }
        // Check if the requested quantity exceeds the available stock before adding to the cart
        if (product.getQuantity() >= quantity) {
            cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
            System.out.println(quantity + "x " + product.getName() + " added to cart.");
        } else {
            System.out.println("Error: Not enough quantity for " + product.getName() + ". Available: " + product.getQuantity());
        }
    }

    private double calculateSubtotal() {
        double subtotal = 0.0;
        for (Map.Entry<Product, Integer> item : cartItems.entrySet()) {
            subtotal += item.getKey().getPrice() * item.getValue();
        }
        return subtotal;
    }

    public void checkout(double customerBalance, ShippingService shippingService) {
        // 1. Edge Case: Prevent checkout on an empty cart
        if (cartItems.isEmpty()) {
            System.out.println("Checkout Failed: Cart is empty.");
            return;
        }

        List<Shippable> shippableItems = new ArrayList<>();

        // 2. Validation Loop: Check stock limits and expiration dates for all items
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int requestedQuantity = entry.getValue();

            if (product.getQuantity() < requestedQuantity) {
                System.out.println("Checkout Failed: " + product.getName() + " is out of stock or requested quantity unavailable.");
                return;
            }

            // Downcast to check specific rules for expirable items
            if (product instanceof ExpirableProduct) {
                ExpirableProduct expirable = (ExpirableProduct) product;
                if (expirable.isExpired()) {
                    System.out.println("Checkout Failed: " + product.getName() + " is expired.");
                    return;
                }
            }

            // Collect items that require shipping to calculate fees later
            if (product instanceof Shippable) {
                for (int i = 0; i < requestedQuantity; i++) {
                    shippableItems.add((Shippable) product);
                }
            }
        }

        // 3. Financial Calculation: Compute base subtotal and dynamic shipping fees
        double subtotal = calculateSubtotal();
        double shippingFees = 0.0;

        if (!shippableItems.isEmpty()) {
            shippingFees = shippingService.calculateShippingFees(shippableItems);
        }

        double totalPaidAmount = subtotal + shippingFees;

        // 4. Final Transaction: Ensure sufficient funds before modifying inventory
        if (customerBalance < totalPaidAmount) {
            System.out.println("Checkout Failed: Customer's balance is insufficient.");
            return;
        }

        // 5. Inventory Update: Deduct purchased quantities from stock
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int requestedQuantity = entry.getValue();
            product.setQuantity(product.getQuantity() - requestedQuantity);
        }

        double remainingBalance = customerBalance - totalPaidAmount;

        System.out.println("\n--- Checkout Details ---");
        System.out.println("Order Subtotal: $" + subtotal);
        System.out.println("Shipping Fees:  $" + shippingFees);
        System.out.println("Paid Amount:    $" + totalPaidAmount);
        System.out.println("Current Balance:$" + remainingBalance);
        System.out.println("------------------------\n");

        cartItems.clear();
    }
}