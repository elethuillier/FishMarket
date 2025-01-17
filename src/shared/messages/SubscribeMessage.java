package shared.messages;

import java.io.Serializable;

public class SubscribeMessage implements Serializable {
    private final int auction_id;

    public SubscribeMessage(int auction_id) {
        this.auction_id = auction_id;
    }

    public int getAuctionId() {
        return auction_id;
    }
}
