package agents.buyer;

import jade.core.behaviours.FSMBehaviour;

public class State_behaviour extends FSMBehaviour {
	private static final long serialVersionUID = 1L;
	public int id_auction;

	public int onEnd() {
		System.out.println("FSM behaviour terminé");
		return super.onEnd();
	}

	public State_behaviour() {
		super();
		registerFirstState(new Start(), "1");
		registerState(new Receive_annonce(), "2");
		// registerState(new send_bid(), "3");
		// registerState(new receive_rep_bid(), "4");
		// registerState(new receive_to_attribute(), "5");
		// registerState(new send_pay(), "6");
		// registerLastState(new receive_to_give(), "7");

		registerTransition("1", "2", 0);
		registerTransition("2", "2", 1);
		registerTransition("2", "3", 2);
		registerTransition("3", "1", 3);
		registerTransition("3", "4", 4);
		registerTransition("4", "5", 5);
		registerTransition("5", "6", 6);
	}
}
