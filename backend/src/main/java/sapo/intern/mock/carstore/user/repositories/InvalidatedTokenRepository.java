package sapo.intern.mock.carstore.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.user.models.InvalidatedToken;
import sapo.intern.mock.carstore.user.models.User;

import java.util.List;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
