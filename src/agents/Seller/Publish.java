package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.PublishMessage;

public class Publish extends CyclicBehaviour {
	private final Seller seller;
	public PublishMessage to_publish;

	public Publish(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(shared.Performatives.to_publish);
		to_publish = new PublishMessage(seller.pack, seller.rising_step, seller.falling_step, seller.cooldown);
		try {
			message.setContentObject(to_publish);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.clearAllReceiver();
		message.setSender(myAgent.getAID());
		myAgent.send(message);

		myAgent.addBehaviour(new Wait_Propagate(seller));

	}
}