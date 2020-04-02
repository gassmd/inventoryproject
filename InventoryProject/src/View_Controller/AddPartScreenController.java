package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        System.out.println("inhouse radio press!");
        isOutsourced = false;

    }

    public void outsourcedRadioAddPart(ActionEvent event) {
        CompIdLabelToggle.setText("Company Name");
        CompFieldAddPart.setPromptText("Company Name");
        System.out.println("outsource radio press!");
        isOutsourced = true;
    }



    public void cancelBtnAddPart(ActionEvent event) throws IOException {
        showMainScreen(event);
        System.out.println("cancel button press!");
    }

    public void showMainScreen(ActionEvent event) throws IOException{
        Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(mainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void saveBtnAddPart(ActionEvent event) throws IOException {
        System.out.println("save button clicked!");
        String partName = NameFieldAddPart.getText();
        String partInv = InvFieldAddPart.getText();
        String partPrice = PriceFieldAddPart.getText();
        String partMax = MaxFieldAddPart.getText();
        String partMin = MinFieldAddPart.getText();
        String partComp = CompFieldAddPart.getText();

        try {
            errorMessage = Part.partValidation(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                if (!isOutsourced) {
                    System.out.println("Part name: " + partName);
                    System.out.println(" Inv: " + partInv);
                    System.out.println("Priceru: " + partPrice);
                    System.out.println("Max " + partMax);
                    System.out.println("Min " + partMin);
                    System.out.println("Comp name: " + partComp);
                    System.out.println("Outsourced : " + isOutsourced);
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
                    System.out.println("Part name: " + partName);
                    System.out.println("Outsourced : " + isOutsourced);
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
                else{
                    System.out.println("Something wrong here buddy");
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
            alert.setContentText("Form contains blank fields");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId = Inventory.getPartIdCount();
        IdFieldAddPart.setText("Auto Gen: " + partId);
    }
}

