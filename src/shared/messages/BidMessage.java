package shared.messages;

public class BidMessage {
    private final int auction_id;
    private final double offer;

    public BidMessage(int auction_id, double offer) {
        this.auction_id = auction_id;
        this.offer = offer;
    }

    public int getAuctionId() {
        return auction_id;
    }

    public double getOffer() {
        return offer;
    }
}
