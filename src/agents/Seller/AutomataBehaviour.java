package agents.Seller;

import agents.Seller.fsm.*;
import jade.core.behaviours.FSMBehaviour;
import shared.Utils.StopWatch;

public class AutomataBehaviour extends FSMBehaviour {
	private static final long serialVersionUID = 1L;
	public static StopWatch watch;
	private Seller seller;

	public static final int NO_EVENT = -9;
	public static final int ANNOUNCE_EVENT = 1;
	public static final int ONE_BID_EVENT = 2;
	public static final int SEVERAL_BID_EVENT = 3;
	public static final int ATTRIBUTE_EVENT = 4;
	public static final int WAIT_PAY_EVENT = 0;
	public static final int GIVE_EVENT = 0;

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("AutomataBehaviour");
		watch = new StopWatch(seller.myAuction.getCooldown());
	}

	public int onEnd() {
		System.out.println("FSM behaviour terminé");
		return super.onEnd();
	}

	public AutomataBehaviour(Seller seller) {
		super();
		this.seller = seller;
		registerFirstState(new AnnounceBehaviour(seller), "1");
		registerState(new OneBidBehaviour(seller), "2");
		registerState(new SeveralBidBehaviour(seller), "3");
		registerState(new AttributeBehaviour(seller), "4");
		registerState(new WaitPayBehaviour(seller), "5");
		registerLastState(new GiveBehaviour(seller), "6");

		registerDefaultTransition("1", "1", new String[]{"1"});
		registerTransition("1", "1", ANNOUNCE_EVENT, new String[]{"1"});
		registerTransition("1", "2", ONE_BID_EVENT, new String[]{"2"});
		registerTransition("2", "3", SEVERAL_BID_EVENT, new String[]{"3"});
		registerTransition("2", "4", ATTRIBUTE_EVENT);
		registerTransition("3", "3", SEVERAL_BID_EVENT, new String[]{"3"});
		registerTransition("3", "1", ANNOUNCE_EVENT, new String[]{"1"}); // Attention, appeler repBid Behaviour avant d'aller à l'état 1
		registerTransition("4", "5", WAIT_PAY_EVENT);
		registerTransition("5", "6", GIVE_EVENT);
	}
}