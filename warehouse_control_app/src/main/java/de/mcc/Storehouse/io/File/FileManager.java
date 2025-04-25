package de.mcc.Storehouse.io.File;

import de.mcc.Storehouse.model.Storehouse;

public interface FileManager {
    Storehouse importData();
    void exportData(Storehouse library);
    public void printSearched(String searched);
}
