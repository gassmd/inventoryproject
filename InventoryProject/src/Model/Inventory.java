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
            for (int i = 0; i < allParts.size(); i++){
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

    public static Product lookupProduct(int productId){
        for (Product p : allProducts){
            if(p.getId() == productId){
                return p;
            }
        }
        return null;
    }

    public static boolean integerCheck(String input){
        try{
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

  /*  public static ObservableList<Part> lookupPart(String partName){
        for (Part p : allParts){
            if(p.getName().equals(partName)){
                return (ObservableList<Part>) p;
            }
        }
        return null;
    }*/

    public static ObservableList<Product> lookupProduct(String productName){
        for (Product p : allProducts){
            if(p.getName().equals(productName)){
                return (ObservableList<Product>) p;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part selectedPart){
        allParts.set(selectedPart.getId(), selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(newProduct.getId(), newProduct);
    }

    public static boolean deletePart(Part selectedPart){
        for (Part p : allParts){
            if (p.getId() == selectedPart.getId()){
                allParts.remove(p);
                return true;
            }
        }
        return false;
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
    }

    public static int getProductIdCount(){
        productIdCount++;
        return productIdCount;
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
