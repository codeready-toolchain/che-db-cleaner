package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class UserDevfile {
    private static final Logger LOG = Logger.getLogger(UserDevfile.class);
    private static final String DELETE_FROM_USERDEVFILE = "DELETE FROM userdevfile WHERE accountid = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + UserDevfile.class);
            try (PreparedStatement deleteFromUserDevfile = connection.prepareStatement(DELETE_FROM_USERDEVFILE)) {
                deleteFromUserDevfile.setString(1, uuid);
                deleteFromUserDevfile.execute();
            }
        }
    }

}
