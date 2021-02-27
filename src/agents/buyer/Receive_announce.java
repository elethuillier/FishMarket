package agents.buyer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.AnnounceMessage;

public class Receive_announce extends Behaviour {
	private final Buyer buyer;
	private boolean receive_announce = false;
	private boolean bid = false;
	private boolean no_bid = false;
	private AnnounceMessage announce;

	public Receive_announce(Buyer buyer) {
		this.buyer = buyer;
	}
	
	// Rajouter le cas ou il ne veut pas bid
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_announce) {
				if (buyer.agent_mode == shared.Utils.ControlMode.AUTO) {
					receive_announce = true;
					Object serial = null;
					try {
						serial = message.getContentObject();
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (serial != null) {
						if (serial instanceof AnnounceMessage) {
							announce = (AnnounceMessage) serial;
							buyer.announce = announce;
						}
						if (buyer.agent_budget > announce.getPrice()) {
							bid = true;
							buyer.last_bid = announce.getPrice();
						} else {
							no_bid = true;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (bid == true)
			return true;
		else if (no_bid == true)
			return true;
		else
			return false;
	}

	public int onEnd() {
		if (bid)
			return 2;
		else
			return 8;
	}

}
