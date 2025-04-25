package de.mcc.Storehouse.model;

import de.mcc.Storehouse.exceptions.ProductAlreadyExistsException;
import lombok.Getter;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

public class Storehouse {


    private @Getter Map<Integer, Product> products = new HashMap<>();

    public Collection<Product> getSortedProducts(Comparator<Product> comparator) {
        ArrayList<Product> list = new ArrayList<>(this.products.values());
        list.sort(comparator);
        return list;
    }

    public void addProduct(Product product) {
        if (products.containsKey(product.getID())) {
            throw new ProductAlreadyExistsException("A product with that ID already exists " + product.getID());
        }
        products.put(product.getID(), product);
    }

@Test
    public boolean removeProduct(int ID) {
        if (products.containsKey(ID)) {
            products.remove(ID);
            return true;
        } else {
            return false;
        }
    }

}

