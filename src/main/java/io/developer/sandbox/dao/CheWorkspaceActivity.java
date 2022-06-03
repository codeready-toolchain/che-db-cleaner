package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class CheWorkspaceActivity {
    private static final Logger LOG = Logger.getLogger(CheWorkspaceActivity.class);
    private static final String DELETE_FROM_CHE_WORKSPACE_ACTIVITY = "DELETE FROM che_workspace_activity WHERE workspace_id = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + CheWorkspaceActivity.class);
            try (PreparedStatement deleteFromWorkspace = connection.prepareStatement(DELETE_FROM_CHE_WORKSPACE_ACTIVITY)) {
                deleteFromWorkspace.setString(1, id);
                deleteFromWorkspace.execute();
            }
        }
    }
}
