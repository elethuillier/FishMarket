package shared.model;

import java.io.Serializable;
import java.util.Objects;

public class Pack implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pack)) return false;
        Pack pack = (Pack) o;
        return Double.compare(pack.start_price, start_price) == 0 && description.equals(pack.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, start_price);
    }
}
