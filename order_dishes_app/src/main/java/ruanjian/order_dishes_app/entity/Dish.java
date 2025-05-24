package ruanjian.order_dishes_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")  // ✅ 必须和 @JoinColumn 中写的一样
    private Integer dishId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Integer calories;

    private BigDecimal protein;

    private BigDecimal fat;

    private BigDecimal carbs;

    private Boolean isAvailable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
