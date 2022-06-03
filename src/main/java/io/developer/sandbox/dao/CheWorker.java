package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class CheWorker {
    private static final Logger LOG = Logger.getLogger(PreferencePreferences.class);
    private static final String DELETE_FROM_CHE_WORKER = "DELETE FROM che_worker WHERE user_id = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + CheWorker.class);
            try (PreparedStatement deleteFromCheWorker = connection.prepareStatement(DELETE_FROM_CHE_WORKER)) {
                deleteFromCheWorker.setString(1, uuid);
                deleteFromCheWorker.execute();
            }
        }
    }
}
