package PackPro;

import java.util.Scanner;
import java.util.ArrayList;

public class ProductMaintApp implements ProductConstants {
    private static ProductDAO productDAO = null;
    private static Scanner sc = null;

    public static void main(String args[]) {
        System.out.println("Welcome to the Product Maintenance application\n");

        productDAO = DAOFactory.getProductDAO();
        sc = new Scanner(System.in);

        displayMenu();

        String action = "";
        while (!action.equalsIgnoreCase("exit")) {
            action = Validator.getString(sc, "Enter a command: ");
            System.out.println();

            if (action.equalsIgnoreCase("list")) {
                displayAllProducts();
            } else if (action.equalsIgnoreCase("add")) {
                addProduct();
            } else if (action.equalsIgnoreCase("del") || action.equalsIgnoreCase("delete")) {
                deleteProduct();
            } else if (action.equalsIgnoreCase("update")) {
                updateProduct();
            } else if (action.equalsIgnoreCase("help") || action.equalsIgnoreCase("menu")) {
                displayMenu();
            } else if (action.equalsIgnoreCase("exit")) {
                System.out.println("Bye.\n");
            } else {
                System.out.println("Error! Not a valid command.\n");
            }

            // switch (action.toLowerCase()) {
            // case "list": displayAllProducts(); break;

            // case "add": addProduct(); break;

            // case "del":
            // case "delete": deleteProduct(); break;

            // case "update": updateProduct(); break;

            // case "help":
            // case "menu": displayMenu(); break;

            // case "exit":
            // case "quit":   System.out.println("Bye.\n"); break;

            // default: System.out.println("Error! Not a valid command.\n"); break;
            // }
        }
    }

    public static void displayMenu() {
        System.out.println("COMMAND MENU");
        System.out.println("list    - List all products");
        System.out.println("add     - Add a product");
        System.out.println("del     - Delete a product");
        System.out.println("update  - Update a product");
        System.out.println("help    - Show this menu");
        System.out.println("exit    - Exit this application\n");
    }

    public static void displayAllProducts() {
        System.out.println("PRODUCT LIST");

        ArrayList<Product> products = productDAO.getProducts();
        if (products == null) {
            System.out.println("Error! Unable to get products.\n");
        } else {
            Product p = null;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < products.size(); i++) {
                p = products.get(i);
                sb.append(StringUtils.padWithSpaces(p.getCode(), CODE_SIZE + 4) +
                        StringUtils.padWithSpaces(p.getId(), CODE_SIZE + 4) +
                        StringUtils.padWithSpaces(p.getDescription(), DESCRIPTION_SIZE + 4) +
                        StringUtils.padWithSpaces(p.getAmountString(), CODE_SIZE + 4) +
                        p.getFormattedPrice() + "\t" + p.getDiscount() + "\n");
            }
            System.out.println(sb.toString());
        }
    }

    public static void addProduct() {
        String code = Validator.getString(sc, "Enter product code: ");
        String ID = Validator.getString(sc, "Enter product ID: ");
        String description = Validator.getLine(sc, "Enter product description: ");
        int amount = Validator.getInt(sc, "Enter product amount: ");
        double price = Validator.getDouble(sc, "Enter price: ");
        char discount = Validator.getChar(sc, "Enter does the item has discount: ");

        Product product = new Product();

        product.setCode(code);
        product.setId(ID);
        product.setDescription(description);
        product.setAmount(amount);
        product.setPrice(price);
        product.setDiscount(discount);
        boolean success = productDAO.addProduct(product);

        System.out.println();
        if (success) {
            System.out.println(description + " was added to the database.\n");
        } else {
            System.out.println("Error! Unable to add product\n");
        }
    }

    public static void deleteProduct() {
        String code = Validator.getString(sc, "Enter product code to delete: ");
        Product p = productDAO.getProduct(code);

        System.out.println();
        if (p != null) {
            boolean success = productDAO.deleteProduct(p);
            if (success) {
                System.out.println(p.getDescription()
                        + " was deleted from the database.\n");
            } else {
                System.out.println("Error! Unable to add product\n");
            }
        } else {
            System.out.println("No product matches that code.\n");
        }
    }

    public static void updateProduct() {
        String code = Validator.getString(sc, "Enter product code: ");
        String ID = Validator.getString(sc, "Enter product ID: ");
        String description = Validator.getLine(sc, "Enter product description: ");
        int amount = Validator.getInt(sc, "Enter product amount: ");
        double price = Validator.getDouble(sc, "Enter price: ");
        char discount = Validator.getChar(sc, "Enter does the item has discount: ");

        Product product = new Product();

        product.setCode(code);
        product.setId(ID);
        product.setDescription(description);
        product.setAmount(amount);
        product.setPrice(price);
        product.setDiscount(discount);
        boolean success = productDAO.updateProduct(product);

        System.out.println();
        if (success) {
            System.out.println(description
                    + " was added to the database.\n");
        } else {
            System.out.println("Error! Unable to add product\n");
        }
    }
}