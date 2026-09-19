public class PhysicalProduct extends Product implements Shippable{
    private double weight;

    public PhysicalProduct(String name, double price, int quantity, double weight){
        super(name, price, quantity);
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }
}
