package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
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
import static View_Controller.MainScreenController.getModifyPartIndex;

public class ModifyPartScreenController implements Initializable {

    @FXML
    private Label ModifyPartIdLabel;
    @FXML
    private TextField ModifyPartNameField;
    @FXML
    private TextField ModifyPartInvField;
    @FXML
    private TextField ModifyPartPriceField;
    @FXML
    private TextField ModifyPartMaxField;
    @FXML
    private TextField ModifyPartMinField;
    @FXML
    private TextField ModifyPartTypeField;

    @FXML
    private Label ModifyPartTypeLabel;

    @FXML
    private RadioButton ModifyPartInHouseRadio;

    @FXML
    private RadioButton ModifyPartOutsourcedRadio;

    @FXML
    private boolean isOutsourced;





    int modifyIndex = getModifyPartIndex();
    private String exceptionMessage = new String();
    private int partId;


    @FXML
    private void inHouseRadioModifyPart(ActionEvent event) {
        isOutsourced = false;
        ModifyPartOutsourcedRadio.setSelected(false);
        ModifyPartTypeLabel.setText("Machine ID");
        ModifyPartTypeField.setText("");
        ModifyPartTypeField.setPromptText("Machine ID");

        System.out.println("inhouse Radio Modify Btn");
    }

    @FXML
    private void outsourcedRadioModifyPart(ActionEvent event) {
        isOutsourced = true;
        ModifyPartOutsourcedRadio.setSelected(false);
        ModifyPartTypeLabel.setText("Company Name");
        ModifyPartTypeField.setText("");
        ModifyPartTypeField.setPromptText("Company Name");
        System.out.println("outsourced Radio Modify Btn");
    }

    @FXML
    private void saveBtnModifyPart(ActionEvent event) throws IOException {
        System.out.println("Save btn modify part");
        String partName = ModifyPartNameField.getText();
        String partInv = ModifyPartInvField.getText();
        String partPrice = ModifyPartPriceField.getText();
        String partMin = ModifyPartMinField.getText();
        String partMax = ModifyPartMaxField.getText();
        String partType = ModifyPartTypeField.getText();

        try{
            exceptionMessage = Part.partValidation(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), exceptionMessage);
            if (exceptionMessage.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else{
                if (isOutsourced == false){
                    System.out.println("Part name: " + partName);
                    InHousePart inHousePart = new InHousePart();
                    inHousePart.setId(partId);
                    inHousePart.setName(partName);
                    inHousePart.setStock(Integer.parseInt(partInv));
                    inHousePart.setPrice(Double.parseDouble(partPrice));
                    inHousePart.setMin(Integer.parseInt(partMin));
                    inHousePart.setMax(Integer.parseInt(partMax));
                    inHousePart.setMachineId(Integer.parseInt(partType));
                    Inventory.updatePart(modifyIndex, inHousePart);
                }
                else{
                    System.out.print("Part name: " + partName);
                    OutsourcedPart outsourcedPart = new OutsourcedPart();
                    outsourcedPart.setId(partId);
                    outsourcedPart.setName(partName);
                    outsourcedPart.setStock(Integer.parseInt(partInv));
                    outsourcedPart.setPrice(Double.parseDouble(partPrice));
                    outsourcedPart.setMin(Integer.parseInt(partMin));
                    outsourcedPart.setMax(Integer.parseInt(partMax));
                    outsourcedPart.setCompanyName(partType);
                    Inventory.updatePart(modifyIndex, outsourcedPart);
                }

                Parent modifySave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifySave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
            alert.setContentText("Form contains blank fields");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelBtnModifyPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Modify");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Cancel modifying part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            showMainScreen(event);
        }
        else{
            System.out.println("You clicked cancel");
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
        Part part = getAllParts().get(modifyIndex);
        partId = getAllParts().get(modifyIndex).getId();
        ModifyPartIdLabel.setText("Auto-Gen: " + partId);
        ModifyPartNameField.setText(part.getName());
        ModifyPartInvField.setText(Integer.toString(part.getStock()));
        ModifyPartPriceField.setText(Double.toString(part.getPrice()));
        ModifyPartMinField.setText(Integer.toString(part.getMin()));
        ModifyPartMaxField.setText(Integer.toString(part.getMax()));
        if (part instanceof InHousePart){
            ModifyPartTypeLabel.setText("Machine ID");
            ModifyPartTypeField.setText(Integer.toString(((InHousePart) getAllParts().get(modifyIndex)).getMachineId()));
            ModifyPartInHouseRadio.setSelected(true);
        }
        else{
            ModifyPartTypeLabel.setText("Company Name");
            ModifyPartTypeField.setText(((OutsourcedPart) getAllParts().get(modifyIndex)).getCompanyName());
            ModifyPartOutsourcedRadio.setSelected(true);
        }
    }
}
