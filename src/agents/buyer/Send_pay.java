package agents.buyer;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.PayMessage;

public class Send_pay extends OneShotBehaviour {
	private final Buyer buyer;

	public Send_pay(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = new ACLMessage(shared.Performatives.to_pay);
		PayMessage pay = new PayMessage(buyer.announce.getAuctionId() ,buyer.last_bid);
		try {
			message.setContentObject(pay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.clearAllReceiver();
		message.setSender(myAgent.getAID());
		myAgent.send(message);
	}

	public int onEnd() {
		return 7;
	}
}
