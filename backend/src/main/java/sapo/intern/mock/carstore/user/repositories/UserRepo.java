package sapo.intern.mock.carstore.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.user.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByNameOrPhone(String name, String phone);
    User findByResetPasswordToken(String token);
}
