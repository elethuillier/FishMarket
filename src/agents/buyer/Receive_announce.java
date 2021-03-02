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
	private int cpt;

	public Receive_announce(Buyer buyer, int cpt) {
		this.buyer = buyer;
		this.cpt = cpt;
	}

	// Rajouter le cas ou il ne veut pas bid
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_announce) {
				if (buyer.agent_mode == shared.Utils.ControlMode.AUTO) {
					System.out.println("Reception to_announce, mode AUTO");
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
							buyer.announces.add(cpt, announce);
						}
						if (buyer.agent_budget > announce.getPrice()) {
							bid = true;
							buyer.last_bid = announce.getPrice();
						} else {
							no_bid = true;
						}
					}
				} else {
					System.out.println("Reception to_announce, mode MANUEL");
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
							buyer.announces.add(cpt, announce);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (buyer.agent_mode == shared.Utils.ControlMode.AUTO) {
			if (bid == true)
				return true;
			else if (no_bid == true)
				return true;
			else
				return false;
		} else {
			if (receive_announce == true)
				return true;
			else
				return false;
		}
	}

	public int onEnd() {
		if (buyer.agent_mode == shared.Utils.ControlMode.AUTO) {
			if (bid)
				return 2;
			else
				return 8;
		} else
			return 2;
	}		
}
