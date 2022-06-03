package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class ProfileAttributes {
    private static final Logger LOG = Logger.getLogger(Profile.class);
    private static final String DELETE_FROM_PROFILE_ATTRIBUTES = "DELETE FROM profile_attributes WHERE user_id = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + Profile.class);
            try (PreparedStatement deleteFromProfile = connection.prepareStatement(DELETE_FROM_PROFILE_ATTRIBUTES)) {
                deleteFromProfile.setString(1, uuid);
                deleteFromProfile.execute();
            }
        }
    }
}
