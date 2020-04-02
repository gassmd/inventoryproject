package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static View_Controller.MainScreenController.getModifyProductIndex;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;

public class ModifyProductScreenController implements Initializable {

    private TextField SearchFieldModifyProduct;
    private ObservableList<Part> partsList = FXCollections.observableArrayList();
    private int modifyIndex = getModifyProductIndex();
    private String errorMessage = new String();
    private int productId;


    @FXML
    private Label IdLabelModifyProduct;
    @FXML
    private TextField NameFieldModifyProduct;
    @FXML
    private TextField InvFieldModifyProduct;
    @FXML
    private TextField PriceFieldModifyProduct;
    @FXML
    private TextField MaxFieldModifyProduct;
    @FXML
    private TextField MinFieldModifyProduct;
    @FXML
    private TableView<Part> AddTableModifyProduct;
    @FXML
    private TableColumn<Part, Integer> IdColAddTableModifyProduct;
    @FXML
    private TableColumn<Part, String> NameColAddTableModifyProduct;
    @FXML
    private TableColumn<Part, Integer> InvColAddTableModifyProduct;
    @FXML
    private TableColumn<Part, Double> PriceColAddTableModifyProduct;
    @FXML
    private TableView<Part> DeleteTableModifyProduct;
    @FXML
    private TableColumn<Part, Integer> IdColDeleteTableModifyProduct;
    @FXML
    private TableColumn<Part, String> NameColDeleteTableModifyProduct;
    @FXML
    private TableColumn<Part, Integer> InvColDeleteTableModifyProduct;
    @FXML
    private TableColumn<Part, Double> PriceColDeleteTableModifyProduct;

    public void addBtnModifyProduct(ActionEvent event) {
        System.out.println("Add btn modify product");
        Part part = AddTableModifyProduct.getSelectionModel().getSelectedItem();
        partsList.add(part);
        updateDeletePartsTable();
    }

    public void deleteBtnModifyProduct(ActionEvent event) {
        System.out.println("delete btn modify product");
        Part part = DeleteTableModifyProduct.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Delete " + part.getName() + " From parts?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            partsList.remove(part);
        }
        else{
            System.out.println("You clicked cancel");
        }
    }

    public void saveBtnModifyProduct(ActionEvent event) throws IOException{
        System.out.println("save btn modify product");
        String productName = NameFieldModifyProduct.getText();
        String productInv = InvFieldModifyProduct.getText();
        String productPrice = PriceFieldModifyProduct.getText();
        String productMin = MinFieldModifyProduct.getText();
        String productMax = MaxFieldModifyProduct.getText();

        try{
            errorMessage = Product.productValidation(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), partsList, errorMessage);
            if (errorMessage.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            }
            else {
                System.out.println("Product name: " + productName);
                Product product = new Product();
                product.setId(productId);
                product.setName(productName);
                product.setStock(Integer.parseInt(productInv));
                product.setPrice(Double.parseDouble(productPrice));
                product.setMin(Integer.parseInt(productMin));
                product.setMax(Integer.parseInt(productMax));
                Inventory.updateProduct(modifyIndex, product);

                showMainScreen(event);
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("Form contains blank fields");
            alert.showAndWait();
        }
    }

    public void cancelBtnModifyProduct(ActionEvent event) throws IOException {
        System.out.println("cancel btn modify product");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Modify");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Cancel modifying product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            showMainScreen(event);
        }
        else{
            System.out.println("You clicked cancel");
        }
    }

    public void searchBtnModifyProduct(ActionEvent event) {
        System.out.println("search btn modify product");
        String search = SearchFieldModifyProduct.getText();
        int index = -1;
        if (Inventory.lookupPart(search) == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search error");
            alert.setHeaderText("Part not found");
            alert.setContentText("Search term not found");
            alert.showAndWait();
        }
        else{
            index = Inventory.lookupPart(search);
            Part part = Inventory.getAllParts().get(index);
            ObservableList<Part> partList = FXCollections.observableArrayList();
            partList.add(part);
            AddTableModifyProduct.setItems(partList);
        }
    }

    public void showMainScreen(ActionEvent event) throws IOException {
        Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(mainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product product = getAllProducts().get(modifyIndex);
        productId = getAllProducts().get(modifyIndex).getId();
        IdLabelModifyProduct.setText("Auto-Gen: " + productId);
        NameFieldModifyProduct.setText(product.getName());
        InvFieldModifyProduct.setText(Integer.toString(product.getStock()));
        PriceFieldModifyProduct.setText(Double.toString(product.getPrice()));
        MinFieldModifyProduct.setText(Integer.toString(product.getMin()));
        MaxFieldModifyProduct.setText(Integer.toString(product.getMax()));
        partsList = product.getAllAssociatedParts();
        IdColAddTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        NameColAddTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        InvColAddTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        PriceColAddTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        IdColDeleteTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        NameColDeleteTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        InvColDeleteTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        PriceColDeleteTableModifyProduct.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        updateAddPartsTable();
        updateDeletePartsTable();
    }

    public void updateAddPartsTable(){
        AddTableModifyProduct.setItems(getAllParts());
    }

    public void updateDeletePartsTable(){
        DeleteTableModifyProduct.setItems(partsList);
    }
}
