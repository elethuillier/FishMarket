package agents.buyer;

import java.io.IOException;

import app.BuyerApplication;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.Utils.ControlMode;
import shared.messages.BidMessage;

public class Send_bid extends Behaviour {
	private final Buyer buyer;
	private int cpt;
	private boolean send_bid = false;

	public Send_bid(Buyer buyer, int cpt) {
		this.buyer = buyer;
		this.cpt = 0;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (buyer.agent_mode.equals(ControlMode.AUTO)) {
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
			System.out.println("Je bid!");
		} else {
			BuyerApplication.controller.setBidListener(auction -> {
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
				send_bid = true;
				System.out.println("Je bid!");
			});
		}
	}

	public int onEnd() {
		return 3;
	}

	@Override
	public boolean done() {
		if (buyer.agent_mode.equals(ControlMode.AUTO)) {
			javafx.application.Platform
					.runLater(() -> BuyerApplication.controller.getInfo().setText(shared.Utils.LabelContent.PROPOSED));
			return true;
		} else {
			if (send_bid == true) {
				javafx.application.Platform.runLater(
						() -> BuyerApplication.controller.getInfo().setText(shared.Utils.LabelContent.PROPOSED));
				return true;
			} else
				return false;
		}
	}

}
