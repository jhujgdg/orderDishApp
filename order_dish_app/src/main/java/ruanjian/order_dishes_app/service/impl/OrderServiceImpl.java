package ruanjian.order_dishes_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruanjian.order_dishes_app.dto.OrderCreateRequest;
import ruanjian.order_dishes_app.entity.Order;
import ruanjian.order_dishes_app.entity.OrderItem;
import ruanjian.order_dishes_app.entity.Dish;
import ruanjian.order_dishes_app.repository.OrderRepository;
import ruanjian.order_dishes_app.repository.DishRepository;
import ruanjian.order_dishes_app.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;



    @Override
    public List<Order> getOrdersByStatus(Integer userId, Order.OrderStatus status) {
        return orderRepository.findAllByUserIdAndStatus(userId, status);
    }
    @Override
    @Transactional
    public Order createOrder(OrderCreateRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus(Order.OrderStatus.preparing);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> itemList = new ArrayList<>();

        for (OrderCreateRequest.OrderItemRequest itemReq : request.getItems()) {
            Dish dish = dishRepository.findById(itemReq.getDishId())
                    .orElseThrow(() -> new RuntimeException("菜品不存在: " + itemReq.getDishId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setDishId(dish.getDishId());
            item.setQuantity(itemReq.getQuantity());
            item.setPriceAtOrder(dish.getPrice());

            BigDecimal itemTotal = dish.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            itemList.add(item);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(itemList);

        return orderRepository.save(order);
    }
    @Override
    public List<Order> getOrdersByUserIdAndStatus(Integer userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    @Transactional
    public void completeOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(Order.OrderStatus.completed);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(Order.OrderStatus.cancelled);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }



}