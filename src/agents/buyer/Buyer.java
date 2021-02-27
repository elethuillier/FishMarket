package agents.buyer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import shared.Performatives;
import shared.messages.AnnounceMessage;

public class Buyer extends Agent {
	public static final int AUTO_MODE = 0;
	public static final int MANUAL_MODE = 1;
	
	private String agent_name;
	public double agent_budget;
	public int agent_mode = AUTO_MODE;
	public AID agent_aid;
	public AnnounceMessage announce;
	public double last_bid = 0;
	/*
	private final MessageTemplate subscribe_message = MessageTemplate.MatchPerformative(Performatives.to_subscribe);
	private final MessageTemplate bid_message = MessageTemplate.MatchPerformative(Performatives.to_bid);
	private final MessageTemplate pay_message = MessageTemplate.MatchPerformative(Performatives.to_pay);
	*/
	@Override
	protected void setup() {
		super.setup();
		System.out.println("Agent: " + getAID().getName() + " is ready.");
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			agent_name = (String) args[0];
			agent_budget = (float) args[1];
			// agent_mode = (int) args[2];
			agent_aid = getAID();
			addBehaviour(new Subscribe(this));
		} else {
			System.out.println("No name specified");
			doDelete();
		}
	}

	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent " + getAID().getName() + " terminating.");
	}
}
