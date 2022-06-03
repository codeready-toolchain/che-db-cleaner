package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class Preference {
    private static final Logger LOG = Logger.getLogger(Preference.class);
    private static final String DELETE_FROM_PREFERENCE = "DELETE FROM preference WHERE userid = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained");

            PreparedStatement deleteFromProfile = connection.prepareStatement(DELETE_FROM_PREFERENCE);
            deleteFromProfile.setString(1, uuid);
            deleteFromProfile.execute();
        }
    }
}
