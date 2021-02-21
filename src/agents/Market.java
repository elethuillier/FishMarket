package agents;

import jade.core.Agent;
import jade.lang.acl.MessageTemplate;
import shared.Performatives;

public class Market extends Agent {
    private final MessageTemplate broadcast_message = MessageTemplate.MatchPerformative(Performatives.to_broadcast);
    private final MessageTemplate broker_message = MessageTemplate.MatchPerformative(Performatives.broker);

    @Override
    protected void setup() {
        super.setup();
    }
}
