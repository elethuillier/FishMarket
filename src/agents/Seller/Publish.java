package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.PublishMessage;
import shared.model.Pack;

public class Publish extends CyclicBehaviour {
	private final Seller seller;
	public PublishMessage to_publish;
	private Pack pack;
	private double step = 20.0;
	private double rising_step = step;
	private double falling_step = step;
	private int cooldown = 5000;

	public Publish(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(shared.Performatives.to_publish);
		to_publish = new PublishMessage(pack, rising_step, falling_step , cooldown);
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

//TODO