package sapo.intern.mock.carstore.ticket.helper;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;


/**
 * Implement of create transaction id which structure is: TRANS_{timestamp}
 */
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransactionIdGenerator implements IdentifierGenerator {

    private String prefix = "TRANS";

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        // Generate the transaction ID using the current timestamp
        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        return prefix + "_" + timestamp;
    }
}
