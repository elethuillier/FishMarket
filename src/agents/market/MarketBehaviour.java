package agents.market;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.Auction;
import shared.Performatives;
import shared.messages.AnnounceMessage;
import shared.messages.BidMessage;
import shared.messages.PublishMessage;
import shared.messages.SubscribeMessage;

import java.util.Optional;

public class MarketBehaviour extends CyclicBehaviour {
    private final Market market;

    public MarketBehaviour(Market market) {
        super(market);
        this.market = market;
    }

    private Auction getAuctionFromId(int id) throws IdMapException {
        Optional<Auction> optional_auction = market.auctions.stream().filter(auction -> auction.getId() == id).findFirst();
        return optional_auction.orElseThrow(() -> new IdMapException("Auction with id " + id + " not found"));
    }

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if(message != null) {
            switch(message.getPerformative()) {
                case Performatives.to_publish:
                    try {
                        Object serial = message.getContentObject();
                        if(serial instanceof PublishMessage) {
                            PublishMessage publish = (PublishMessage) serial;
                            market.auctions.add(publish.getAuction());
                            message.clearAllReceiver();
                            message.setSender(market.getAID());
                            market.send(message);
                        }
                        break;
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_subscribe:
                    try {
                        Object serial = message.getContentObject();
                        if(serial instanceof SubscribeMessage) {
                            SubscribeMessage subscribe = (SubscribeMessage) serial;
                            Auction auction = getAuctionFromId(subscribe.getAuctionId());
                            if(!auction.getSubscribers().contains(message.getSender())) {
                                auction.getSubscribers().add(message.getSender());
                            }
                        }
                        break;
                    } catch (UnreadableException | IdMapException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_announce:
                    try {
                        Object serial = message.getContentObject();
                        if(serial instanceof AnnounceMessage) {
                            AnnounceMessage announce = (AnnounceMessage) serial;
                            Auction auction = getAuctionFromId(announce.getAuctionId());
                            auction.setCurrentPrice(auction.getCurrentPrice());

                            message.clearAllReceiver();
                            for(AID sub_id : auction.getSubscribers()) {
                                message.addReceiver(sub_id);
                            }
                            market.send(message);
                            break;
                        }
                    } catch (UnreadableException | IdMapException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_bid:
                    try {
                        Object serial = message.getContentObject();
                        if(serial instanceof BidMessage) {
                            BidMessage bid = (BidMessage) serial;
                            Auction auction = getAuctionFromId(bid.getAuctionId());
                            auction.getOfferers().add(message.getSender());
                        }
                    } catch (UnreadableException | IdMapException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public class IdMapException extends RuntimeException {
        public IdMapException(String message) {
            super(message);
        }
    }
}
