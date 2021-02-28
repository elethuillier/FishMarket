package agents.Seller;

import jade.core.behaviours.FSMBehaviour;

public class State_behaviour extends FSMBehaviour {
	private static final long serialVersionUID = 1L;

	public int onEnd() {
		System.out.println("FSM behaviour terminé");
		return super.onEnd();
	}

	public State_behaviour(Seller seller) {
		super();
		registerFirstState(new announce(seller), "1");
		registerState(new OneBid(seller), "2");
		registerState(new SeveralBid(seller), "3");
		registerState(new AttributePack(seller), "4");
//		 registerState(new WaitPay(), "5");
//		 registerLastState(new Give(), "6");

		registerDefaultTransition("1", "1");
		registerTransition("1", "1", 1);
		registerTransition("1", "2", 2);
		registerTransition("2", "3", 3);
		registerTransition("2", "4", 4);
		registerTransition("3", "3", 3);
		registerTransition("3", "1", 1); // Attention, appeler repBid Behaviour avant d'aller à l'état 1
		registerTransition("4", "5", 0); // Un seul choix par defaut
		registerTransition("5", "6", 0); // Un seul choix par defaut
	}
}