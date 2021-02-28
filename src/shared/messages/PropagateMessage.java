package shared.messages;

import java.io.Serializable;

import jade.core.AID;
import shared.model.Pack;

public class PropagateMessage implements Serializable {
	private final int auction_id;
	private final AID seller_id;
	private final Pack pack;

	public PropagateMessage(int auction_id, AID seller_id, Pack pack) {
		this.auction_id = auction_id;
		this.seller_id = seller_id;
		this.pack = pack;
	}

	public int getAuctionId() {
		return auction_id;
	}

	public AID getSellerId() {
		return seller_id;
	}

	public Pack getPack() {
		return pack;
	}
}
