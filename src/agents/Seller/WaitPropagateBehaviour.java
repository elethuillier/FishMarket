package agents.Seller;

import app.SellerApplication;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import shared.Performatives;
import shared.messages.PropagateMessage;

public class WaitPropagateBehaviour extends Behaviour {
	private final Seller seller;
	private final MessageTemplate propagate_template = MessageTemplate.MatchPerformative(Performatives.to_propagate);
	public PropagateMessage to_propagate;
	private boolean received = false;

	public WaitPropagateBehaviour(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("WaitPropagateBehaviour");
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(propagate_template);
		if (message != null) {
			try {
				Object serial = message.getContentObject();
				if (serial instanceof PropagateMessage) {
					to_propagate = (PropagateMessage) serial;
					received = true;
				}
			} catch (UnreadableException e) { e.printStackTrace(); }
		} else { block(); }
	}

	@Override
	public boolean done() {
		return received;
	}

	@Override
	public int onEnd() {
		SellerApplication.controller.getAnnounce().setDisable(false);
		return super.onEnd();
	}
}
