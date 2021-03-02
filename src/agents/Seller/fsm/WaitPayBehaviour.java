package agents.Seller.fsm;

import agents.Seller.AutomataBehaviour;
import agents.Seller.Seller;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import shared.Performatives;
import shared.messages.PayMessage;

public class WaitPayBehaviour extends Behaviour {
	private final Seller seller;
	private final MessageTemplate pay_template = MessageTemplate.MatchPerformative(Performatives.to_pay);
	private int event;

	public WaitPayBehaviour(Seller seller) {
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("WaitPayBehaviour");
		event = AutomataBehaviour.NO_EVENT;
	}

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());
		ACLMessage message = myAgent.receive(pay_template);
		if (message != null) {
			try {
				Object serial = message.getContentObject();
				if (serial instanceof PayMessage) {
					event = AutomataBehaviour.GIVE_EVENT;
				}
			} catch (UnreadableException e) { e.printStackTrace(); }
		} else {
			block();
		}
	}

	@Override
	public int onEnd() {
		return event;
	}

	@Override
	public boolean done() {
		return event == AutomataBehaviour.GIVE_EVENT;
	}
}
