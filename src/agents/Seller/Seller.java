package agents.Seller;

import java.util.ArrayList;
import java.util.List;

import app.SellerApplication;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import shared.Utils;
import shared.messages.BidMessage;
import shared.model.Auction;

public class Seller extends Agent {
	public AID agent_aid;
	public AID market;
	public List<BidMessage> my_bids = new ArrayList<>();
	public Auction myAuction;

	@Override
	protected void setup() {
		super.setup();

		ServiceDescription sd = new ServiceDescription();
		sd.setType("seller");
		sd.setName(getLocalName());
		Utils.register(this, sd);

		SellerApplication.controller.setPackLabel("");
		SellerApplication.controller.setStateLabel(Utils.LabelContent.CREATE);

		System.out.println("L'agent " + getAID().getName() + " est prêt.");
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			System.out.println("setListener");
			agent_aid = getAID();
			SellerApplication.controller.setCreateListener(message -> {
				SequentialBehaviour sequence = new SequentialBehaviour();
				sequence.addSubBehaviour(new PublishBehaviour(message, this));
				sequence.addSubBehaviour(new WaitPropagateBehaviour(this));
				addBehaviour(sequence);
			});

			SellerApplication.controller.setAnnounceListener(() -> {
				addBehaviour(new AutomataBehaviour(this));
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
