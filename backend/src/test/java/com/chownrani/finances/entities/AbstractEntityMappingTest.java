package com.chownrani.finances.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.RootClass;
import org.junit.jupiter.api.Test;

import com.chownrani.finances.entities.abstracts.Card;

class AbstractEntityMappingTest {

    @Test
    void allEntitiesUseTimestampSoftDeleteAndAuditColumns() {
        try (StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .build()) {

            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Competence.class)
                    .addAnnotatedClass(Emergency.class)
                    .addAnnotatedClass(FixedExpenseCategory.class)
                    .addAnnotatedClass(Investment.class)
                    .addAnnotatedClass(Invoice.class)
                    .addAnnotatedClass(Subscription.class)
                    .addAnnotatedClass(SubscriptionCompetence.class)
                    .addAnnotatedClass(VariableExpenseCategory.class)
                    .addAnnotatedClass(VariableExpenseCompetence.class)
                    .addAnnotatedClass(Card.class)
                    .addAnnotatedClass(Credit.class)
                    .addAnnotatedClass(Debit.class)
                    .buildMetadata();

            List<RootClass> roots = metadata.getEntityBindings().stream()
                    .filter(RootClass.class::isInstance)
                    .map(RootClass.class::cast)
                    .toList();

            assertEquals(12, roots.size(), "unexpected number of root entities");

            for (RootClass root : roots) {
                String name = root.getEntityName();

                assertEquals(SoftDeleteType.TIMESTAMP, root.getSoftDeleteStrategy(),
                        "soft delete strategy of " + name);

                List<String> columns = root.getTable().getColumns().stream()
                        .map(Column::getName)
                        .toList();

                assertTrue(columns.contains("deleted_at"), name + " missing deleted_at column: " + columns);
                assertTrue(columns.contains("created_at"), name + " missing created_at column: " + columns);
                assertTrue(columns.contains("updated_at"), name + " missing updated_at column: " + columns);
            }
        }
    }
}
