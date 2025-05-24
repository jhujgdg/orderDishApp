package ruanjian.order_dishes_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer userId;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ 关键部分
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public enum OrderStatus {
        preparing, completed, cancelled
    }
}
