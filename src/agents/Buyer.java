package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import shared.Performatives;

public class Buyer extends Agent {
	private String agent_name;

	private final MessageTemplate subscribe_message = MessageTemplate.MatchPerformative(Performatives.to_subscribe);
	private final MessageTemplate bid_message = MessageTemplate.MatchPerformative(Performatives.to_bid);
	private final MessageTemplate pay_message = MessageTemplate.MatchPerformative(Performatives.to_pay);

	@Override
	protected void setup() {
		super.setup();
		System.out.println("Agent: " + getAID().getName() + " is ready.");
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			agent_name = (String) args[0];
			addBehaviour(new Buyer_Behaviour());
		} else {
			System.out.println("No name specified");
			doDelete();
		}
	}

	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent " + getAID().getName() + " terminating.");
	}

	private class Buyer_Behaviour extends Behaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
		
		public void subscribe() {
			
		}
	}
}
