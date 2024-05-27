package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
