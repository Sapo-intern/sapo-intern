package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sapo.intern.mock.carstore.issue.helper.IssueProductKey;
import sapo.intern.mock.carstore.issue.models.IssueProduct;
import sapo.intern.mock.carstore.ticket.dtos.CreateIssueDto;

import java.util.List;

public interface IssueProductRepo extends JpaRepository<IssueProduct, IssueProductKey> {
    @Query(nativeQuery = true, value = "INSERT INTO `carstore`.`issue_product`\n" +
            "(`quantity`,\n" +
            "`issue_id`,\n" +
            "`product_id`)\n" +
            "VALUES\n" +
            "(:quantity,\n" +
            ":issueId,\n" +
            ":productId);\n")
    @Modifying
    void addIssues(@Param("quantity") int quantity,
                   @Param("productId") Long productId,
                   @Param("issueId") Long issueId);

    @Modifying
    default void addIssues(List<Long> issuesIdList,List<CreateIssueDto> issueDtos) {
        for (CreateIssueDto dto : issueDtos) {

        }
    }

}
