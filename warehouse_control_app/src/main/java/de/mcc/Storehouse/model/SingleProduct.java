package de.mcc.Storehouse.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class SingleProduct extends Product {
    public static final String TYPE = "SP";

    private @Getter
    @Setter int number;

    public SingleProduct(String name, String category, boolean extraCare, int number) {
        super(name, category, extraCare);
        this.number = number;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") +
                getName() + ";" +
                getCategory() + ";" +
                isExtraCare() + ";" +
                number + ";" +
                getID();
    }

    @Override
    public int compareTo(Product o) {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", number=" + number + '}';
    }

    public void addSomeSingleProducts(int amount) {
        number += amount;
    }

    public void removeSomeSingleProducts(int amount) {
        number -= amount;
    }

}
