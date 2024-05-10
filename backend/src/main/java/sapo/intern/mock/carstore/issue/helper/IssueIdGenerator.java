package sapo.intern.mock.carstore.issue.helper;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


public class IssueIdGenerator implements IdentifierGenerator {
    private String prefix = "ISS";
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return null;
    }
}
