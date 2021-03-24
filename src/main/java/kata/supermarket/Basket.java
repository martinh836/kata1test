package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Basket {
    private final List<Item> items;
    private final Map<ProductDiscount, Item> discounts;

    public Basket() {
        this.items = new ArrayList<>();
        this.discounts = new HashMap<>();
    }

    public void add(final Item item) {
        this.items.add(item);
        if (item.discount() != null) {
            // Add a discounted item; eventually this allows us to sum up where multi buys apply
            discounts.put(item.discount(), item);
        }
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * TODO: This could be a good place to apply the results of
         *  the discount calculations.
         *  It is not likely to be the best place to do those calculations.
         *  Think about how Basket could interact with something
         *  which provides that functionality.
         */
        private BigDecimal discounts() {
            return discounts.values().stream().map(k -> k.discount().discount(k)).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
