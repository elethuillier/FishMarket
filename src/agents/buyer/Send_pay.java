package agents.buyer;

import java.io.IOException;

import app.BuyerApplication;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.PayMessage;

public class Send_pay extends OneShotBehaviour {
	private final Buyer buyer;
	private int cpt;

	public Send_pay(Buyer buyer, int cpt) {
		this.buyer = buyer;
		this.cpt = cpt;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("Je paie ! Aie la douloureuse ...");
		javafx.application.Platform
		.runLater(() -> BuyerApplication.controller.getInfo().setText(shared.Utils.LabelContent.BUYER_PAYED));
		ACLMessage message = new ACLMessage(shared.Performatives.to_pay);
		PayMessage pay = new PayMessage(buyer.announces.get(cpt).getAuctionId(), buyer.last_bid);
		try {
			message.setContentObject(pay);
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
		return 7;
	}
}
