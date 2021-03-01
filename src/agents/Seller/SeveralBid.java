package agents.Seller;

import java.util.Calendar;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.BidMessage;

public class SeveralBid extends Behaviour {
	private final Seller seller;

	public SeveralBid(Seller seller) {
		this.seller = seller;
	}

	private static final long serialVersionUID = 1L;
	private int inProgress = 0;
	public static final int FIN = 1;
	public static final int ATTENTE = 3;
	public long cooldown = -1;

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());

		switch (inProgress) {
		case ATTENTE:
			ACLMessage message = myAgent.receive();
			if (message != null) {
				if (message.getPerformative() == shared.Performatives.to_bid) {
					Object serial = null;
					try {
						serial = message.getContentObject();
						cooldown = Calendar.getInstance().getTimeInMillis();
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (serial != null) {
						if (serial instanceof BidMessage) {
							BidMessage bid = (BidMessage) serial;
							seller.my_bids.add(bid);
						}
						inProgress = FIN;
					}
				}
			}else {
				block();
			}
			break;

		default:
			break;
		}

		if (cooldown < 0) {
			cooldown = Calendar.getInstance().getTimeInMillis();
		}

		if (cooldown > 0 && (cooldown + 5000 < Calendar.getInstance().getTimeInMillis())) {
			inProgress = FIN;
		}
	}

	// Overrided functions
	@Override
	public boolean done() {
		return (inProgress == FIN) || (inProgress == ATTENTE);
	}

	@Override
	public int onEnd() {
		return inProgress;
	}

}
