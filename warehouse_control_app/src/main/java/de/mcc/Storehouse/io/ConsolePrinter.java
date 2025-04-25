package de.mcc.Storehouse.io;

import de.mcc.Storehouse.model.Product;
import de.mcc.Storehouse.model.SingleProduct;
import de.mcc.Storehouse.model.WeighedProduct;
import lombok.extern.java.Log;

import java.util.Collection;

@Log
public class ConsolePrinter {
    public void printSingleProducts(Collection<Product> products) {
        int countSingleProducts = 0;
        for (Product product : products) {
            if (product instanceof SingleProduct) {
                printLine(product.toString());
                countSingleProducts++;
            }
        }
        if (countSingleProducts == 0) {
            printLine("No single products in storehouse.");
        }
    }

    public void printWeighedProducts(Collection<Product> products) {
        int countWeighedProducts = 0;
        for (Product product : products) {
            if (product instanceof WeighedProduct) {
                printLine(product.toString());
                countWeighedProducts++;
            }
        }
        if (countWeighedProducts == 0) {
            printLine("No weighed products in storehouse.");
        }
    }


    public void printLine(String text) {
        System.out.println(text.toUpperCase());
    }

    public void printWarning(String text) {
        log.warning(text.toUpperCase());
    }

}
