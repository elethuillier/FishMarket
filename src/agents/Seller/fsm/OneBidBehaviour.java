package agents.Seller.fsm;

import agents.Seller.AutomataBehaviour;
import agents.Seller.Seller;
import app.SellerApplication;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import javafx.application.Platform;
import model.AuctionSellerElement;
import shared.Performatives;
import shared.Utils;
import shared.messages.BidMessage;
import shared.messages.RepBidMessage;

import java.io.IOException;

public class OneBidBehaviour extends Behaviour {
	private static final long serialVersionUID = 1L;
	private final Seller seller;
	private final MessageTemplate bid_template = MessageTemplate.MatchPerformative(Performatives.to_bid);
	private int event;

	public OneBidBehaviour(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("OneBidBehaviour");
		Platform.runLater(() -> {
			SellerApplication.controller.setPackLabel(seller.myAuction.getPack().getDescription() + " : ");
			SellerApplication.controller.setStateLabel(Utils.LabelContent.ONE_PROPOSITION);
		});
		event = AutomataBehaviour.NO_EVENT;
		AutomataBehaviour.watch.start();
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
			event = AutomataBehaviour.ATTRIBUTE_EVENT;
		} else block(AutomataBehaviour.watch.delta());
	}

	@Override
	public boolean done() {
		return (event == AutomataBehaviour.SEVERAL_BID_EVENT || event == AutomataBehaviour.ATTRIBUTE_EVENT);
	}

	@Override
	public int onEnd() {
		if(event == AutomataBehaviour.ATTRIBUTE_EVENT) {
			ACLMessage message = new ACLMessage(Performatives.rep_bid);
			RepBidMessage repbid = new RepBidMessage(seller.myAuction.getId(), RepBidMessage.State.OK);
			message.addReceiver(seller.market);
			try { message.setContentObject(repbid); } catch (IOException e) { e.printStackTrace(); }
			message.setSender(myAgent.getAID());
			myAgent.send(message);
		}
		return event;
	}

}
