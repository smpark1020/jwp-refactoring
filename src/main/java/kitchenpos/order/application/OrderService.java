package kitchenpos.order.application;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.*;
import kitchenpos.order.dto.OrderCreateRequest;
import kitchenpos.order.dto.OrderLineItemCreateRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {
    private final MenuRepository menuDao;
    private final OrderRepository orderDao;
    private final OrderLineItemRepository orderLineItemDao;
    private final OrderTableRepository orderTableDao;

    public OrderService(
            final MenuRepository menuDao,
            final OrderRepository orderDao,
            final OrderLineItemRepository orderLineItemDao,
            final OrderTableRepository orderTableDao
    ) {
        this.menuDao = menuDao;
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
        this.orderTableDao = orderTableDao;
    }

    @Transactional
    public OrderResponse create(final OrderCreateRequest orderCreateRequest) {
        final OrderTable orderTable = orderTableDao.findById(orderCreateRequest.getOrderTableId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 테이블입니다."));

        Map<Long, Menu> menus = findMenusByMenuId(orderCreateRequest.getOrderLineItemCreateRequests());
        Order order = orderCreateRequest.toEntity(menus, orderTable);
        final Order savedOrder = orderDao.save(order);
        return OrderResponse.of(savedOrder);
    }

    public List<OrderResponse> list() {
        final List<Order> orders = orderDao.findAll();
        return OrderResponse.ofList(orders);
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final OrderStatus orderStatus) {
        final Order savedOrder = orderDao.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        savedOrder.changeOrderStatus(orderStatus);
        return OrderResponse.of(savedOrder);
    }

    private Map<Long, Menu> findMenusByMenuId(List<OrderLineItemCreateRequest> orderLineItemCreateRequests) {
        Map<Long, Menu> menus = new HashMap<>();
        for (OrderLineItemCreateRequest request : orderLineItemCreateRequests) {
            Menu menu = menuDao.findById(request.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴가 포함되어 있습니다."));
            menus.put(menu.getId(), menu);
        }
        return menus;
    }
}
