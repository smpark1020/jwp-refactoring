package kitchenpos.order.domain;

import kitchenpos.order.exception.AlreadyCompletionException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long orderTableId;

    @Column(updatable = false)
    private LocalDateTime orderedTime = LocalDateTime.now();

    @Embedded
    private OrderLineItems orderLineItems;

    protected Order() {
    }

    public Order(Long id, Long orderTableId, OrderStatus orderStatus, List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderLineItems = new OrderLineItems(orderLineItems);
    }

    public Order(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order(Long orderTableId, OrderStatus orderStatus, List<OrderLineItem> orderLineItems) {
        this(null, orderTableId, orderStatus, orderLineItems);
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        checkOrderStatus();
        this.orderStatus = orderStatus;
    }

    private void checkOrderStatus() {
        if (Objects.equals(OrderStatus.COMPLETION, this.orderStatus)) {
            throw new AlreadyCompletionException();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItemValues();
    }

    public boolean isOrderStatusCompleted() {
        return orderStatus.isCompleted();
    }
}