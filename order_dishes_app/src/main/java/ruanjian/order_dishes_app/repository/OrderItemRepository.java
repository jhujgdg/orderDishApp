package ruanjian.order_dishes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruanjian.order_dishes_app.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder_OrderId(Integer orderId);

}