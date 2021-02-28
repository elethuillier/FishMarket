package app;

import agents.market.MarketBehaviour;
import controller.MarketController;
import jade.Boot;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AuctionMarketElement;
import shared.EnvConfiguration;
import shared.Utils;
import shared.model.Auction;
import shared.model.Pack;

import java.io.IOException;
import java.util.Optional;

public class MarketApplication extends Application {
    public static MarketApplication self;
    public static MarketController controller;
    private static String agent_name;
    private final ObservableList<AuctionMarketElement> observable_auctions = FXCollections.observableArrayList();

    @FXML
    private AnchorPane root;

    public MarketApplication() {
        self = this;
        Boot.main(new String[]{"-local-port", EnvConfiguration.default_port, "-gui", agent_name + ":agents.market.Market()"});
    }

    public static void main(String[] args) {
        if(args.length > 0 && !args[0].isEmpty()) agent_name = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/market.fxml"));
        addTestData();
        try {
            root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTestData() {
        for(int i=0; i<5; i++) {
            observable_auctions.add(new AuctionMarketElement(i,"seller", new Pack(10, "daurade"), (double) i));
        }
        observable_auctions.get(0).setDone(true);
    }

    public AuctionMarketElement getAuctionFromId(int id) throws Utils.IdMapException {
        Optional<AuctionMarketElement> optional_auction = observable_auctions.stream().filter(auction -> auction.getId() == id).findFirst();
        return optional_auction.orElseThrow(() -> new Utils.IdMapException("Auction with id " + id + " not found"));
    }

    public ObservableList<AuctionMarketElement> getObservableAuctions() {
        return observable_auctions;
    }
}
