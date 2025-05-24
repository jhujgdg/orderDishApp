package ruanjian.order_dishes_app.service;

import org.springframework.data.domain.Page;
import ruanjian.order_dishes_app.entity.Dish;
import ruanjian.order_dishes_app.entity.DishReview;

import java.util.List;
import java.util.Optional;

public interface DishService {
    List<Dish> getAllAvailableDishes();
    List<Dish> searchDishes(String keyword);
    Optional<Dish> getDishById(Integer id);
    List<DishReview> getReviewsForDish(Integer dishId);
    List<Dish> getTopRatedDishes(int limit);
    Page<Dish> getDishesByCategory(Integer categoryId, int page, int size);
}
