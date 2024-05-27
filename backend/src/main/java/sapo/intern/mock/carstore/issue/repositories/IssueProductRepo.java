package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.intern.mock.carstore.issue.models.IssueProduct;

public interface IssueProductRepo extends JpaRepository<IssueProduct, Long> {
}
