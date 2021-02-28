package agents.Seller;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import shared.messages.BidMessage;
import shared.model.Pack;

public class Seller extends Agent {
	private String agent_name;
	public AID agent_aid;
	
	public Pack pack = new Pack(400, "Poisson");
	public double step = 20.0;
	public double rising_step = step;
	public double falling_step = step;
	public int cooldown = 5000;

	List<Integer> my_auctionsID = new ArrayList<Integer>();
	List<BidMessage> my_bids = new ArrayList<BidMessage>();

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
