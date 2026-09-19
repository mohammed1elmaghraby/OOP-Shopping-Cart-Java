import java.time.LocalDate;

public class main {
    public static void main(String[] args) {
        ShippingService shippingService = new ShippingService();

        PhysicalProduct monitor = new PhysicalProduct("Msi Pro Mp225 Monitor", 3000.0, 5, 4.5);
        DigitalProduct game = new DigitalProduct("Hogwarts Legacy Digital Key", 1500.0, 100);
        PhysicalExpiredProduct coffee = new PhysicalExpiredProduct("Turkish Coffee", 150.0, 20, LocalDate.of(2027, 10, 1), 0.5);
        PhysicalExpiredProduct dates = new PhysicalExpiredProduct("Dates", 50.0, 10, LocalDate.of(2025, 1, 1), 0.2);

        // =========================================================
        // Test Case 1: (Happy Path)
        // =========================================================
        System.out.println("========== Test Case 1: Happy Path ==========");
        Cart cart1 = new Cart();
        cart1.addProduct(monitor, 1);
        cart1.addProduct(game, 1);
        cart1.addProduct(coffee, 2);

        cart1.checkout(6000.0, shippingService);

        // =========================================================
        // Test Case 2: (Insufficient Balance)
        // =========================================================
        System.out.println("\n========== Test Case 2: Insufficient Balance ==========");
        Cart cart2 = new Cart();
        cart2.addProduct(monitor, 2);

        cart2.checkout(5000.0, shippingService);


        // =========================================================
        // Test Case 3: (Expired Product)
        // =========================================================
        System.out.println("\n========== Test Case 3: Expired Product ==========");
        Cart cart3 = new Cart();
        cart3.addProduct(dates, 1);

        cart3.checkout(1000.0, shippingService);


        // =========================================================
        // Test Case 4: (Out of Stock)
        // =========================================================
        System.out.println("\n========== Test Case 4: Out of Stock ==========");
        Cart cart4 = new Cart();

        cart4.addProduct(monitor, 10);


        // =========================================================
        // Test Case 5: (Empty Cart)
        // =========================================================
        System.out.println("\n========== Test Case 5: Empty Cart ==========");
        Cart cart5 = new Cart();

        cart5.checkout(1000.0, shippingService);
    }
}