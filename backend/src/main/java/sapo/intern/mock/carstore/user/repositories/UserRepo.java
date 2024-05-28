package sapo.intern.mock.carstore.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.user.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
    List<User> findByNameOrPhone(String name, String phone);
    User findByResetPasswordToken(String token);

    @Query("SELECT p FROM User p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}

