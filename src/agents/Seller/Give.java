package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.GiveMessage;

public class Give extends OneShotBehaviour {
	private final Seller seller;

	public Give(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage message = new ACLMessage(shared.Performatives.to_give);
		GiveMessage give = new GiveMessage(seller.monAuction.getId());
		message.addReceiver(seller.destinataire);
		try {
			message.setContentObject(give);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.setSender(myAgent.getAID());
		myAgent.send(message);
	}

	@Override
	public int onEnd() {
		return 0;
	}

}
