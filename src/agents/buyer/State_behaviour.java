package agents.buyer;

import jade.core.behaviours.FSMBehaviour;

public class State_behaviour extends FSMBehaviour {
	private static final long serialVersionUID = 1L;

	public int onEnd() {
		System.out.println("FSM behaviour terminé");
		return super.onEnd();
	}

	public State_behaviour(Buyer buyer, int cpt) {
		super();
		registerFirstState(new Start(), "1");
		registerState(new Receive_announce(buyer, cpt), "2");
		registerState(new Send_bid(buyer, cpt), "3");
		registerState(new Receive_rep_bid(), "4");
		registerState(new Receive_to_attribute(), "5");
		registerState(new Send_pay(buyer, cpt), "6");
		registerLastState(new Receive_to_give(), "7");
		registerLastState(new No_bid(), "8");

		registerTransition("1", "2", 0); // Start vers announce
		// registerTransition("2", "2", 1);
		registerTransition("2", "3", 2); // Announce vers send_bid
		registerTransition("3", "4", 3); // Send_bid vers rep_bid
		registerTransition("4", "1", 4); // Rep_bid vers Start
		registerTransition("4", "5", 5); // Rep_bid vers to_attribute
		registerTransition("5", "6", 6); // to_attribute vers send_pay
		registerTransition("6", "7", 7); // Send_pay vers to_give
		
		registerTransition("2", "8", 8); // Je ne veux pas bid
	}
}
