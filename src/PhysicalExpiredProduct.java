import java.time.LocalDate;

public class PhysicalExpiredProduct extends ExpirableProduct implements Shippable {
    private double weight;

    public PhysicalExpiredProduct(String name, double price, int quantity, LocalDate expDate, double weight){
        super(name, price, quantity, expDate);
        this.weight = weight;
    }

    @Override
    public double getWeight(){
        return weight;
    }
}
