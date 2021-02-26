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
import model.AuctionMarketElement;
import shared.EnvConfiguration;

import java.io.IOException;

public class MarketApplication extends Application {
    public static MarketApplication self;
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

            MarketController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTestData() {
        for(int i=0; i<5; i++) {
            observable_auctions.add(new AuctionMarketElement("seller", "daurade", (double) i));
        }
    }

    public ObservableList<AuctionMarketElement> getObservableAuctions() {
        return observable_auctions;
    }
}
