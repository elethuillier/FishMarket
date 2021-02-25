package agents.buyer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Subscribe extends CyclicBehaviour {

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_publish) {
				//récupérer l'id de l'enchère ici ?
				myAgent.addBehaviour(new State_behaviour());
			}
		}
	}

}
