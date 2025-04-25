package de.mcc.Storehouse.io.File;


import de.mcc.Storehouse.io.ConsolePrinter;
import de.mcc.Storehouse.io.DataReader;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileManagerBuilder {

    public FileManager build() {
        return new CsvFileManager();
    }


}
