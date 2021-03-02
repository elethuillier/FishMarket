package agents.Seller.fsm;

import java.io.IOException;

import agents.Seller.AutomataBehaviour;
import agents.Seller.Seller;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.AttributeMessage;

public class AttributeBehaviour extends OneShotBehaviour {
	private final Seller seller;

	public AttributeBehaviour(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("AttributeBehaviour");
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage message = new ACLMessage(shared.Performatives.to_attribute);
		AttributeMessage attribute = new AttributeMessage(seller.myAuction.getId(), seller.myAuction.getOfferers().pop());
		message.addReceiver(seller.market);
		try { message.setContentObject(attribute); } catch (IOException e) { e.printStackTrace(); }
		message.setSender(myAgent.getAID());
		myAgent.send(message);
	}

	@Override
	public int onEnd() {
		return AutomataBehaviour.WAIT_PAY_EVENT;
	}

}
