package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.ticket.dtos.CreateIssueDto;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue, Long> {
    @Query(nativeQuery = true, value = "INSERT INTO issues " +
            "(description, progress, status, ticket_id, service_id, user_id) " +
            "VALUES (:description, :progress, :status, :ticketId, :serviceId, :employeeId)")
    @Modifying
    void addIssues(@Param("description") String description,
                   @Param("progress") int progress,
                   @Param("status") int status,
                   @Param("ticketId") Long ticketId,
                   @Param("serviceId") Long serviceId,
                   @Param("employeeId") Long employeeId);

    // Custom method to handle bulk insert
    @Modifying
    default void addIssuesById(Long ticketId, List<CreateIssueDto> issueDtos) {
        for (CreateIssueDto dto : issueDtos) {
            addIssues(dto.getDescription(), 0, 0, ticketId, dto.getServiceId(), dto.getEmployeeId());
        }
    }

    @Query(nativeQuery = true, value = "select issue_id from issues where ticket_id = :ticketId")
    List<Long> getAllByTicketId(@Param("ticketId") Long ticketId);
}
