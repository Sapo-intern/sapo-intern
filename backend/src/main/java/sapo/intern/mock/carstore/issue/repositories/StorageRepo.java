package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.dtos.ProductQuantity;
import sapo.intern.mock.carstore.issue.models.StorageTransaction;

import java.util.List;

@Repository
public interface StorageRepo extends JpaRepository<StorageTransaction, Long> {
    @Query("SELECT new sapo.intern.mock.carstore.issue.dtos.ProductQuantity(st.product.id, SUM(st.quantity)) " +
            "FROM StorageTransaction st GROUP BY st.product.id")
    List<ProductQuantity> findTotalQuantityForAllProducts();
}
