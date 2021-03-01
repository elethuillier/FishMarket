package agents.market;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import shared.model.Auction;

import java.util.ArrayList;
import java.util.List;

import shared.Utils;

public class Market extends Agent {
	public final List<Auction> auctions = new ArrayList<>();

	@Override
	protected void setup() {
		super.setup();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("market");
		sd.setName(getLocalName());
		Utils.register(this, sd);
	}
}
