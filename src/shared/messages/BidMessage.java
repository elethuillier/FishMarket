package shared.messages;

import jade.core.AID;

public class BidMessage {
    private final int auction_id;
    private final String description;
    private final double price;
    private final AID buyer_id;

    public BidMessage(int auction_id, String description, double price, AID buyer_id) {
        this.auction_id = auction_id;
        this.description = description;
        this.price = price;
        this.buyer_id = buyer_id;
    }

    public int getAuctionId() {
        return auction_id;
    }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public AID getBuyerId() { return buyer_id; }
}
