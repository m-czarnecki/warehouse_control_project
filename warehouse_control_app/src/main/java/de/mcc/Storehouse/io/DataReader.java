package de.mcc.Storehouse.io;

import de.mcc.Storehouse.model.SingleProduct;
import de.mcc.Storehouse.model.WeighedProduct;
import lombok.extern.java.Log;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public SingleProduct readAndCreateSingleProduct() {
        printer.printWarning("Please enter the product name: ");
        String name = sc.nextLine();
        printer.printWarning("Please enter the product category: ");
        String category = sc.nextLine();
        printer.printWarning("Please enter the quantity of the products: ");
        int number = sc.nextInt();
        sc.nextLine();
        printer.printWarning("Does the product require extra care? Please enter true or false [false by default]: ");
        boolean extraCare = Boolean.parseBoolean(sc.nextLine().toLowerCase());

        return new SingleProduct(name, category, extraCare, number);
    }

    public WeighedProduct readAndCreateWeightProduct() {
        printer.printWarning("Please enter the product name: ");
        String name = sc.nextLine();
        printer.printWarning("Please enter the product category: ");
        String category = sc.nextLine();
        printer.printWarning("Please enter the amount of the products in kg [using comma or period depending on system language] : ");
        double weight = sc.nextDouble();
        sc.nextLine();
        printer.printWarning("Does the product require extra care? Please enter true or false [false by default]: ");

        boolean extraCare = Boolean.parseBoolean(sc.nextLine().toLowerCase());

        return new WeighedProduct(name, category, extraCare, weight);
    }


    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public double getDouble() {
        try {
            return sc.nextDouble();
        } finally {
            sc.nextLine();
        }
    }

    public char getChar() {

        String s = sc.nextLine();
        return s.charAt(0);

    }

    public String getString() {
        return sc.nextLine();
    }

    public void close() {

        sc.close();
    }


}

