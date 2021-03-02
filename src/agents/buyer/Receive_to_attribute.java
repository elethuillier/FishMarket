package agents.buyer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.AttributeMessage;
import shared.messages.RepBidMessage;

public class Receive_to_attribute extends Behaviour {
	public boolean receive_attribute = false;
	public AttributeMessage attribute;

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_attribute) {
				System.out.println("Reception to_attribute");
				receive_attribute = true;
				Object serial = null;
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof AttributeMessage) {
						attribute = (AttributeMessage) serial;
					}
				}
			}
		}		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (receive_attribute == true)
			return true;
		else
			return false;
	}

	public int onEnd() {
		return 6;
	}
}
