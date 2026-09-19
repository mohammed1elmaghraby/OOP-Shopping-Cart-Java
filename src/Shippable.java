/**
 * Interface representing items that can be physically shipped.
 * Any class implementing this interface must provide weight details
 * for dynamic shipping fee calculations.
 */
public interface Shippable {
    public String getName();
    public double getWeight();
}
