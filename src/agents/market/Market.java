package agents.market;

import jade.core.Agent;
import shared.model.Auction;

import java.util.ArrayList;
import java.util.List;

public class Market extends Agent {
	public final List<Auction> auctions = new ArrayList<>();

	@Override
	protected void setup() {
		super.setup();
	}
}
