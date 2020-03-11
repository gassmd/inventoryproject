package mainScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class mainScreenController {
    @FXML
    void partSearchBtn(ActionEvent event){
        System.out.println("Part search button pressed!");
    }
    @FXML
    void partAddBtn(ActionEvent event){
        System.out.println("Part add button pressed!");
    }
    @FXML
    void partModifyBtn(ActionEvent event){
            System.out.println("Part modify pressed!");
        }
    @FXML
    void partDeleteBtn(ActionEvent event){
            System.out.println("Part delete pressed!");
        }
    @FXML
    void productSearchBtn(ActionEvent event){
        System.out.println("Product search button pressed!");
    }
    @FXML
    void productAddBtn(ActionEvent event){
            System.out.println("Product add pressed!");
        }
    @FXML
    void productModifyBtn(ActionEvent event){
        System.out.println("Product modify pressed!");
    }
    @FXML
    void productDeleteBtn(ActionEvent event){
        System.out.println("Product delete pressed!");
    }


}
