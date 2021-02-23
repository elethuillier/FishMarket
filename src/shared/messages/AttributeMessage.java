package shared.messages;

import jade.core.AID;

import java.io.Serializable;

public class AttributeMessage implements Serializable {
    private final int auction_id;
    private final AID buyer_id;

    public AttributeMessage(int auction_id, AID buyer_id) {
        this.auction_id = auction_id;
        this.buyer_id = buyer_id;
    }

    public int getAuctionId() {
        return auction_id;
    }

    public AID getBuyerId() {
        return buyer_id;
    }
}
