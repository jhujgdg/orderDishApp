package ruanjian.order_dishes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruanjian.order_dishes_app.entity.Order;

import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserIdAndStatus(Integer userId, Order.OrderStatus status);
    List<Order> findByUserIdAndStatus(Integer userId, Order.OrderStatus status);
}