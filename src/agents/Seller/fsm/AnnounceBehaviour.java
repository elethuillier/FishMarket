package agents.Seller.fsm;

import java.io.IOException;

import agents.Seller.AutomataBehaviour;
import agents.Seller.Seller;
import app.SellerApplication;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.AuctionSellerElement;
import shared.Performatives;
import shared.messages.AnnounceMessage;
import shared.messages.BidMessage;
import shared.Utils.StopWatch;

public class AnnounceBehaviour extends Behaviour {
	private static final long serialVersionUID = 1L;
	private final Seller seller;
	private final MessageTemplate bid_template = MessageTemplate.MatchPerformative(Performatives.to_bid);

	public AnnounceBehaviour(Seller seller) {
		this.seller = seller;
	}

	private StopWatch watch;
	private int event;

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("AnnounceBehaviour");
		event = AutomataBehaviour.NO_EVENT;
		seller.myAuction.getOfferers().empty();
		watch = new StopWatch(seller.myAuction.getCooldown());
		ACLMessage message = new ACLMessage(shared.Performatives.to_announce);
		AnnounceMessage annonce = new AnnounceMessage(seller.myAuction.getId(), seller.myAuction.getCurrentPrice());
		message.addReceiver(seller.market);
		try { message.setContentObject(annonce); } catch (IOException e) { e.printStackTrace(); }
		message.setSender(myAgent.getAID());
		myAgent.send(message);
		watch.start();
	}

	@Override
	public void action() {
		ACLMessage receive = myAgent.receive(bid_template);
		if (receive != null) {
			System.out.println(myAgent.getAID().getName() + " Offre recue");
			try {
				Object serial = receive.getContentObject();
				if (serial instanceof BidMessage) {
					BidMessage bid = (BidMessage) serial;
					SellerApplication.self.getObservableAuctions().add(new AuctionSellerElement(bid.getAuctionId(), seller.myAuction.getPack(), seller.myAuction.getCurrentPrice(), bid.getBuyerId().getName()));
					seller.myAuction.getOfferers().add(bid.getBuyerId());
					event = AutomataBehaviour.ONE_BID_EVENT;
				}
			} catch (UnreadableException e) { e.printStackTrace(); }
		} else if (watch.done()) {
				seller.myAuction.setCurrentPrice(seller.myAuction.getCurrentPrice() - seller.myAuction.getFallingStep());
				event = AutomataBehaviour.ANNOUNCE_EVENT;
		} else block(watch.delta());
	}

	@Override
	public boolean done() {
		return (event == AutomataBehaviour.ONE_BID_EVENT || event == AutomataBehaviour.ANNOUNCE_EVENT);
	}

	@Override
	public int onEnd() {
		return event;
	}
}
