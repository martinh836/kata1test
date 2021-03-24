package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductDiscount {
    // Here we would apply the rule for the discount category
    BigDecimal discount(Item item) {
        // For prototype just discount by 10%
        return item.price().divide(BigDecimal.TEN, RoundingMode.HALF_UP);
    }
}
