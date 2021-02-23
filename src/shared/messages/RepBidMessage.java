package shared.messages;

public class RepBidMessage {
    private final int auction_id;
    private final State state;

    public RepBidMessage(int auction_id, State state) {
        this.auction_id = auction_id;
        this.state = state;
    }

    public int getAuctionId() {
        return auction_id;
    }

    public State getState() {
        return state;
    }

    public enum State{
        OK, NOK
    }
}