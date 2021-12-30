package kitchenpos.menu.application;

import kitchenpos.menu.domain.*;
import kitchenpos.menu.domain.MenuProductRepository;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.dto.MenuCreateRequest;
import kitchenpos.menu.dto.MenuProductCreateRequest;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuDao;
    private final MenuGroupRepository menuGroupDao;
    private final MenuProductRepository menuProductDao;
    private final ProductRepository productDao;

    public MenuService(
            final MenuRepository menuDao,
            final MenuGroupRepository menuGroupDao,
            final MenuProductRepository menuProductDao,
            final ProductRepository productDao
    ) {
        this.menuDao = menuDao;
        this.menuGroupDao = menuGroupDao;
        this.menuProductDao = menuProductDao;
        this.productDao = productDao;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest menuCreateRequest) {
        MenuGroup menuGroup = menuGroupDao.findById(menuCreateRequest.getMenuGroupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴 그룹입니다."));

        List<MenuProduct> menuProducts = createMenuProducts(menuCreateRequest.getMenuProductCreateRequests());
        Menu menu = menuCreateRequest.toEntity(menuGroup, menuProducts);

        final Menu savedMenu = menuDao.save(menu);
        return MenuResponse.of(savedMenu);
    }

    public List<MenuResponse> list() {
        List<Menu> menus = menuDao.findAll();
        return MenuResponse.ofList(menus);
    }

    private List<MenuProduct> createMenuProducts(List<MenuProductCreateRequest> menuCreateRequest) {
        List<MenuProduct> menuProducts = new ArrayList<>();
        for (MenuProductCreateRequest menuProductCreateRequest : menuCreateRequest) {
            Product product = productDao.findById(menuProductCreateRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            menuProducts.add(menuProductCreateRequest.toEntity(product));
        }
        return menuProducts;
    }
}
