package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class PreferencePreferences {
    private static final Logger LOG = Logger.getLogger(PreferencePreferences.class);
    private static final String DELETE_FROM_PREFERENCE_PREFERENCES = "DELETE FROM preference_preferences WHERE preference_userid = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for " + PreferencePreferences.class);
            try (PreparedStatement deleteFromPreferencePreferences = connection.prepareStatement(DELETE_FROM_PREFERENCE_PREFERENCES)) {
                deleteFromPreferencePreferences.setString(1, uuid);
                deleteFromPreferencePreferences.execute();
            };
        }
    }
}
