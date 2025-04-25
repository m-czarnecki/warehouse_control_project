package de.mcc.Storehouse.model.comparator;


import de.mcc.Storehouse.model.Product;

import java.util.Comparator;

public class IDComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
            if (p1 == null && p2 == null) {
                return 0;
            }
            if (p1 == null) {
                return 1;
            }
            if (p2 == null) {
                return -1;
            }
            Integer i1 = p1.getID();
            Integer i2 = p2.getID();
            return - i1.compareTo(i2);
        }
}
