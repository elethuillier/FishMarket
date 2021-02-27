package controller;

import app.SellerApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.AuctionSellerElement;
import shared.Pack;
import shared.messages.PublishMessage;

import java.util.function.Consumer;

public class SellerController {
    @FXML
    private TextField name;
    @FXML
    private TextField initial_price;
    @FXML
    private TextField cooldown;
    @FXML
    private TextField rising_step;
    @FXML
    private TextField falling_step;
    @FXML
    private Button button_create;
    @FXML
    private TableView<AuctionSellerElement> auctions;
    @FXML
    private TableColumn<AuctionSellerElement, String> packs;
    @FXML
    private TableColumn<AuctionSellerElement, Number> prices;
    @FXML
    private TableColumn<AuctionSellerElement, String> buyers;
    @FXML
    private Label pack_label;
    @FXML
    private Label state_label;

    private SellerApplication app;

    @FXML
    private void initialize() {
        packs.setCellValueFactory(cellData -> cellData.getValue().packProperty());
        prices.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        buyers.setCellValueFactory(cellData -> cellData.getValue().buyerProperty());
    }

    public void setApp(SellerApplication app) {
        this.app = app;
        auctions.setItems(app.getObservableAuctions());
    }

    public void setStateLabel(String state) {
        this.state_label.setText(state);
    }

    public void setPackLabel(Pack pack) {
        this.pack_label.setText(pack.getDescription());
    }

    public void setCreateListener(Consumer<PublishMessage> callback) {
        button_create.setOnMouseClicked(event -> {
            String[] numeric_inputs = {initial_price.getText(), cooldown.getText(), rising_step.getText(), falling_step.getText()};
            if(!name.getText().isEmpty() && shared.Utils.areNumeric(numeric_inputs)) {
                Pack pack = new Pack(Double.parseDouble(initial_price.getText()), name.getText());
                PublishMessage message = new PublishMessage(pack,
                        Double.parseDouble(rising_step.getText()),
                        Double.parseDouble(falling_step.getText()),
                        (int) Double.parseDouble(cooldown.getText())
                );
                callback.accept(message);
            }
        });
    }
}
