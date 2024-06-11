package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.models.Customer;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query("SELECT p FROM Customer p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}
