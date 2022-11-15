package mgarzon.createbest.productcatalog;
import static org.junit.Assert.*;
import org.junit.Test;

public class ProductTest {

    @Test
    public void checkProductName() {
        Product aProduct = new Product( "DELL MONITOR", 180);
        assertEquals("Check the name of the product", "DELL MONITOR", aProduct.getProductName());
    }


    @Test
    public void checkProductPrice() {
        // I am making this test to fail
        Product aProduct = new Product( "DELL MONITOR", 180);
        assertEquals("Check the price of the product", 180, aProduct.getPrice(), 0.1);
    }
    
}
