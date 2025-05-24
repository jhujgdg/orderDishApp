package ruanjian.order_dishes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruanjian.order_dishes_app.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
}
