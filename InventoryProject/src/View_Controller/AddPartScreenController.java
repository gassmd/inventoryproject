package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartScreenController implements Initializable {

    @FXML
    private Label CompIdLabelToggle;
    @FXML
    private TextField IdFieldAddPart;
    @FXML
    private TextField NameFieldAddPart;
    @FXML
    private TextField InvFieldAddPart;
    @FXML
    private TextField PriceFieldAddPart;
    @FXML
    private TextField MaxFieldAddPart;
    @FXML
    private TextField MinFieldAddPart;
    @FXML
    private TextField CompFieldAddPart;

    private boolean isOutsourced;
    private String errorMessage = new String();
    private int partId;


    public void inHouseRadioAddPart(ActionEvent event) {
        CompIdLabelToggle.setText("Machine ID");
        CompFieldAddPart.setPromptText("Machine ID");
        isOutsourced = false;

    }                                                               //inHouse / outsourced radio button handlers. Changes boolean and label/ field prompt text upon selection

    public void outsourcedRadioAddPart(ActionEvent event) {
        CompIdLabelToggle.setText("Company Name");
        CompFieldAddPart.setPromptText("Company Name");
        isOutsourced = true;
    }



    public void cancelBtnAddPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel add part");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Cancel adding new part?");
        Optional<ButtonType> result = alert.showAndWait();                  // cancel confirmation

        if (result.get() == ButtonType.OK) {
            showMainScreen(event);
        }
    }

    public void showMainScreen(ActionEvent event) throws IOException {
        Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(mainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);                                                         // returns to main screen
        window.show();
    }

    @FXML
    void saveBtnAddPart(ActionEvent event) throws IOException {
        String partName = NameFieldAddPart.getText();
        String partInv = InvFieldAddPart.getText();
        String partPrice = PriceFieldAddPart.getText();                                         // sets parameters in text fields to each variable
        String partMax = MaxFieldAddPart.getText();
        String partMin = MinFieldAddPart.getText();
        String partComp = CompFieldAddPart.getText();

        try {
            errorMessage = Part.partValidation(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");                       //Part validation, prints corresponding error message from Part if error arises.
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                if (!isOutsourced) {
                    InHousePart inHousePart = new InHousePart();
                    inHousePart.setId(partId);
                    inHousePart.setName(partName);
                    inHousePart.setStock(Integer.parseInt(partInv));
                    inHousePart.setPrice(Double.parseDouble(partPrice));
                    inHousePart.setMax(Integer.parseInt(partMax));
                    inHousePart.setMin(Integer.parseInt(partMin));
                    inHousePart.setMachineId(Integer.parseInt(partComp));
                    Inventory.addPart(inHousePart);
                } else if (isOutsourced) {
                    OutsourcedPart outsourcedPart = new OutsourcedPart();
                    outsourcedPart.setId(partId);
                    outsourcedPart.setName(partName);
                    outsourcedPart.setStock(Integer.parseInt(partInv));
                    outsourcedPart.setPrice(Double.parseDouble(partPrice));
                    outsourcedPart.setMax(Integer.parseInt(partMax));
                    outsourcedPart.setMin(Integer.parseInt(partMin));
                    outsourcedPart.setCompanyName(partComp);
                    Inventory.addPart(outsourcedPart);
                }
                Parent partsSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(partsSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains blank fields");                         // Catches blank fields or incorrect formatting
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId = Inventory.getPartIdCount();
        IdFieldAddPart.setText("Auto Gen: " + partId);
    }
}

