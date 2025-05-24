package ruanjian.order_dishes_app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruanjian.order_dishes_app.entity.DishReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishReviewRepository extends JpaRepository<DishReview, Integer> {
    List<DishReview> findByDishId(Integer dishId);

    @Query("SELECT d.dishId, AVG(r.rating) as avgRating FROM DishReview r " +
            "JOIN Dish d ON d.dishId = r.dishId " +
            "GROUP BY d.dishId ORDER BY avgRating DESC")
    List<Object[]> findTopRatedDishIds(Pageable pageable);
    Optional<DishReview> findByUserIdAndDishIdAndOrderId(Integer userId, Integer dishId, Integer orderId);

}
