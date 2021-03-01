package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import shared.messages.PublishMessage;

public class Publish extends OneShotBehaviour {
	public PublishMessage to_publish;
	Seller seller;

	public Publish(PublishMessage message, Seller seller) {
		this.to_publish = message;
		this.seller = seller;
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
			if (result.length > 0)
				System.out.println(" " + result[0].getName());
			seller.destinataire = result[0].getName();

		} catch (FIPAException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ACLMessage messageLoc = new ACLMessage(shared.Performatives.to_publish);
		try {
			messageLoc.setContentObject(to_publish);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messageLoc.clearAllReceiver();
		messageLoc.setSender(myAgent.getAID());
		myAgent.send(messageLoc);

		myAgent.addBehaviour(new Wait_Propagate(seller));

	}
}