package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.Pack;

public class AuctionBuyerElement {
    private final StringProperty seller;
    private final StringProperty pack;
    private final DoubleProperty price;

    public AuctionBuyerElement(String seller, Pack pack, Double price) {
        this.seller = new SimpleStringProperty(seller);
        this.pack = new SimpleStringProperty(pack.getDescription());
        this.price = new SimpleDoubleProperty(price);
    }

    public String getSeller() {
        return seller.get();
    }

    public StringProperty sellerProperty() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller.set(seller);
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
}
