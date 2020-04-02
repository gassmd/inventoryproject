package Model;

import javafx.beans.property.*;

public abstract class Part {

    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty price;       //Variables as defined in UML
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Part() {          //Constructor
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        stock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    public void setId(int id) {                      //setters
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public void setMax(int max) {
        this.max.set(max);
    }


    public int getId() {                         // getters
        return this.id.get();
    }

    public String getName() {
        return this.name.get();
    }

    public double getPrice() {
        return this.price.get();
    }

    public int getStock() {
        return this.stock.get();
    }

    public int getMin() {
        return this.min.get();
    }

    public int getMax() {
        return this.max.get();
    }


    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public IntegerProperty minProperty() {
        return min;
    }

    public IntegerProperty maxProperty() {
        return max;
    }


    public static String partValidation(String name, int min, int max, int inv, double price, String errorMessage) {
        if (name == null) {
            errorMessage = errorMessage + ("Name field blank");
        }
        if (min > max) {
            errorMessage = errorMessage + ("Inventory minimum must be less than maximum");
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + ("Inventory must be between min and max values");
        }
        if (inv < 1) {
            errorMessage = errorMessage + ("Inventory must be greater than 0");
        }
        if (price < 1) {
            errorMessage = errorMessage + ("Price must be greater than 0");
        }

        return errorMessage;

    }
}
