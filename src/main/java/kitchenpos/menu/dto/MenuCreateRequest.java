package kitchenpos.menu.dto;

import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuGroup;

import java.math.BigDecimal;
import java.util.List;

public class MenuCreateRequest {

    private String name;
    private int price;
    private Long menuGroupId;
    private List<MenuProductCreateRequest> menuProductCreateRequests;

    public MenuCreateRequest() {
    }

    public MenuCreateRequest(int price, List<MenuProductCreateRequest> menuProductCreateRequests) {
        this.price = price;
        this.menuProductCreateRequests = menuProductCreateRequests;
    }

    public MenuCreateRequest(String name,
                             int price,
                             Long menuGroupId,
                             List<MenuProductCreateRequest> menuProductCreateRequests) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProductCreateRequests = menuProductCreateRequests;
    }

    public Menu toEntity(MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        Menu menu = Menu.of(name, price, menuGroup, menuProducts);
        return menu;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductCreateRequest> getMenuProductCreateRequests() {
        return menuProductCreateRequests;
    }
}
