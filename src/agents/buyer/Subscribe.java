package agents.buyer;

import app.BuyerApplication;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.PropagateMessage;
import shared.messages.RepBidMessage;

public class Subscribe extends CyclicBehaviour {
	private final Buyer buyer;
	public PropagateMessage to_propagate;
	
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
				//récupérer l'id de l'enchère ici ?		
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
				// FIXME elian : rename ProposeListener vers BidListener, mais pas plutôt SubscribeListener de base ?
				BuyerApplication.controller.setBidListener(auction -> buyer.my_auctions.add(auction));
				
				myAgent.addBehaviour(new State_behaviour(buyer));
			}
		}
		
	}
}
