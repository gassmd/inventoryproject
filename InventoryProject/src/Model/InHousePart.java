package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InHousePart extends Part {

    private final IntegerProperty machineId;

    public InHousePart(){
        super();                        // super class calls Part parent class
        machineId = new SimpleIntegerProperty();
    }

    public void setMachineId(int machineId){
        this.machineId.set(machineId);
    }

    public int getMachineId(){
        return this.machineId.get();
    }


}
