package shared.messages;

import java.io.Serializable;

public class AnnounceMessage implements Serializable {
    private final int auction_id;
    private final double price;

    public AnnounceMessage(int auction_id, double price) {
        this.auction_id = auction_id;
        this.price = price;
    }

    public int getAuctionId() {
        return auction_id;
    }

    public double getPrice() {
        return price;
    }
}
