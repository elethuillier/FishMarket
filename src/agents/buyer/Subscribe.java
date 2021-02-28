package agents.buyer;

import java.io.IOException;

import app.BuyerApplication;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.BidMessage;
import shared.messages.PropagateMessage;
import shared.messages.RepBidMessage;
import shared.messages.SubscribeMessage;

public class Subscribe extends CyclicBehaviour {
	private final Buyer buyer;
	public PropagateMessage to_propagate;
	public int cpt_announce = 0;

	public Subscribe(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		Object serial = null;
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_propagate) {
				// récupérer l'id de l'enchère ici ?
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof PropagateMessage) {
						to_propagate = (PropagateMessage) serial;
					}
				}
				final ACLMessage message_sub = new ACLMessage(shared.Performatives.to_subscribe);
				BuyerApplication.controller.setSubscribeListener(auction -> {
					buyer.my_auctions.add(auction);
					SubscribeMessage sub = new SubscribeMessage(auction.getId());
					try {
						message_sub.setContentObject(sub);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					message_sub.clearAllReceiver();
					message_sub.setSender(myAgent.getAID());
					myAgent.send(message_sub);
					myAgent.addBehaviour(new State_behaviour(buyer, cpt_announce));
					cpt_announce++;
				});
			}
		}
	}
}
