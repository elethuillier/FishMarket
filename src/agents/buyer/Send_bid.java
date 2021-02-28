package agents.buyer;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.BidMessage;

public class Send_bid extends OneShotBehaviour {
	private final Buyer buyer;

	public Send_bid(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = new ACLMessage(shared.Performatives.to_bid);
		BidMessage bid = new BidMessage(buyer.announce.getAuctionId(), buyer.agent_aid);
		try {
			message.setContentObject(bid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.clearAllReceiver();
		message.setSender(myAgent.getAID());
		myAgent.send(message);
	}

	public int onEnd() {
		return 3;
	}

}
