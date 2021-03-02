package agents.buyer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.messages.AttributeMessage;
import shared.messages.GiveMessage;

public class Receive_to_give extends Behaviour {
	public boolean receive_give = false;
	public GiveMessage give;

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_give) {
				System.out.println("Reception to_give");
				receive_give = true;
				Object serial = null;
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof GiveMessage) {
						give = (GiveMessage) serial;
					}
				}
			}
		}	
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (receive_give == true)
			return true;
		else 
			return false;
	}

}
