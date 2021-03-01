package controller;

import app.BuyerApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.AuctionBuyerElement;
import shared.Utils;
import shared.Utils.ControlMode;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BuyerController {
    @FXML
    private Button manual;
    @FXML
    private TextField limit;
    @FXML
    private Button auto;
    @FXML
    private TableView<AuctionBuyerElement> auctions;
    @FXML
    private TableColumn<AuctionBuyerElement, String> sellers;
    @FXML
    private TableColumn<AuctionBuyerElement, String> packs;
    @FXML
    private TableColumn<AuctionBuyerElement, Number> prices;
    @FXML
    private Button subscribe;
    @FXML
    private Button propose;
    @FXML
    private Label info;

    private BuyerApplication app;

    public BuyerController() {}

    @FXML
    private void initialize() {
        sellers.setCellValueFactory(cellData -> cellData.getValue().sellerProperty());
        packs.setCellValueFactory(cellData -> cellData.getValue().packProperty());
        prices.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
    }

    public void setApp(BuyerApplication app) {
        this.app = app;
        auctions.setItems(app.getObservableAuctions());
    }

    public void setModeListener(BiConsumer<ControlMode, Double> callback) {
        manual.setOnMouseClicked(event -> {
            String strLimit = limit.getText();
            if(!strLimit.isEmpty() && Utils.isNumeric(strLimit)) {
                callback.accept(ControlMode.MANUAL, Double.parseDouble(strLimit));
            }
        });

        auto.setOnMouseClicked(event -> {
            String strLimit = limit.getText();
            if(!strLimit.isEmpty() && Utils.isNumeric(strLimit)) {
                callback.accept(ControlMode.AUTO, Double.parseDouble(strLimit));
            }
        });
    }

    public void setSubscribeListener(Consumer<AuctionBuyerElement> callback) {
        subscribe.setOnMouseClicked(event -> callback.accept(auctions.getSelectionModel().getSelectedItem()));
    }

    public void setBidListener(Consumer<AuctionBuyerElement> callback) {
        propose.setOnMouseClicked(event -> callback.accept(auctions.getSelectionModel().getSelectedItem()));
    }
}
