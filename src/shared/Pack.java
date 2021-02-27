package shared;

public class Pack {
    private final String description;
    private final double start_price;

    public Pack(double start_price) {
        this.start_price = start_price;
        description = "Fish";
    }

    public Pack(double start_price, String description) {
        this.start_price = start_price;
        this.description = description;
    }

    public double getStartPrice() {
        return start_price;
    }

    public String getDescription() {
        return description;
    }
}
