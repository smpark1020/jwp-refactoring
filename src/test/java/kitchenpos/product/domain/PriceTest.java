package kitchenpos.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("Price를 생성한다.")
    void constructor(int input) {
        // when
        Price result = new Price(input);

        // then
        assertThat(result.getPrice()).isEqualTo(input);
    }

    @Test
    @DisplayName("음수가 입력되면 생성에 실패한다.")
    void constructor_negative() {
        // when, then
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가격은 0원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("입력값과 현재 값을 곱한 후 반환합니다.")
    void multiply() {
        // given
        int priceValue = 16_000;
        Price price = new Price(priceValue);

        // when
        int input = 2;
        Price result = price.multiply(input);

        // then
        assertThat(result).isEqualTo(new Price(priceValue * input));
    }
}