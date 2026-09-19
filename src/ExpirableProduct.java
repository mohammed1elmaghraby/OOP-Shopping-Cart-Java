import java.time.LocalDate;

public abstract class ExpirableProduct extends Product {
    private LocalDate expDate;

    public ExpirableProduct(String name, double price, int quantity, LocalDate expDate){
        super(name, price, quantity);
        this.expDate = expDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    // Evaluates whether the product has passed its expiration date compared to the current date
    public boolean isExpired(){
        if(expDate.isBefore(LocalDate.now())) {
            return true;
        }
        return false;
    }
}