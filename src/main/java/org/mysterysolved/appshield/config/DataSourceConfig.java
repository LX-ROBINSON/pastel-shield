package org.mysterysolved.appshield.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class DataSourceConfig {

    @PersistenceContext(unitName = "jdbc/pastel_shield")
    private EntityManager em;

    public EntityManager getDataSource() {
        return em;
    }
}
