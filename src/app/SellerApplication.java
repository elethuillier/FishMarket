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
import model.AuctionSellerElement;
import shared.EnvConfiguration;
import shared.Pack;

import java.io.IOException;

public class SellerApplication extends Application {
    public static SellerApplication self;
    private static String agent_name;

    private final ObservableList<AuctionSellerElement> observable_auctions = FXCollections.observableArrayList();

    @FXML
    private AnchorPane root;

    public SellerApplication() {
        super();
        self = this;
        Boot.main(new String[]{"-local-port", EnvConfiguration.default_port, agent_name + ":agents.Seller.Seller()"});
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

            SellerController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AuctionSellerElement> getObservableAuctions() {
        return observable_auctions;
    }

    private void addTestData() {
        for(int i=0; i<5; i++) {
            observable_auctions.add(new AuctionSellerElement(new Pack(10,"huitre"), i, "jean-rémi"));
        }
    }
}
