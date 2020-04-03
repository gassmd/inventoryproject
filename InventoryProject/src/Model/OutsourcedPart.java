package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutsourcedPart extends Part {

    private final StringProperty companyName;

    public OutsourcedPart(){
        super();                                        // super class calls Part parent class
        companyName = new SimpleStringProperty();
    }

    public void setCompanyName(String companyName){
                                                                // company name getters/setters
        this.companyName.set(companyName);
    }

    public String getCompanyName(){
        return this.companyName.get();
    }

}