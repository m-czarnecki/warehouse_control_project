package de.mcc.Storehouse.io.File;

import de.mcc.Storehouse.exceptions.DataExportException;
import de.mcc.Storehouse.exceptions.DataImportException;
import de.mcc.Storehouse.exceptions.InvalidDataException;
import de.mcc.Storehouse.io.ConsolePrinter;
import de.mcc.Storehouse.model.*;
import org.junit.Test;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

public class CsvFileManager implements FileManager{
    public static String PRODUCTS_FILE_NAME = "Storehouse.csv";
    public ConsolePrinter printer = new ConsolePrinter();


    @Override
    public void exportData(Storehouse storehouse) {
        exportProducts(storehouse);
    }
@Override
    public Storehouse importData() {
        Storehouse storehouse = new Storehouse();
        importProducts(storehouse);
        return storehouse;
    }

    private void exportProducts(Storehouse storehouse) {
        Collection<Product> products = storehouse.getProducts().values();
        exportToCsv(products, PRODUCTS_FILE_NAME);
    }



    private <T extends CsvConvertable> void exportToCsv(Collection<T> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("type;name;category;extra care;quantity/amount;id");
            bufferedWriter.newLine();
            for ( T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Writing data to file error: " + fileName);
        }
    }

    private Product createObjectFromString(String csvText) {
        String[] split = csvText.split(";");
        String type = split[0];
        if(SingleProduct.TYPE.equals(type)) {
            return createSingleProduct(split);
        } else if(WeighedProduct.TYPE.equals(type)) {
            return createWeighedProduct(split);
        }
        throw new InvalidDataException("Unknown product type: " + type);
    }
@Test
    public SingleProduct createSingleProduct(String[] data) {
        String name = data[1];
        String category = data[2];
        boolean extraCare = Boolean.parseBoolean(data[3]);
        int number = Integer.parseInt(data[4]);
        return new SingleProduct(name, category, extraCare, number);
    }
@Test
public WeighedProduct createWeighedProduct(String[] data) {
        String name = data[1];
        String category = data[2];
        boolean extraCare = Boolean.parseBoolean(data[3]);
        double weight = Double.parseDouble(data[4]);
        return new WeighedProduct(name, category, extraCare, weight);
    }

    private void importProducts(Storehouse storehouse) {
        try (Scanner fileReader = new Scanner(new File(PRODUCTS_FILE_NAME))) {
            fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                Product product = createObjectFromString(line);
                storehouse.addProduct(product);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("File not found: " + PRODUCTS_FILE_NAME);
        }
    }

    @Test
    public void printSearched(String searched){
        try (Scanner fileReader = new Scanner(new File(PRODUCTS_FILE_NAME))) {
            boolean anyProduct = false;
            fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (line.toLowerCase().contains(searched.toLowerCase())){
                    printer.printLine(createObjectFromString(line).toString());
                    anyProduct = true;
                }
            }
            if (!anyProduct){
                printer.printLine("No such product in storehouse :(");
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("File not found: " + PRODUCTS_FILE_NAME);
        }
    }
}
