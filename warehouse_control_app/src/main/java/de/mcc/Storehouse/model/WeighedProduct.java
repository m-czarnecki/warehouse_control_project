package de.mcc.Storehouse.model;

import lombok.Getter;
import lombok.Setter;

public class WeighedProduct extends Product {
    public static final String TYPE = "WP";

    private @Setter
    @Getter double weight;

    public WeighedProduct(String name, String category, boolean extraCare, double weight) {
        super(name, category, extraCare);
        this.weight = weight;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") +
                getName() + ";" +
                getCategory() + ";" +
                isExtraCare() + ";" +
                weight + ";" +
                getID();

    }

    @Override
    public int compareTo(Product o) {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString()
                        + ", weight=" + weight + '}';
    }

    public void addSomeWeighedProducts(double amount) {
        weight += amount;
    }

    public void removeSomeWeighedProducts(double amount) {
        weight -= amount;
    }
}
