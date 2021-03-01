package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.AttributeMessage;

public class AttributePack extends OneShotBehaviour {
	private final Seller seller;

	public AttributePack(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage message = new ACLMessage(shared.Performatives.to_attribute);
		AttributeMessage attribute = new AttributeMessage(seller.my_auctionsID.get(0),
				seller.my_bids.get(seller.my_bids.size() - 1).getBuyerId());
		message.addReceiver(seller.destinataire);
		try {
			message.setContentObject(attribute);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.clearAllReceiver();
		message.setSender(myAgent.getAID());
		myAgent.send(message);
	}

	@Override
	public int onEnd() {
		return 0;
	}

}
