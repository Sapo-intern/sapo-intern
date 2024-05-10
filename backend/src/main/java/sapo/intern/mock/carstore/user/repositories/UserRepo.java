package sapo.intern.mock.carstore.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.user.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
