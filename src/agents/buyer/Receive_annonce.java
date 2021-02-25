package agents.buyer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class Receive_annonce extends Behaviour {

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_announce) {
				
			}
		}
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
