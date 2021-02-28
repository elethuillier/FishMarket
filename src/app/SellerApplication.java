package app;

import controller.SellerController;
import jade.Boot;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AuctionBuyerElement;
import model.AuctionMarketElement;
import model.AuctionSellerElement;
import shared.EnvConfiguration;
import shared.Utils;
import shared.model.Pack;

import java.io.IOException;
import java.util.Optional;

public class SellerApplication extends Application {
    public static SellerApplication self;
    public static SellerController controller;
    private static String agent_name;

    private final ObservableList<AuctionSellerElement> observable_auctions = FXCollections.observableArrayList();

    @FXML
    private AnchorPane root;

    public SellerApplication() {
        super();
        self = this;
    }

    public static void main(String[] args) {
        if(args.length > 0 && !args[0].isEmpty()) agent_name = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/seller.fxml"));
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
        Boot.main(new String[]{"-container", "-host", "localhost", "-port", EnvConfiguration.default_port, agent_name + ":agents.Seller.Seller()"});
    }

    public AuctionSellerElement getAuctionFromId(int id) throws Utils.IdMapException {
        Optional<AuctionSellerElement> optional_auction = observable_auctions.stream().filter(auction -> auction.getId() == id).findFirst();
        return optional_auction.orElseThrow(() -> new Utils.IdMapException("Auction with id " + id + " not found"));
    }

    public ObservableList<AuctionSellerElement> getObservableAuctions() {
        return observable_auctions;
    }

    private void addTestData() {
        for(int i=0; i<5; i++) {
            observable_auctions.add(new AuctionSellerElement(i, new Pack(10,"huitre"), i, "jean-rémi"));
        }
    }
}
