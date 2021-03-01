package agents.Seller;

import java.util.ArrayList;
import java.util.List;

import app.SellerApplication;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import shared.Utils;
import shared.messages.BidMessage;
import shared.model.Auction;
import shared.model.Pack;

public class Seller extends Agent {
	public AID agent_aid;
	public double Payed = 0;
	public AID destinataire;
	public List<BidMessage> my_bids = new ArrayList<BidMessage>();
	public Auction monAuction;

	@Override
	protected void setup() {
		super.setup();

		ServiceDescription sd = new ServiceDescription();
		sd.setType("seller");
		sd.setName(getLocalName());
		Utils.register(this, sd);

		System.out.println("L'agent " + getAID().getName() + " est prêt.");
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			agent_aid = getAID();
			SellerApplication.controller.setCreateListener(message -> {
				addBehaviour(new Publish(message, this));
			});

		} else {
			System.out.println("Inconnu");
			doDelete();
		}
	}

	@Override
	protected void takeDown() {
		System.out.println("Agent " + getAID().getName() + " terminé.");
	}
}
