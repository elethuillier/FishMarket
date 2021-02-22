package shared.messages;

public class SubscribeMessage {
    private final int auction_id;

    public SubscribeMessage(int auction_id) {
        this.auction_id = auction_id;
    }

    public int getAuctionId() {
        return auction_id;
    }
}
