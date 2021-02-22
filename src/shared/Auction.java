package shared;

import agents.Seller;
import jade.core.AID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Auction implements Serializable {
    private final Seller seller;
    private final Pack pack;
    private final double rising_step;
    private final double falling_step;
    private final int cooldown; // ms
    private final int id;

    private List<AID> subscribers = new ArrayList<>();
    private List<AID> offerers = new Stack<>();

    private double current_price;
    private boolean done = false;

    public Auction(Pack pack, Seller seller, double rising_step, double falling_step, int cooldown) {
        this.pack = pack;
        this.seller = seller;
        current_price = pack.getStartPrice();
        this.rising_step = rising_step;
        this.falling_step = falling_step;
        this.cooldown = cooldown;
        this.id = hashCode();
    }

    public Seller getSeller() {
        return seller;
    }

    public List<AID> getOfferers() {
        return offerers;
    }

    public List<AID> getSubscribers() {
        return subscribers;
    }

    public double getCurrentPrice() {
        return current_price;
    }

    public void setCurrentPrice(double current_price) {
        this.current_price = current_price;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auction)) return false;
        Auction auction = (Auction) o;
        return Double.compare(auction.rising_step, rising_step) == 0 && Double.compare(auction.falling_step, falling_step) == 0 && cooldown == auction.cooldown && seller.equals(auction.seller) && pack.equals(auction.pack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seller, pack, rising_step, falling_step, cooldown);
    }
}
