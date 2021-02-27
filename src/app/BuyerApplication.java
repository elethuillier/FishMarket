package app;

import controller.BuyerController;
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
import shared.EnvConfiguration;
import shared.Pack;

import java.io.IOException;

public class BuyerApplication extends Application {
    public static BuyerApplication self;
    public static String agent_name = "Bob";
    public static String str_agent_budget = "100";
    public static BuyerController controller;

    private final ObservableList<AuctionBuyerElement> observable_auctions = FXCollections.observableArrayList();

    @FXML
    private AnchorPane root;

    public BuyerApplication() {
        super();
        self = this;
        Boot.main(new String[]{"-local-port", "1112", agent_name + str_agent_budget + ":agents.buyer.Buyer()"});
    }

    public static void main(String[] args) {
        if(args.length > 0 && !args[0].isEmpty() && !args[1].isEmpty()) {
        	agent_name = args[0];
        	str_agent_budget = args[1];
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/buyer.fxml"));
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

    public ObservableList<AuctionBuyerElement> getObservableAuctions() {
        return observable_auctions;
    }

    private void addTestData() {
        for(int i=0; i<5; i++) {
            observable_auctions.add(new AuctionBuyerElement("stéphane", new Pack(10, "saumon"), (double) i));
        }
    }
}
