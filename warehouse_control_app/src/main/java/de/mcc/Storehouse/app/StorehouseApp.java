package de.mcc.Storehouse.app;

import de.mcc.Storehouse.io.ConsolePrinter;

public class StorehouseApp {
    private final static ConsolePrinter printer = new ConsolePrinter();

    public static void main(String[] args) {

        printer.printLine("Welcome to storehouse control system!");
        StorehouseControl storehouseControl = new StorehouseControl();
        storehouseControl.controlLoop();
    }
}
