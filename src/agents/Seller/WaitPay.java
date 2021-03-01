package agents.Seller;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.PayMessage;

public class WaitPay extends Behaviour {
	private final Seller seller;

	public WaitPay(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_pay) {
				Object serial = null;
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof PayMessage) {
						PayMessage pay = (PayMessage) serial;
						seller.Payed = pay.getPrice();
					}
				}
			}
		} else {
			block();
		}
	}

	@Override
	public int onEnd() {
		return 0;
	}

	@Override
	public boolean done() {
		return true;
	}

}
