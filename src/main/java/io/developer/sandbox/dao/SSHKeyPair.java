package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class SSHKeyPair {
    private static final Logger LOG = Logger.getLogger(SSHKeyPair.class);
    private static final String DELETE_FROM_SSH_KEY_PAIR = "DELETE FROM sshkeypair WHERE owner = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + SSHKeyPair.class);
            try (PreparedStatement deleteFromSSHKeyPair = connection.prepareStatement(DELETE_FROM_SSH_KEY_PAIR)) {
                deleteFromSSHKeyPair.setString(1, id);
                deleteFromSSHKeyPair.execute();
            }
        }
    }

}
