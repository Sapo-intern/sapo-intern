package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.dtos.ProductQuantity;
import sapo.intern.mock.carstore.issue.models.StorageTransaction;

import java.util.List;

@Repository
public interface StorageRepo extends JpaRepository<StorageTransaction, Long> {
    @Query("SELECT new sapo.intern.mock.carstore.issue.dtos.ProductQuantity(p.id, " +
            "COALESCE(SUM(CASE WHEN st.type = 1 THEN -st.quantity WHEN st.type =0 THEN st.quantity WHEN st.type = 2  THEN st.quantity ELSE 0 END), 0), " +
            "p.name, p.productCode, p.unitPrice) " +
            "FROM Product p LEFT JOIN StorageTransaction st ON p.id = st.product.id " +
            "GROUP BY p.id")
    List<ProductQuantity> findTotalQuantityForAllProducts();


}
