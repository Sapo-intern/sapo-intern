package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.models.Products;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {
    Products findByName(String name);
}
