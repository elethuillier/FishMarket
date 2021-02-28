package shared.model;

import jade.core.AID;

import java.io.Serializable;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.Stack;

public class Auction implements Serializable {
    private final AID seller;
    private final Pack pack;
    private final double rising_step;
    private final double falling_step;
    private final int cooldown; // ms

    private final Set<AID> subscribers = new HashSet<>();
    private final Stack<AID> offerers = new Stack<>();

    private double current_price;
    private boolean done = false;

    private final int id;

    public Auction(Pack pack, AID seller, double rising_step, double falling_step, int cooldown) {
        this.pack = pack;
        this.seller = seller;
        current_price = pack.getStartPrice();
        this.rising_step = rising_step;
        this.falling_step = falling_step;
        this.cooldown = cooldown;
        this.id = hashCode();
    }

    public AID getSeller() {
        return seller;
    }

    public Stack<AID> getOfferers() {
        return offerers;
    }

    public Set<AID> getSubscribers() {
        return subscribers;
    }

    public int getId() {
        return id;
    }

    public Pack getPack() { return pack; }



    public void setCurrentPrice(double current_price) {
        this.current_price = current_price;
    }

    public double getCurrentPrice() { return current_price; }

    public void setDone(boolean done) { this.done = done; }

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
