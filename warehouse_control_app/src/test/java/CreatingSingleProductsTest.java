import de.mcc.Storehouse.io.File.CsvFileManager;
import de.mcc.Storehouse.model.SingleProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreatingSingleProductsTest {
    static CsvFileManager testCsv;

    @BeforeEach
    public void init() {
        testCsv = new CsvFileManager();
    }

    @Test
    void testCreateSingleProductWithValidData() {
        String[] data = {"WeighedProduct", "Apple", "Fruit", "true", "8"};
        SingleProduct product = testCsv.createSingleProduct(data);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Apple", product.getName());
        Assertions.assertEquals("Fruit", product.getCategory());
        Assertions.assertTrue(product.isExtraCare());
        Assertions.assertEquals(8, product.getNumber());
    }

    @Test
    void testCreateSingleProductWithFalseExtraCare() {
        String[] data = {"WeighedProduct", "Banana", "Fruit", "false", "8"};
        SingleProduct product = testCsv.createSingleProduct(data);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Banana", product.getName());
        Assertions.assertEquals("Fruit", product.getCategory());
        Assertions.assertFalse(product.isExtraCare());
        Assertions.assertEquals(8, product.getNumber());
    }

    @Test
    void testCreateSingleProductWithInvalidWeight() {
        String[] data = {"WeighedProduct", "Orange", "Fruit", "true", "invalid"};
        assertThrows(NumberFormatException.class, () -> {
            testCsv.createWeighedProduct(data);
        });
    }

    @Test
    void testCreateSingleProductWithMissingData() {
        String[] data = {"WeighedProduct", "Orange", "Fruit", "true"};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testCsv.createWeighedProduct(data);
        });
    }
}
