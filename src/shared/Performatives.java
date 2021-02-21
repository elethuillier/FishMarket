package shared;

import jade.lang.acl.ACLMessage;

public class Performatives {
    public static final int to_announce = ACLMessage.CFP; // "Call For Proposal"
    public static final int to_attribute = ACLMessage.ACCEPT_PROPOSAL;
    public static final int to_give = ACLMessage.AGREE;
    public static final int rep_bid = ACLMessage.INFORM;
    public static final int to_bid = ACLMessage.PROPOSE;
    public static final int to_pay = ACLMessage.CONFIRM;

    public static final int to_subscribe = ACLMessage.SUBSCRIBE;
    public static final int to_broadcast = ACLMessage.PROPAGATE;
    public static final int broker = ACLMessage.PROXY;
}
