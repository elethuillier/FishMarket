package shared.messages;

import shared.Auction;

public class PublishMessage {
    private final Auction auction;

    public PublishMessage(Auction auction) {
        this.auction = auction;
    }

    public Auction getAuction() {
        return auction;
    }
}
