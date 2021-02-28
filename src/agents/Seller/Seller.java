package agents.Seller;

import jade.core.AID;
import jade.core.Agent;

public class Seller extends Agent {
	// private String agent_name;
	public AID agent_aid;

	@Override
	protected void setup() {
		super.setup();
		System.out.println("L'agent " + getAID().getName() + " est prêt.");
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			agent_aid = getAID();
			addBehaviour(new Publish(this));
		} else {
			System.out.println("Inconnu");
			doDelete();
		}

	}

	@Override
	protected void takeDown() {
		System.out.println("Agent " + getAID().getName() + " terminé.");
	}
}
