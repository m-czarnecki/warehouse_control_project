package de.mcc.Storehouse.model;

import lombok.*;

public abstract class Product implements Comparable<Product>, CsvConvertable {

    private @Getter
    @Setter String name;
    private @Getter
    @Setter int ID;
    private static int nextID;
    private @Getter
    @Setter String category;
    private @Getter
    @Setter boolean extraCare;

    public Product(String name, String category, boolean extraCare) {
        this.name = name;
        this.ID = nextID++;
        this.category = category;
        this.extraCare = extraCare;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name +
                ", ID=" + ID +
                ", category='" + category +
                ", extra_Care=" + extraCare;
    }
}
