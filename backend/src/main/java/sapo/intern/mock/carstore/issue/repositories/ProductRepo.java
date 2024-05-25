package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.models.Employee;
import sapo.intern.mock.carstore.issue.models.Products;
import sapo.intern.mock.carstore.user.models.User;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {
    Products findByName(String name);
}
