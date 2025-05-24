package ruanjian.order_dishes_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "dish_reviews", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "dishId", "orderId"}))
public class DishReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private Integer userId;

    private Integer dishId;

    private Integer orderId;

    private Byte rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime reviewDate;
}
