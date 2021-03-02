package agents.Seller.fsm;

import agents.Seller.AutomataBehaviour;
import agents.Seller.Seller;
import app.SellerApplication;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.AuctionSellerElement;
import shared.Performatives;
import shared.messages.BidMessage;

public class SeveralBidBehaviour extends Behaviour {
	private static final long serialVersionUID = 1L;
	private final Seller seller;

	private final MessageTemplate bid_template = MessageTemplate.MatchPerformative(Performatives.to_bid);
	private int event;

	public SeveralBidBehaviour(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("SeveralBidBehaviour");
		event = AutomataBehaviour.NO_EVENT;
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage receive = myAgent.receive(bid_template);
		if (receive != null) {
			System.out.println(myAgent.getAID().getName() + " Nouvelle Offre recue");
			try {
				Object serial = receive.getContentObject();
				if (serial instanceof BidMessage) {
					BidMessage bid = (BidMessage) serial;
					SellerApplication.self.getObservableAuctions().add(new AuctionSellerElement(bid.getAuctionId(), seller.myAuction.getPack(), seller.myAuction.getCurrentPrice(), bid.getBuyerId().getName()));
					seller.myAuction.getOfferers().add(bid.getBuyerId());
					event = AutomataBehaviour.SEVERAL_BID_EVENT;
				}
			} catch (UnreadableException e) { e.printStackTrace(); }
		} else if (AutomataBehaviour.watch.done()) {
			event = AutomataBehaviour.ANNOUNCE_EVENT;
		} else block(AutomataBehaviour.watch.delta());
	}

	// Overrided functions
	@Override
	public boolean done() {
		return (event == AutomataBehaviour.ANNOUNCE_EVENT || event == AutomataBehaviour.SEVERAL_BID_EVENT);
	}

	@Override
	public int onEnd() {
		return event;
	}
}
