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
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.deletePart;
import static Model.Inventory.deleteProduct;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    private static Part modifyPart;
    private static int modifyPartIndex;
    private static Product modifyProduct;
    private static int modifyProductIndex;

    @FXML                                               // TextFields
    private TextField PartsSearchFieldMain;
    @FXML
    private TextField ProductsSearchFieldMain;

    @FXML                                               //Parts table and columns
    private TableView<Part> PartsTableMain;
    @FXML
    private TableColumn<Part, Integer> PartIdColPartsTableMain;
    @FXML
    private TableColumn<Part, String> PartNameColPartsTableMain;
    @FXML
    private TableColumn<Part, Integer> InventoryLevelColPartsTableMain;
    @FXML
    private TableColumn<Part, Double> PriceColPartsTableMain;

    @FXML                                                           //Products table and columns
    private TableView<Product> ProductsTableMain;
    @FXML
    private TableColumn<Product, Integer> ProductIdColProductsTableMain;
    @FXML
    private TableColumn<Product, String> ProductNameColProductsTableMain;
    @FXML
    private TableColumn<Product, Integer> InventoryLevelColProductsTableMain;
    @FXML
    private TableColumn<Product, Double> PriceColProductsTableMain;


    @FXML
    void partSearchBtnMain(ActionEvent event) throws IOException {
        String searchPart = PartsSearchFieldMain.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found!");
            alert.setContentText("Search term not found");
            alert.showAndWait();                                                    // searches through part list for all parts
        } else {
            partIndex = Inventory.lookupPart(searchPart);
            Part temp = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(temp);
            PartsTableMain.setItems(tempPartList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Successful");
            alert.setHeaderText("Search term " + searchPart + " found.");
            alert.setContentText("'OK' to continue");
            alert.showAndWait();
        }
    }

    @FXML
    void partAddBtnMain(ActionEvent event) throws IOException {
        showAddPartScreen(event);                                   // goes to add part screen
    }

    @FXML
    void partModifyBtnMain(ActionEvent event) throws IOException {
        showModifyPartScreen(event);                                    // goes to modify part screen
    }

    @FXML
    void partDeleteBtnMain(ActionEvent event) throws IOException {
        Part part = PartsTableMain.getSelectionModel().getSelectedItem();
       if (deletePart(part)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete error");
            alert.setHeaderText("Part cannot be deleted");
            alert.setContentText("Part currently in use");
            alert.showAndWait();
        }
        else{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                  // handles part deletion
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deletePart(part);
            updatePartsTable();
            System.out.println("Part " + part.getName() + " removed.");
        } else {
            System.out.println("Part " + part.getName() + " was not removed.");
        }
    }

}


    @FXML
    void productSearchBtnMain(ActionEvent event){
        String search = ProductsSearchFieldMain.getText();
        int index = -1;
        if (Inventory.lookupProduct(search)== -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search error");
            alert.setHeaderText("Product not found");
            alert.setContentText("Search term not found");
            alert.showAndWait();
        }
        else{
            index = Inventory.lookupProduct(search);
        }
        System.out.println("Product search button pressed!");
    }

    @FXML
    void productAddBtnMain(ActionEvent event) throws IOException {
            showAddProductScreen(event);
        }
    @FXML
    void productModifyBtnMain(ActionEvent event) throws IOException{
        modifyProduct = ProductsTableMain.getSelectionModel().getSelectedItem();
        modifyProductIndex = getAllProducts().indexOf(modifyProduct);
        showModifyProductScreen(event);
    }
    @FXML
    void productDeleteBtnMain(ActionEvent event) throws IOException {
        Product product = ProductsTableMain.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Product Deletion");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteProduct(product);
            updatePartsTable();
            System.out.println("Part " + product.getName() + " removed.");
        }
        else{
            System.out.println("Part " + product.getName() + " was not removed.");
        }
    }


    public void showAddPartScreen(ActionEvent event) throws IOException{
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));            // methods to show different screens
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void showAddProductScreen(ActionEvent event) throws IOException{
        Parent addProducts = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene scene = new Scene(addProducts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void showModifyPartScreen(ActionEvent event) throws IOException{
        modifyPart = PartsTableMain.getSelectionModel().getSelectedItem();
        modifyPartIndex = getAllParts().indexOf(modifyPart);
        Parent modifyParts = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
        Scene scene = new Scene(modifyParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void showModifyProductScreen(ActionEvent event) throws IOException{
        Parent modifyProducts = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
        Scene scene = new Scene(modifyProducts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public static int getModifyPartIndex(){
        return modifyPartIndex;
    }                                                               // get index of part/product to modify

    public static int getModifyProductIndex(){ return modifyProductIndex;}


    @Override
    public void initialize(URL url, ResourceBundle rb){
        PartIdColPartsTableMain.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        PartNameColPartsTableMain.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        InventoryLevelColPartsTableMain.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        PriceColPartsTableMain.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        ProductIdColProductsTableMain.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ProductNameColProductsTableMain.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        InventoryLevelColProductsTableMain.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        PriceColProductsTableMain.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        updatePartsTable();
        updateProductsTable();

    }
    public void updatePartsTable(){
        PartsTableMain.setItems(getAllParts());
    }

    public void updateProductsTable(){
        ProductsTableMain.setItems(getAllProducts());
    }

}
