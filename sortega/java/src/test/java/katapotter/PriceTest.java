package katapotter;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static katapotter.Price.price;
import static java.util.Arrays.asList;

public class PriceTest {

    private void samePrice(double expected, double actual) {
        assertEquals(expected, actual, 0.001);
    }

    @Test()
    public void noBooksNoMoney() {
        samePrice(0, price(Collections.<Integer>emptyList()));
    }

    @Test
    public void priceWithoutDiscounts() {
        samePrice(8, price(asList(0)));
        samePrice(8, price(asList(1)));
        samePrice(8, price(asList(2)));
        samePrice(8, price(asList(3)));
        samePrice(8, price(asList(4)));
        samePrice(8 * 2, price(asList(0, 0)));
        samePrice(8 * 3, price(asList(1, 1, 1)));
    }

    @Test
    public void priceSimpleDiscounts() {
        samePrice(8 * 2 * 0.95, price(asList(0, 1)));
        samePrice(8 * 3 * 0.9, price(asList(0, 2, 4)));
        samePrice(8 * 4 * 0.8, price(asList(0, 1, 2, 4)));
        samePrice(8 * 5 * 0.75, price(asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void priceSeveralDiscounts() {
        samePrice(8 + (8 * 2 * 0.95), price(asList(0, 0, 1)));
        samePrice(2 * (8 * 2 * 0.95), price(asList(0, 0, 1, 1)));
        samePrice((8 * 4 * 0.8) + (8 * 2 * 0.95), price(asList(0, 0, 1, 2, 2, 3)));
        samePrice(8 + (8 * 5 * 0.75), price(asList(0, 1, 1, 2, 3, 4)));
    }

    @Test
    public void priceEdgeCases() {
        samePrice(2 * (8 * 4 * 0.8), price(asList(0, 0, 1, 1, 2, 2, 3, 4)));
        samePrice(3 * (8 * 5 * 0.75) + 2 * (8 * 4 * 0.8),
            price(asList(0, 0, 0, 0, 0,
                   1, 1, 1, 1, 1,
                   2, 2, 2, 2,
                   3, 3, 3, 3, 3,
                   4, 4, 4, 4)));
    }
}
