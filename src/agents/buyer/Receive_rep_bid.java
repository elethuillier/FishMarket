package agents.buyer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.RepBidMessage;

public class Receive_rep_bid extends Behaviour {
	public RepBidMessage rep_bid;
	public boolean ok = false;
	public boolean receive_bid = false;

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.rep_bid) {
				System.out.println("Reception rep_bid");
				receive_bid = true;
				Object serial = null;
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof RepBidMessage) {
						rep_bid = (RepBidMessage) serial;
					}
				}
				if (rep_bid.getState().equals(RepBidMessage.State.OK)) {
					ok = true;
				}
			}
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (receive_bid == true)
			return true;
		else
			return false;
	}

	public int onEnd() {
		if (ok == true)
			return 5;
		else
			return 4;
	}

}
