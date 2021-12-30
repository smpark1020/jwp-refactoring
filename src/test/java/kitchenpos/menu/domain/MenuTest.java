package kitchenpos.menu.domain;

import kitchenpos.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    @Test
    @DisplayName("메뉴 가격이 메뉴 상품 가격들의 합보다 크면 생성할 수 없다.")
    void constructor_price_fail() {
        // given
        MenuGroup menuGroup = MenuGroup.of("두마리메뉴");

        Product product = Product.of("후라이드", 16_000);

        List<MenuProduct> menuProducts = new ArrayList<>();
        menuProducts.add(MenuProduct.of(product, 1));

        // when, then
        assertThatThrownBy(() -> new Menu("후라이드치킨", 17_000, menuGroup, menuProducts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 가격이 메뉴 상품 가격들의 합보다 큽니다.");
    }
}
