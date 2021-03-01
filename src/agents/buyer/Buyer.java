package agents.buyer;

import java.util.ArrayList;

import app.BuyerApplication;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import shared.Performatives;
import shared.Utils.ControlMode;
import shared.messages.AnnounceMessage;

public class Buyer extends Agent {
	private String agent_name;
	public double agent_budget;
	public ControlMode agent_mode = ControlMode.AUTO;
	public AID agent_aid;
	public ArrayList<AnnounceMessage> announces;
	public ArrayList<model.AuctionBuyerElement> my_auctions;
	public double last_bid = 0;

	@Override
	protected void setup() {
		super.setup();
		BuyerApplication.controller.setModeListener(mode -> agent_mode = mode);
		System.out.println("Agent: " + getAID().getName() + " is ready.");
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			agent_name = BuyerApplication.agent_name;
			agent_budget = Double.parseDouble(BuyerApplication.str_agent_budget);
			// agent_mode = (int) args[2];
			agent_aid = getAID();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("buyer");
			sd.setName(getLocalName());
			shared.Utils.register(this, sd);
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
