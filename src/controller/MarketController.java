package controller;


import app.MarketApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.AuctionMarketElement;

public class MarketController {
    @FXML
    private TableColumn<AuctionMarketElement, String> sellers;
    @FXML
    private TableColumn<AuctionMarketElement, String> packs;
    @FXML
    private TableColumn<AuctionMarketElement, Number> prices;
    @FXML
    private TableView<AuctionMarketElement> auctions;
    @FXML
    private AnchorPane root;

    private MarketApplication app;

    public MarketController() {}

    @FXML
    private void initialize() {
        sellers.setCellValueFactory(cellData -> cellData.getValue().sellerProperty());
        packs.setCellValueFactory(cellData -> cellData.getValue().packProperty());
        prices.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
    }

    public void setApp(MarketApplication app) {
        this.app = app;
        auctions.setItems(app.getObservableAuctions());
    }
}
