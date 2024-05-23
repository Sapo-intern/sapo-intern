package sapo.intern.mock.carstore.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.product.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
