package agents.Seller;

import java.io.IOException;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import shared.messages.AttributeMessage;

public class AttributePack extends Behaviour{
	private final Seller seller;

	public AttributePack(Seller seller) {
		this.seller = seller;
	}
	
    @Override
    public void action() {
        System.out.println(myAgent.getAID().getName()+ " attribute offer : ");
        ACLMessage message = new ACLMessage(shared.Performatives.to_attribute);
        AttributeMessage attribute = new AttributeMessage(seller.my_auctionsID.get(0), seller.my_bids.get(seller.my_bids.size()-1).getBuyerId());
        try {
            message.setContentObject(attribute);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        message.clearAllReceiver();
        message.setSender(myAgent.getAID());
        myAgent.send(message);
    }


	@Override
	public boolean done() {
		// TODO 
		return false;
	}
	
    @Override
    public int onEnd() {
        return 0;
    }
}
