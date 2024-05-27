package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import sapo.intern.mock.carstore.issue.enums.StorageType;

import java.sql.Date;

public class Storage {
    @Id
    private Long id;
    private Date createdDate = new Date(System.currentTimeMillis());
    private int quantity;
    private StorageType type;
    @OneToOne
    private RepairPart part;
}
