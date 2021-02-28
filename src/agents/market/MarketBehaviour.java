package agents.market;

import app.MarketApplication;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.AuctionMarketElement;
import shared.model.Auction;
import shared.Performatives;
import shared.messages.*;
import shared.Utils.IdMapException;

import java.io.IOException;
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
                            Auction auction = new Auction(publish.getPack(), message.getSender(), publish.getRisingStep(), publish.getFallingStep(), publish.getCooldown());
                            market.auctions.add(auction);
                            MarketApplication.self.getObservableAuctions().add(new AuctionMarketElement(auction.getId(), auction.getSeller().getName(), auction.getPack(), auction.getCurrentPrice()));

                            ACLMessage response = new ACLMessage(Performatives.to_propagate);
                            PropagateMessage propagate = new PropagateMessage(auction.hashCode(), auction.getSeller(), auction.getPack());
                            response.setContentObject(propagate);

                            MarketApplication.self.getObservableAuctions().add(
                                    new AuctionMarketElement(auction.getId(), propagate.getSellerId().getName(), propagate.getPack(), propagate.getPack().getStartPrice())
                            );

                            myAgent.send(response);
                        }
                        break;
                    } catch (UnreadableException | IOException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_subscribe:
                    try {
                        Object serial = message.getContentObject();
                        if(serial instanceof SubscribeMessage) {
                            SubscribeMessage subscribe = (SubscribeMessage) serial;
                            Auction auction = getAuctionFromId(subscribe.getAuctionId());
                            auction.getSubscribers().add(message.getSender());
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
                            auction.setCurrentPrice(announce.getPrice());
                            MarketApplication.self.getAuctionFromId(auction.getId()).setPrice(auction.getCurrentPrice());
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
                            auction.getOfferers().push(bid.getBuyerId());

                            message.clearAllReceiver();
                            message.addReceiver(auction.getSeller());
                            message.setSender(myAgent.getAID());
                            myAgent.send(message);
                        }
                    } catch (UnreadableException | IdMapException e) {
                        e.printStackTrace();
                    }
                case Performatives.rep_bid:
                    try {
                        Object serial = message.getContentObject();
                        if(serial instanceof RepBidMessage) {
                            RepBidMessage repbid = (RepBidMessage) serial;
                            Auction auction = getAuctionFromId(repbid.getAuctionId());
                            message.clearAllReceiver();

                            if(repbid.getState() == RepBidMessage.State.OK) {
                                message.addReceiver(auction.getOfferers().pop());
                            } else {
                                for(int i = 0 ; i < auction.getOfferers().size() ; i++) {
                                    message.addReceiver(auction.getOfferers().pop());
                                }
                            }
                            market.send(message);
                            break;
                        }
                    } catch (UnreadableException | IdMapException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_attribute:
                    try {
                        Object serial = message.getContentObject();
                        if (serial instanceof AttributeMessage) {
                            AttributeMessage attribute = (AttributeMessage) serial;
                            message.clearAllReceiver();
                            message.addReceiver(attribute.getBuyerId());
                            message.setSender(market.getAID());
                            market.send(message);
                        }
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_pay:
                    try {
                        Object serial = message.getContentObject();
                        if (serial instanceof PayMessage) {
                            PayMessage pay = (PayMessage) serial;
                            message.clearAllReceiver();
                            Auction auction = getAuctionFromId(pay.getAuctionId());
                            message.addReceiver(auction.getSeller());
                            message.setSender(market.getAID());
                            market.send(message);
                        }
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                case Performatives.to_give:
                    try {
                        Object serial = message.getContentObject();
                        if (serial instanceof GiveMessage) {
                            GiveMessage give = (GiveMessage) serial;
                            message.clearAllReceiver();
                            Auction auction = getAuctionFromId(give.getAuctionId());
                            message.addReceiver(auction.getOfferers().peek());
                            message.setSender(market.getAID());
                            market.send(message);

                            AuctionMarketElement observable_auction = MarketApplication.self.getAuctionFromId(auction.getId());
                            observable_auction.setDone(true); observable_auction.checkDone.run();

                            auction.setDone(true);
                        }
                    } catch (UnreadableException | IdMapException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
