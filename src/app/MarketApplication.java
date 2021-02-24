package app;

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
import model.AuctionElement;

import java.io.IOException;

public class MarketApplication extends Application {
    public static MarketApplication self;
    private final ObservableList<AuctionElement> observable_auctions = FXCollections.observableArrayList();
    private final String fxml_url = "../view/market.fxml";

    @FXML
    private AnchorPane root;

    public MarketApplication() {
        self = this;
        Boot.main(new String[]{"-gui", "FishMarket:agents.market.Market()"});
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml_url));
        addTestData();
        try {
            root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            MarketController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTestData() {
        for(int i=0; i<5; i++) {
            observable_auctions.add(new AuctionElement("seller", "daurade", (double) i));
        }
    }

    public ObservableList<AuctionElement> getObservableAuctions() {
        return observable_auctions;
    }
}
