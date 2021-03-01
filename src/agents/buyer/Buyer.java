package agents.buyer;

import java.util.ArrayList;
import java.util.List;

import app.BuyerApplication;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import model.AuctionBuyerElement;
import shared.Utils.ControlMode;
import shared.messages.AnnounceMessage;

public class Buyer extends Agent {
	private String agent_name;
	public double agent_budget;
	public ControlMode agent_mode = ControlMode.AUTO;
	public AID agent_aid;
	public List<AnnounceMessage> announces;
	public List<AuctionBuyerElement> my_auctions = new ArrayList<>();
	public double last_bid = 0;
	public AID market_aid;

	@Override
	protected void setup() {
		super.setup();
		BuyerApplication.controller.setModeListener((mode, budget) -> {agent_mode = mode;
		agent_budget = budget;});
		System.out.println("Agent: " + getAID().getName() + " is ready.");
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			agent_name = BuyerApplication.agent_name;
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
