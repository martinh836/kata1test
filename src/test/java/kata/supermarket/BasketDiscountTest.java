package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketDiscountTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                regularItems(),
                discountedItems()
        );
    }

    private static Arguments regularItems() {
        return Arguments.of("multiple weighed items", "3.40",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(false), twoHundredGramsOfPickAndMix(false), aPackOfDigestives(false))
        );
    }

    private static Arguments discountedItems() {
        return Arguments.of("multiple weighed items", "3.18",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(true), twoHundredGramsOfPickAndMix(true), aPackOfDigestives(true))
        );
    }

    private static Item aPackOfDigestives(boolean isDiscounted) {
        Product aPackOfDigestives = new Product(new BigDecimal("1.55"));
        if (isDiscounted) {
            aPackOfDigestives.addDiscount(new ProductDiscount());
        }
        return aPackOfDigestives.oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets(boolean isDiscounted) {
        return new WeighedProduct(new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets(boolean isDiscounted) {
        return aKiloOfAmericanSweets(isDiscounted).weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix(boolean isDiscounted) {
        WeighedProduct aKiloOfPickAndMix = new WeighedProduct(new BigDecimal("2.99"));
        if (isDiscounted) {
            aKiloOfPickAndMix.addDiscount(new ProductDiscount());
        }
        return aKiloOfPickAndMix;
    }

    private static Item twoHundredGramsOfPickAndMix(boolean isDiscounted) {
        return aKiloOfPickAndMix(isDiscounted).weighing(new BigDecimal(".2"));
    }
}