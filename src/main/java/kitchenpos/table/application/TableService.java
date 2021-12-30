package kitchenpos.table.application;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.dto.OrderTableCreateRequest;
import kitchenpos.table.dto.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableService {
    private final OrderRepository orderDao;
    private final OrderTableRepository orderTableDao;

    public TableService(final OrderRepository orderDao, final OrderTableRepository orderTableDao) {
        this.orderDao = orderDao;
        this.orderTableDao = orderTableDao;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableCreateRequest orderTableCreateRequest) {
        OrderTable orderTable = orderTableCreateRequest.toEntity();
        OrderTable saveOrderTable = orderTableDao.save(orderTable);
        return OrderTableResponse.of(saveOrderTable);
    }

    public List<OrderTableResponse> list() {
        List<OrderTable> orderTables = orderTableDao.findAll();
        return OrderTableResponse.ofList(orderTables);
    }

    @Transactional
    public OrderTableResponse changeEmpty(final Long orderTableId) {
        final OrderTable savedOrderTable = orderTableDao.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 테이블입니다."));

        savedOrderTable.empty();
        return OrderTableResponse.of(savedOrderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final Long orderTableId, final int numberOfGuests) {
        final OrderTable savedOrderTable = orderTableDao.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 테이블입니다."));

        savedOrderTable.changeNumberOfGuests(numberOfGuests);

        return OrderTableResponse.of(savedOrderTable);
    }
}
