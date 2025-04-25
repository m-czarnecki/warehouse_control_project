import de.mcc.Storehouse.model.Product;
import de.mcc.Storehouse.model.SingleProduct;
import de.mcc.Storehouse.model.Storehouse;
import de.mcc.Storehouse.model.WeighedProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RemovingProductsByIDTest {
    private Storehouse storehouse;

    @BeforeEach
    void setUp() {
        storehouse = new Storehouse();
    }

    @Test
    void testRemoveExistingProduct() {
        Product product = new SingleProduct("Apple", "Fruit", false, 10);
        storehouse.addProduct(product);
        boolean result = storehouse.removeProduct(product.getID());

        assertTrue(result);
        assertFalse(storehouse.getProducts().containsKey(product.getID()));
    }

    @Test
    void testRemoveNonExistentProduct() {
        boolean result = storehouse.removeProduct(999);
        assertFalse(result);
    }

    @Test
    void testRemoveProductFromEmptyStorehouse() {
        boolean result = storehouse.removeProduct(1);
        assertFalse(result);
    }

    @Test
    void testRemoveProductMultipleTimes() {
        Product product = new SingleProduct("Banana", "Fruit", false, 5);
        storehouse.addProduct(product);

        boolean firstRemoveResult = storehouse.removeProduct(product.getID());
        boolean secondRemoveResult = storehouse.removeProduct(product.getID());

        assertTrue(firstRemoveResult);
        assertFalse(secondRemoveResult);
    }

    @Test
    void testRemoveProductWithSimilarID() {
        Product product1 = new SingleProduct("Apple", "Fruit", false, 10);
        Product product2 = new WeighedProduct("Orange", "Fruit", false, 2.5);
        storehouse.addProduct(product1);
        storehouse.addProduct(product2);

        boolean result = storehouse.removeProduct(product1.getID());

        assertTrue(result);
        assertFalse(storehouse.getProducts().containsKey(product1.getID()));
        assertTrue(storehouse.getProducts().containsKey(product2.getID()));
    }

    @Test
    void testRemoveProductWithNegativeID() {
        Product product = new SingleProduct("Pear", "Fruit", false, 8);
        product.setID(-1);
        storehouse.addProduct(product);

        boolean result = storehouse.removeProduct(-1);

        assertTrue(result);
        assertFalse(storehouse.getProducts().containsKey(-1));
    }}
