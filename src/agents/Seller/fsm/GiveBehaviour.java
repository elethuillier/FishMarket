package agents.Seller.fsm;

import java.io.IOException;

import agents.Seller.Seller;
import app.SellerApplication;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import shared.Performatives;
import shared.Utils;
import shared.messages.GiveMessage;

public class GiveBehaviour extends OneShotBehaviour {
	private final Seller seller;

	public GiveBehaviour(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("GiveBehaviour");
		Platform.runLater(() -> {
			SellerApplication.controller.setPackLabel(seller.myAuction.getPack().getDescription() + " : ");
			SellerApplication.controller.setStateLabel(Utils.LabelContent.PAYED);
		});
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage message = new ACLMessage(Performatives.to_give);
		GiveMessage give = new GiveMessage(seller.myAuction.getId());
		message.addReceiver(seller.market);
		try { message.setContentObject(give); } catch (IOException e) { e.printStackTrace(); }
		message.setSender(myAgent.getAID());
		myAgent.send(message);
	}

	@Override
	public int onEnd() {
		return 0;
	}

}
