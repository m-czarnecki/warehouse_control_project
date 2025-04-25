import de.mcc.Storehouse.io.File.CsvFileManager;
import de.mcc.Storehouse.model.WeighedProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class CreatingWeighedProductsTest {
    static CsvFileManager testCsv;

    @BeforeEach
    public void init() {
        testCsv = new CsvFileManager();
    }

    @Test
    void testCreateWeighedProductWithValidData() {
        String[] data = {"WeighedProduct", "Apple", "Fruit", "true", "1.5"};
        WeighedProduct product = testCsv.createWeighedProduct(data);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Apple", product.getName());
        Assertions.assertEquals("Fruit", product.getCategory());
        Assertions.assertTrue(product.isExtraCare());
        Assertions.assertEquals(1.5, product.getWeight());
    }

    @Test
    void testCreateWeighedProductWithFalseExtraCare() {
        String[] data = {"WeighedProduct", "Banana", "Fruit", "false", "2"};
        WeighedProduct product = testCsv.createWeighedProduct(data);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Banana", product.getName());
        Assertions.assertEquals("Fruit", product.getCategory());
        Assertions.assertFalse(product.isExtraCare());
        Assertions.assertEquals(2.0, product.getWeight());
    }

    @Test
    void testCreateWeighedProductWithInvalidWeight() {
        String[] data = {"WeighedProduct", "Orange", "Fruit", "true", "invalid"};
        assertThrows(NumberFormatException.class, () -> {
            testCsv.createWeighedProduct(data);
        });
    }

    @Test
    void testCreateWeighedProductWithMissingData() {
        String[] data = {"WeighedProduct", "Orange", "Fruit", "true"};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testCsv.createWeighedProduct(data);
        });
    }


}

