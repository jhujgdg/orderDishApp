package ruanjian.order_dishes_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    // ✅ 实际存入数据库的字段
    @Column(name = "dish_id")
    private Integer dishId;

    private Integer quantity;

    private BigDecimal priceAtOrder;

    // ✅ 只读地加载菜品详情（比如名字、图片）
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id", insertable = false, updatable = false)
    private Dish dish;
}
