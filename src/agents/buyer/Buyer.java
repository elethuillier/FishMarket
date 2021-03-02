package agents.buyer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.BuyerApplication;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import model.AuctionBuyerElement;
import shared.Utils.ControlMode;
import shared.messages.AnnounceMessage;
import shared.messages.SubscribeMessage;

public class Buyer extends Agent {
	public double agent_budget;
	public ControlMode agent_mode = ControlMode.AUTO;
	public AID agent_aid;
	public List<AnnounceMessage> announces = new ArrayList<>();
	public List<AuctionBuyerElement> my_auctions = new ArrayList<>();
	public double last_bid = 0;
	public AID market_aid;
	public int cpt_announce = 0;

	@Override
	protected void setup() {
		super.setup();
		javafx.application.Platform
				.runLater(() -> BuyerApplication.controller.getInfo().setText(shared.Utils.LabelContent.MODE));
		BuyerApplication.controller.getPropose().setDisable(true);
		BuyerApplication.controller.getSubscribe().setDisable(true);
		BuyerApplication.controller.setModeListener((mode, budget) -> {
			agent_mode = mode;
			agent_budget = budget;
			BuyerApplication.controller.getSubscribe().setDisable(false);
		});
		ACLMessage message_sub = new ACLMessage(shared.Performatives.to_subscribe);
		BuyerApplication.controller.setSubscribeListener(auction -> {
			my_auctions.add(auction);
			SubscribeMessage sub = new SubscribeMessage(auction.getId());
			try {
				message_sub.setContentObject(sub);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (agent_mode == ControlMode.AUTO)
				javafx.application.Platform.runLater(
						() -> BuyerApplication.controller.getInfo().setText(shared.Utils.LabelContent.SUBSCRIBE_AUTO));
			else
				javafx.application.Platform.runLater(() -> BuyerApplication.controller.getInfo()
						.setText(shared.Utils.LabelContent.SUBSCRIBE_MANUAL));
			message_sub.clearAllReceiver();
			message_sub.setSender(getAID());
			message_sub.addReceiver(market_aid);
			System.out.println("Je me subscribe");
			send(message_sub);
			addBehaviour(new State_behaviour(this, cpt_announce));
			cpt_announce++;
		});
		System.out.println("Agent: " + getAID().getName() + " is ready.");
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
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
