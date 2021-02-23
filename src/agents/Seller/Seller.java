package agents.Seller;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;


public class Seller extends Agent {
    private List<String[]> PropositionsRecues = new ArrayList<>();
    private List<Offre> OffresEnvoyees = new ArrayList<>();
	@Override
    protected void setup() {
        System.out.println("L'agent "+getAID().getName()+ " est prêt.");

    }
	
	@Override
    protected void takeDown() {
        System.out.println("Agent "+ getAID().getName()+ " terminating.");
    }
}
