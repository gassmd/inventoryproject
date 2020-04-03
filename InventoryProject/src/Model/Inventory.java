package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIdCount = 0;
    private static int productIdCount = 0;

    public Inventory(){
        }

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }


    public static int lookupPart(String search){         // Looks up part by ID, returns Part if present, else null
        boolean isFound = false;
        int index = 0;
        if (integerCheck(search)){                      // searches by ID if search term is integer
            for (int i = 0; i < allParts.size(); i++){
                if (Integer.parseInt(search) == allParts.get(i).getId()){
                    index = i;
                    isFound = true;
                }
            }
        }
        else{
            for (int i = 0; i < allParts.size(); i++){          // search
                search = search.toLowerCase();
                if (search.equals(allParts.get(i).getName().toLowerCase())){
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound){
            return index;
        }
        else{
            System.out.println("Error: No parts found");
            return -1;
        }
    }

    public static int lookupProduct(String searchTerm){
        boolean isFound = false;
        int index = 0;
        if (integerCheck(searchTerm)){
            for(int i = 0; i < allProducts.size(); i++){                                // searches products by Id if search term is integer, by name otherwise
                if (Integer.parseInt(searchTerm) == allProducts.get(i).getId()){
                    index = i;
                    isFound = true;
                }
            }
        }
        else{
            for (int i = 0; i < allProducts.size(); i++){
                if (searchTerm.equals(allProducts.get(i).getName())){
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound == true){
            return index;
        }
        else {
            System.out.println("No products found");
            return -1;
        }
    }

    public static boolean integerCheck(String input){
        try{
            Integer.parseInt(input);
            return true;                                                    // check if input is integer to return Id
        }
        catch (Exception e){
            return false;
        }
    }



    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }                                                                           // updates selected part/product

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart){
        for (Part p : allParts){
            if (p.getId() == selectedPart.getId()){
                allParts.remove(p);
                return true;
            }
        }
        return false;                                                       // deletes part/product and returns true if found, else returns false
    }

    public static boolean deleteProduct(Product selectedProduct){
        for (Product p : allProducts){
            if (p.getId() == selectedProduct.getId()){
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }

    public static int getPartIdCount(){
        partIdCount++;
        return partIdCount;
    }                                                                      // returns part/product autogen ids

    public static int getProductIdCount(){
        productIdCount++;
        return productIdCount;
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }                                                                   // returns parts/products lists

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
