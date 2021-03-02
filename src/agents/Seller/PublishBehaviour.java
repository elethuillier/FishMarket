package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import shared.messages.PublishMessage;
import shared.model.Auction;

public class PublishBehaviour extends OneShotBehaviour {
	public PublishMessage to_publish;
	Seller seller;

	public PublishBehaviour(PublishMessage message, Seller seller) {
		this.to_publish = message;
		this.seller = seller;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("PublishBehaviour");
	}

	@Override
	public void action() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("market");
		dfd.addServices(sd);

		DFAgentDescription[] result;
		try {
			result = DFService.search(myAgent, dfd);
			System.out.println(result.length + " results");
			if (result.length > 0) {
				System.out.println(" " + result[0].getName());
				seller.market = result[0].getName();
			}
		} catch (FIPAException e1) { e1.printStackTrace(); }

		ACLMessage messageLoc = new ACLMessage(shared.Performatives.to_publish);
		messageLoc.addReceiver(seller.market);
		try { messageLoc.setContentObject(to_publish); } catch (IOException e) { e.printStackTrace(); }
		messageLoc.setSender(myAgent.getAID());
		myAgent.send(messageLoc);

		seller.myAuction = new Auction(to_publish.getPack(), myAgent.getAID(), to_publish.getRisingStep(), to_publish.getFallingStep(), to_publish.getCooldown());
		seller.myAuction.setCurrentPrice(to_publish.getPack().getStartPrice());
	}
}