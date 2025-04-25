import de.mcc.Storehouse.exceptions.DataImportException;
import de.mcc.Storehouse.io.ConsolePrinter;
import de.mcc.Storehouse.io.File.CsvFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
public class CsvSearchingTest {
    private CsvFileManager csvFileManager;

    @TempDir
    Path tempDir;

    private File productsFile;

    private TestConsolePrinter printer;

    @BeforeEach
    void setUp() throws IOException {
        productsFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(productsFile)) {
            writer.write("type;name;category;extra care;quantity/amount;id\n");
            writer.write("WP;Apple;Fruit;true;1.5;001\n");
            writer.write("SP;Banana;Fruit;false;10;002\n");
        }

        CsvFileManager.PRODUCTS_FILE_NAME = productsFile.getAbsolutePath();

        printer = new TestConsolePrinter();
        csvFileManager = new CsvFileManager();
        csvFileManager.printer = printer;
    }


    @Test
    void testPrintSearchedWithNoMatchingProduct() {
        csvFileManager.printSearched("Orange");

        String expectedOutput = "No such product in storehouse :(\n";
        assertEquals(expectedOutput, printer.getOutput());
    }

    @Test
    void testPrintSearchedFileNotFound() {
        CsvFileManager.PRODUCTS_FILE_NAME = "invalid_file_path.csv";

        DataImportException exception = assertThrows(DataImportException.class, () -> {
            csvFileManager.printSearched("Apple");
        });

        assertEquals("File not found: invalid_file_path.csv", exception.getMessage());
    }

    static class TestConsolePrinter extends ConsolePrinter {

        private final StringBuilder output = new StringBuilder();

        @Override
        public void printLine(String line) {
            output.append(line).append("\n");
        }

        public String getOutput() {
            return output.toString();
        }
    }
}
