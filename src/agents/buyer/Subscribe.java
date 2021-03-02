package agents.buyer;

import java.io.IOException;

import app.BuyerApplication;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.AuctionBuyerElement;
import shared.messages.PropagateMessage;
import shared.messages.SubscribeMessage;

public class Subscribe extends CyclicBehaviour {
	private final Buyer buyer;
	public PropagateMessage to_propagate;

	public Subscribe(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		Object serial = null;
		if (message != null) {
			if (message.getPerformative() == shared.Performatives.to_propagate) {
				buyer.market_aid = message.getSender();
				try {
					serial = message.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (serial != null) {
					if (serial instanceof PropagateMessage) {
						to_propagate = (PropagateMessage) serial;
						BuyerApplication.self.getObservableAuctions().add(new AuctionBuyerElement(to_propagate.getAuctionId(), to_propagate.getSellerId().getLocalName(), to_propagate.getPack(), to_propagate.getPack().getStartPrice()));
					}
				}
			}
		}
	}
}
