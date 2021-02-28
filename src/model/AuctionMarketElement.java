package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.model.Pack;

public class AuctionMarketElement {
    private final int id;
    private final StringProperty seller;
    private final StringProperty pack;
    private final DoubleProperty price;
    private boolean done = false;

    public Runnable checkDone;

    public AuctionMarketElement(int id, String seller, Pack pack, Double price) {
        this.id = id;
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

    public Double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public boolean isDone() { return done; }

    public void setDone(boolean done) { this.done = done; }

    public int getId() { return id; }
}
