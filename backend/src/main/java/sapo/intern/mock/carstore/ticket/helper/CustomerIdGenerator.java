package sapo.intern.mock.carstore.ticket.helper;

import jakarta.persistence.SequenceGenerator;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.lang.annotation.Annotation;
import java.util.EnumSet;
import java.util.Properties;

public class CustomerIdGenerator implements IdentifierGenerator {

    private String prefix = "CUS";

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return null;
    }

}
