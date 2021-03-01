package agents.Seller;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.PropagateMessage;

public class Wait_Propagate extends Behaviour {
	private final Seller seller;
	public PropagateMessage to_propagate;
	private int auction_id = 0;
	private boolean recu = false;

	public Wait_Propagate(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();
		Object serial = null;
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_propagate) {
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof PropagateMessage) {
						to_propagate = (PropagateMessage) serial;
						seller.my_auctionsID.add(to_propagate.getAuctionId());
						recu = true;
					}
				}

				myAgent.addBehaviour(new State_behaviour(seller));
			}
		} else {
			block();
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return recu;
	}
}
