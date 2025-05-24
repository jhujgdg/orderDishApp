package ruanjian.order_dishes_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ruanjian.order_dishes_app.entity.Dish;

import java.util.List;
@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    // 模糊查询
    List<Dish> findByNameContainingIgnoreCaseAndIsAvailableTrue(String keyword);

    // 查找所有可用菜品
    List<Dish> findByIsAvailableTrue();

    // 按分类查找菜品
    @Query("SELECT d FROM Dish d JOIN DishCategory dc ON d.dishId = dc.dishId WHERE dc.categoryId = :categoryId")
    Page<Dish> findByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

}
