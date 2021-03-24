package kata.supermarket;

import java.math.BigDecimal;

public class Product {

    private final BigDecimal pricePerUnit;
    private ProductDiscount productDiscount = null;

    public Product(final BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }

    // Hold a discount here
    public void addDiscount(ProductDiscount productDiscount) { this.productDiscount = productDiscount; }

    // Let the discount do the work
    public ProductDiscount discount() { return productDiscount; }
}
