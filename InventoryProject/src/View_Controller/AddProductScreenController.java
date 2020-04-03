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

import static Model.Inventory.getAllParts;

public class AddProductScreenController implements Initializable {

    @FXML
    private Label IdLabelAddProduct;
    @FXML
    private TextField AddProductSearchField;
    @FXML
    private TableView<Part> DeleteTableAddProduct;
    @FXML
    private TableColumn<Part, Integer> IdColAddProduct;
    @FXML
    private TableColumn<Part, String> NameColAddProduct;
    @FXML
    private TableColumn<Part, Double> PriceColAddProduct;
    @FXML
    private TableColumn<Part, Integer> InvColAddProduct;
    @FXML
    private TableView<Part> AddTableAddProduct;
    @FXML
    private TableColumn<Part, Integer> IdColDeleteProduct;
    @FXML
    private TableColumn<Part, String> NameColDeleteProduct;
    @FXML
    private TableColumn<Part, Double> PriceColDeleteProduct;
    @FXML
    private TableColumn<Part, Integer> InvColDeleteProduct;
    @FXML
    private TextField IdFieldAddProduct;
    @FXML
    private TextField NameFieldAddProduct;
    @FXML
    private TextField InvFieldAddProduct;
    @FXML
    private TextField PriceFieldAddProduct;
    @FXML
    private TextField MaxFieldAddProduct;
    @FXML
    private TextField MinFieldAddProduct;


    private String errorMessage = new String();
    private int productId;
    private ObservableList<Part> partsList = FXCollections.observableArrayList();


    @FXML
    void addBtnAddProduct(ActionEvent event){
        Part part = AddTableAddProduct.getSelectionModel().getSelectedItem();
        partsList.add(part);                                                                // updates and adds product to table
        updateDeleteTableView();
    }

    @FXML
    void deleteBtnAddProduct(ActionEvent event) {
        Part part = DeleteTableAddProduct.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm deletion");
        alert.setContentText("Delete " + part.getName() + " from parts?");                  // deletion confirmation
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            partsList.remove(part);
        }
    }

    @FXML
    void saveBtnAddProduct(ActionEvent event) throws IOException {
        String productName = NameFieldAddProduct.getText();
        String productInv = InvFieldAddProduct.getText();
        String productPrice = PriceFieldAddProduct.getText();
        String productMax = MaxFieldAddProduct.getText();
        String productMin = MinFieldAddProduct.getText();

        try{
            errorMessage = Product.productValidation(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), partsList, errorMessage);
            if (errorMessage.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else{
                Product product = new Product();
                product.setId(productId);
                product.setName(productName);
                product.setStock(Integer.parseInt(productInv));
                product.setPrice(Double.parseDouble(productPrice));
                product.setMin(Integer.parseInt(productMin));
                product.setMax(Integer.parseInt(productMax));
                Inventory.addProduct(product);

                Parent addProductParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene (addProductParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Form contains blank fields");
            alert.showAndWait();
        }
    }

    @FXML
    void cancelBtnAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel add product");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Cancel adding new product?");                      // cancel confirmation
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            showMainScreen(event);
        }
    }

    @FXML
    void searchBtnAddProduct(ActionEvent event) {
        String searchTerm = AddProductSearchField.getText();
        int index = -1;
        if (Inventory.lookupPart(searchTerm) == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search error");
            alert.setHeaderText("Part not found");
            alert.setContentText("Search term not found");                      // searches through list to return search term
            alert.showAndWait();
        }
        else {
            index = Inventory.lookupPart(searchTerm);
            Part temp = getAllParts().get(index);
            ObservableList<Part> tempList = FXCollections.observableArrayList();
            tempList.add(temp);
            AddTableAddProduct.setItems(tempList);
        }
    }

    public void showMainScreen(ActionEvent event) throws IOException {
        Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));             //shows main screen
        Scene scene = new Scene(mainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void updateAddTableView(){
        AddTableAddProduct.setItems(getAllParts());                                         //update table views
    }

    public void updateDeleteTableView(){
        DeleteTableAddProduct.setItems(partsList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdColAddProduct.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        NameColAddProduct.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        PriceColAddProduct.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        InvColAddProduct.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        IdColDeleteProduct.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        NameColDeleteProduct.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        PriceColDeleteProduct.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        InvColDeleteProduct.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        updateAddTableView();
        updateDeleteTableView();
        productId = Inventory.getProductIdCount();
        IdLabelAddProduct.setText("Auto-Gen: " + productId);
    }
}
