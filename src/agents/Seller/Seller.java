package agents.Seller;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;
import shared.model.Auction;


public class Seller extends Agent {
    private List<String[]> PropositionsRecues = new ArrayList<>();
    private List<Auction> OffresEnvoyees = new ArrayList<>();
	@Override
    protected void setup() {
        System.out.println("L'agent "+getAID().getName()+ " est prêt.");

    }
	
	@Override
    protected void takeDown() {
        System.out.println("Agent "+ getAID().getName()+ " terminating.");
    }
}
