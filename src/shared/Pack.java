package shared;

public class Pack {
    private final String description = "fish";
    private double start_price;

    public Pack(double start_price) {
        this.start_price = start_price;
    }

    public double getStartPrice() {
        return start_price;
    }

    public void setStartPrice(double start_price) {
        this.start_price = start_price;
    }
}
