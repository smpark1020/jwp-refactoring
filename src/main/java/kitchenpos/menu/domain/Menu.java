package kitchenpos.menu.domain;

import kitchenpos.product.domain.Price;
import kitchenpos.product.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Price price;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MenuGroup menuGroup;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    public Menu(String name, int price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        int sum = sumMenuProductPrice(menuProducts);
        if (price > sum) {
            throw new IllegalArgumentException("메뉴 가격이 메뉴 상품 가격들의 합보다 큽니다.");
        }

        this.price = new Price(price);
        this.name = name;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
    }

    public static Menu of(String name, int price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        Menu menu = new Menu(name, price, menuGroup, menuProducts);
        for (MenuProduct menuProduct : menuProducts) {
            menuProduct.updateMenu(menu);
        }
        return menu;
    }

    private int sumMenuProductPrice(List<MenuProduct> menuProducts) {
        int sum = 0;
        for (MenuProduct menuProduct : menuProducts) {
            sum += menuProduct.sumProductPrice();
        }
        return sum;
    }

    public int getPriceValue() {
        return this.price.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
