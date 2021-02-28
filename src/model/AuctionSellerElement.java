package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.model.Pack;

public class AuctionSellerElement {
    private final int id;
    private final StringProperty pack;
    private final DoubleProperty price;
    private final StringProperty buyer;

    public AuctionSellerElement(int id, Pack pack, double price, String buyer) {
        this.id = id;
        this.pack = new SimpleStringProperty(pack.getDescription());
        this.price = new SimpleDoubleProperty(price);
        this.buyer = new SimpleStringProperty(buyer);
    }

    public String getPack() {
        return pack.get();
    }

    public StringProperty packProperty() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack.set(pack);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getBuyer() {
        return buyer.get();
    }

    public StringProperty buyerProperty() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer.set(buyer);
    }

    public int getId() { return id; }
}
