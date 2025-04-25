package de.mcc.Storehouse.app;

import de.mcc.Storehouse.exceptions.DataExportException;
import de.mcc.Storehouse.exceptions.DataImportException;
import de.mcc.Storehouse.exceptions.InvalidDataException;
import de.mcc.Storehouse.exceptions.NoSuchOptionException;
import de.mcc.Storehouse.io.ConsolePrinter;
import de.mcc.Storehouse.io.DataReader;
import de.mcc.Storehouse.io.File.FileManager;
import de.mcc.Storehouse.io.File.FileManagerBuilder;
import de.mcc.Storehouse.model.Product;
import de.mcc.Storehouse.model.SingleProduct;
import de.mcc.Storehouse.model.Storehouse;
import de.mcc.Storehouse.model.WeighedProduct;
import de.mcc.Storehouse.model.comparator.IDComparator;
import org.junit.Test;

import java.util.Collection;
import java.util.InputMismatchException;


public class StorehouseControl {
    public final static double PRICE_PRO_KG_IN_MONTH = 0.99;
    public final static double PRICE_PRO_ITEM_IN_MONTH = 1.29;
    public final static double EXTRA_CARE_PRICE_IN_MONTH = 0.39;
    private final ConsolePrinter printer = new ConsolePrinter();
    private final DataReader dataReader = new DataReader(printer);
    private final FileManager fileManager;
    private Storehouse storehouse;

    public StorehouseControl() {
        fileManager = new FileManagerBuilder().build();
        try {
            storehouse = fileManager.importData();
            printer.printLine("Data has been successfully imported from a CSV file.");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("A new storehouse has been initialized.");
            storehouse = new Storehouse();
        }
    }
    public void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_SINGLE_PRODUCT:
                    addSingleProduct();
                    break;
                case ADD_WEIGHED_PRODUCT:
                    addWeighedProduct();
                    break;
                case PRINT_SINGLE_PRODUCTS:
                    printSingleProducts();
                    break;
                case PRINT_WEIGHED_PRODUCTS:
                    printWeighedProducts();
                    break;
                case DELETE_SINGLE_PRODUCTS:
                    deleteSingleProduct();
                    break;
                case DELETE_WEIGHED_PRODUCT:
                    deleteWeightedProduct();
                    break;
                case CALCULATE_MONTHLY_STORAGE_COSTS:
                    double sum = calculatePriceOfAllProductsInStorehouse(storehouse.getProducts().values());
                    printer.printLine("Monthly storage costs in storehouse: " + sum + " [EUR]");
                    break;
                case PRINT_ALL_PRODUCTS:
                    printSingleProducts();
                    printWeighedProducts();
                    break;
                case SEARCH_FOR_PRODUCT:
                    printer.printWarning("What are you looking for in our storehouse?");
                    fileManager.printSearched(dataReader.getString());
                    break;
                case ADD_OR_REMOVE_SOME_WEIGHED_PRODUCTS:
                    addOrRemoveWeighedProducts();
                    break;
                case ADD_OR_REMOVE_SOME_SINGLE_PRODUCTS:
                    addOrRemoveSingleProducts();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printWarning("There is no such option. Please enter again.");
            }
        } while (option != Option.EXIT);
    }


    private void addOrRemoveWeighedProducts() {
        char operator = getOperator();
        printer.printWarning("Please enter product ID: ");
        int ID = getID();

        if (storehouse.getProducts().containsKey(ID) && storehouse.getProducts().get(ID) instanceof WeighedProduct wp) {

            double amountBeforeChanges = wp.getWeight();
            printer.printWarning("Please enter the amount in kg (using comma if necessary): ");
            double amount = getAmount();

            if (operator == '-') {
                if (amount <= wp.getWeight()) {
                    wp.removeSomeWeighedProducts(amount);
                    printer.printLine("Operation succesfull.");
                } else {
                    wp.removeSomeWeighedProducts(wp.getWeight());
                    printer.printLine("Insufficient amount in storehouse. Only " + amountBeforeChanges + "kg was removed. ");
                }
            } else if (operator == '+') {
                wp.addSomeWeighedProducts(amount);
                printer.printLine("Operation succesfull.");
            }
        } else {
            printer.printWarning("No weighed product with such ID in storehouse. Please try again.");
        }
    }

    private double getAmount() {
        double amount = 0;
        boolean quantitySet = false;
        while (!quantitySet) {
            try {
                amount = dataReader.getDouble();
                amount = checkAmountAndRepeatIfNecessary(amount);
                quantitySet = true;
            } catch (InputMismatchException e) {
                printer.printWarning("Entered quantity is not an integer. Please try again: ");
            }
        }
        return amount;

    }

    private void addOrRemoveSingleProducts() {
        char operator = getOperator();
        printer.printWarning("Please enter product ID: ");
        int ID = getID();

        if (storehouse.getProducts().containsKey(ID) && storehouse.getProducts().get(ID) instanceof SingleProduct sp) {

            int numberBeforeChanges = sp.getNumber();

            printer.printWarning("Please enter the quantity: ");
            int quantity = getQuantity();


            if (operator == '-') {
                if (quantity <= sp.getNumber()) {
                    sp.removeSomeSingleProducts(quantity);
                    printer.printLine("Operation succesfull.");
                } else {
                    sp.removeSomeSingleProducts(sp.getNumber());
                    printer.printLine("Insufficient quantity in storehouse. Only " + numberBeforeChanges + " was removed.  ");
                }
            } else if (operator == '+') {
                sp.addSomeSingleProducts(quantity);
                printer.printLine("Operation succesfull.");
            }

        } else {
            printer.printLine("No single product with such ID in storehouse. Please try again.");
        }
    }

    private int getQuantity() {
        int quantity = 0;
        boolean quantitySet = false;
        while (!quantitySet) {
            try {
                quantity = dataReader.getInt();
                quantity = checkQuantityAndRepeatIfNecessary(quantity);
                quantitySet = true;
            } catch (InputMismatchException e) {
                printer.printWarning("Entered quantity is not an integer. Please try again: ");
            }
        }
        return quantity;

    }

    private int getID() {
        int ID = 0;
        boolean IdSet = false;
        while (!IdSet) {
            try {
                ID = dataReader.getInt();
                IdSet = true;
            } catch (InputMismatchException e) {
                System.err.println("Entered ID is not a number. Please try again: ");
            }
        }
        return ID;
    }


    private char getOperator() {
        char c = ' ';
        while ((c != '+' && c != '-')) {
            printer.printWarning("Do you want to add or remove products? Enter + or -");
            c = dataReader.getChar();
        }
        return c;
    }

    private double checkAmountAndRepeatIfNecessary(double amount) {
        while (amount < 0) {
            printer.printWarning("Amount must be a positive value. Please try again.");
            amount = dataReader.getDouble();
        }
        return amount;
    }

    public int checkQuantityAndRepeatIfNecessary(int amount) {
        while (amount < 0) {
            printer.printWarning("Amount must be a positive value. Please try again.");
            amount = dataReader.getInt();
        }
        return amount;
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("The entered value is not a number. Please try again.");
            }
        }
        return option;
    }

    private void exit() {
        try {
            fileManager.exportData(storehouse);
            printer.printLine("Data successfully exported to the CSV file.");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Thank you. See you soon!");
        dataReader.close();
    }

    private void printSingleProducts() {
        printer.printSingleProducts(storehouse.getSortedProducts(new IDComparator()));
    }

    private void addSingleProduct() {
        try {
            SingleProduct singleProduct = dataReader.readAndCreateSingleProduct();
            storehouse.addProduct(singleProduct);
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create a new single product. Incorrect data.");
        }

    }

    private void addWeighedProduct() {
        try {
            WeighedProduct weighedProduct = dataReader.readAndCreateWeightProduct();
            storehouse.addProduct(weighedProduct);
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create a new weighed product. Incorrect data.");
        }

    }

    private void deleteWeightedProduct() {
        try {
            printer.printWarning("Please enter the ID of the weighed product you want to delete: ");
            int ID = dataReader.getInt();
            if (storehouse.removeProduct(ID))
                printer.printLine("The weighed product has been deleted.");
            else
                printer.printLine("The specified weighed product does not exist.");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to delete the weighed product. Incorrect ID.");
        }
    }

    private void deleteSingleProduct() {
        try {
            printer.printWarning("Please enter the ID of the single product you want to delete: ");
            int ID = dataReader.getInt();
            if (storehouse.removeProduct(ID))
                printer.printLine("The single product has been deleted.");
            else
                printer.printLine("The specified single product does not exist.");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to delete the new single product. Incorrect ID.");
        }
    }

    private void printWeighedProducts() {
        printer.printWeighedProducts(storehouse.getSortedProducts(new IDComparator()));
    }


    private void printOptions() {
        for (Option value : Option.values()) {
            printer.printLine(value.toString());
        }
        printer.printWarning("Please select an option: ");

    }
@Test
    public double calculatePriceOfAllProductsInStorehouse(Collection<Product> products) {
        double priceOfSingleProducts = calculateSingleProducts(products);
        double priceOfWeighedProducts = calculateWeighedProducts(products);
        return priceOfWeighedProducts + priceOfSingleProducts;

    }

    private double calculateSingleProducts(Collection<Product> products) {
        double sum = 0;
        for (Product product : products) {
            if (product instanceof SingleProduct) {
                sum += ((SingleProduct) product).getNumber() * PRICE_PRO_ITEM_IN_MONTH;
                if (product.isExtraCare())
                    sum += ((SingleProduct) product).getNumber() * EXTRA_CARE_PRICE_IN_MONTH;
            }
        }
        return sum;

    }

    private double calculateWeighedProducts(Collection<Product> products) {
        double sum = 0;
        for (Product product : products) {
            if (product instanceof WeighedProduct) {
                sum += ((WeighedProduct) product).getWeight() * PRICE_PRO_KG_IN_MONTH;
                if (product.isExtraCare())
                    sum += ((WeighedProduct) product).getWeight() * EXTRA_CARE_PRICE_IN_MONTH;
            }
        }
        return sum;
    }

    private enum Option {
        EXIT(0, " - exit"),
        ADD_SINGLE_PRODUCT(1, " - adding a new single product [SP]"),
        ADD_WEIGHED_PRODUCT(2, " - adding a new weighed product [WP]"),
        PRINT_SINGLE_PRODUCTS(3, " - display available single products [SP]"),
        PRINT_WEIGHED_PRODUCTS(4, " - display available weighed products [WP]"),
        DELETE_SINGLE_PRODUCTS(5, " - delete a single product [SP]"),
        DELETE_WEIGHED_PRODUCT(6, " - delete a weighed product [WP]"),
        CALCULATE_MONTHLY_STORAGE_COSTS(7, " - calculate monthly storage costs of all products"),
        PRINT_ALL_PRODUCTS(8, " - print all products in storehouse"),
        SEARCH_FOR_PRODUCT(9, " - search for a product"),
        ADD_OR_REMOVE_SOME_SINGLE_PRODUCTS(10, " - add or remove some single products [SP]"),
        ADD_OR_REMOVE_SOME_WEIGHED_PRODUCTS(11, " - add or remove some weighed products [WP]");


        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }


        @Override
        public String toString() {
            return value + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No option with the ID: " + option);
            }
        }
    }
}



