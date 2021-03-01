package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.PublishMessage;

public class Publish extends OneShotBehaviour {
	public PublishMessage to_publish;
	Seller seller;

	public Publish(PublishMessage message, Seller seller) {
		this.to_publish = message;
		this.seller = seller;
	}

	@Override
	public void action() {
		ACLMessage messageLoc = new ACLMessage(shared.Performatives.to_publish);
		try {
			messageLoc.setContentObject(to_publish);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messageLoc.clearAllReceiver();
		messageLoc.setSender(myAgent.getAID());
		myAgent.send(messageLoc);

		myAgent.addBehaviour(new Wait_Propagate(seller));

	}
}