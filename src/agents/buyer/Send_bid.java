package agents.buyer;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.BidMessage;

public class Send_bid extends OneShotBehaviour {
	private final Buyer buyer;
	private int cpt;

	public Send_bid(Buyer buyer, int cpt) {
		this.buyer = buyer;
		this.cpt = 0;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = new ACLMessage(shared.Performatives.to_bid);
		BidMessage bid = new BidMessage(buyer.announces.get(cpt).getAuctionId(), buyer.agent_aid);
		try {
			message.setContentObject(bid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.clearAllReceiver();
		message.setSender(myAgent.getAID());
		message.addReceiver(buyer.market_aid);
		myAgent.send(message);
		
	}

	public int onEnd() {
		return 3;
	}

}
