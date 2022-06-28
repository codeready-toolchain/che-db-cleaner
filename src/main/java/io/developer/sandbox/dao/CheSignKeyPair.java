package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class CheSignKeyPair {

    private static final Logger LOG = Logger.getLogger(CheSignKeyPair.class);
    private static final String DELETE_FROM_SIGN_KEY_PAIR = "DELETE FROM che_sign_key_pair WHERE workspace_id = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + CheSignKeyPair.class);
            try (PreparedStatement deleteFromCheSignKeyPair = connection.prepareStatement(DELETE_FROM_SIGN_KEY_PAIR)) {
                deleteFromCheSignKeyPair.setString(1, id);
                deleteFromCheSignKeyPair.execute();
            }
        }
    }


}
