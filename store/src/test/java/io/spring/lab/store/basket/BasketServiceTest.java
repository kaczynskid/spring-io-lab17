package io.spring.lab.store.basket;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.lab.store.basket.item.BasketItem;
import io.spring.lab.store.basket.item.BasketItemService;
import io.spring.lab.store.special.SpecialCalculation;
import io.spring.lab.store.special.SpecialClient;

import static io.spring.lab.store.special.SpecialCalculationRequest.requestCalculationFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = {
        "io.spring.lab:warehouse:+"
})
public class BasketServiceTest {

    protected static final long ITEM_ID = 1L;
    protected static final String ITEM_NAME = "A";
    protected static final BigDecimal ITEM_UNIT_PRICE = BigDecimal.valueOf(40);
    protected static final int ITEM_REGULAR_COUNT = 2;
    protected static final BigDecimal ITEM_REGULAR_PRICE = BigDecimal.valueOf(80.0);
    protected static final int ITEM_SPECIAL_COUNT = 5;
    protected static final BigDecimal ITEM_SPECIAL_PRICE = BigDecimal.valueOf(150.0);
    protected static final String SPECIAL_ID = "abc";

    @MockBean
    SpecialClient specials;

    @Autowired
    BasketService baskets;

    @Autowired
    BasketItemService basketItems;

    @Test
    public void shouldUpdateBasketWithRegularPriceItem() {
        // given
        Basket basket = baskets.create();
        when(specials.calculateFor(ITEM_ID, requestCalculationFor(ITEM_UNIT_PRICE, ITEM_REGULAR_COUNT)))
                .thenReturn(new SpecialCalculation(null, ITEM_REGULAR_PRICE));

        // when
        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), ITEM_ID, ITEM_REGULAR_COUNT);

        // then
        assertThat(diff).isNotNull();
        assertThat(diff.getCountDiff()).isEqualTo(ITEM_REGULAR_COUNT);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(ITEM_REGULAR_PRICE);

        // and
        BasketItem basketItem = basketItems.findOneItem(basket.getId(), ITEM_ID);
        assertThat(basketItem.getName()).isEqualTo(ITEM_NAME);
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(ITEM_REGULAR_PRICE);
        assertThat(basketItem.getSpecialId()).isNull();
    }

    @Test
    public void shouldUpdateBasketWithSpecialPriceItem() {
        // given
        Basket basket = baskets.create();
        when(specials.calculateFor(ITEM_ID, requestCalculationFor(ITEM_UNIT_PRICE, ITEM_SPECIAL_COUNT)))
                .thenReturn(new SpecialCalculation(SPECIAL_ID, ITEM_SPECIAL_PRICE));

        // when
        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), ITEM_ID, ITEM_SPECIAL_COUNT);

        // then
        assertThat(diff).isNotNull();
        assertThat(diff.getCountDiff()).isEqualTo(ITEM_SPECIAL_COUNT);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(ITEM_SPECIAL_PRICE);

        // and
        BasketItem basketItem = basketItems.findOneItem(basket.getId(), ITEM_ID);
        assertThat(basketItem.getName()).isEqualTo(ITEM_NAME);
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(ITEM_SPECIAL_PRICE);
        assertThat(basketItem.getSpecialId()).isEqualTo(SPECIAL_ID);
    }

}