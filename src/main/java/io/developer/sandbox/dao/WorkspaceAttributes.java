package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class WorkspaceAttributes {
    private static final Logger LOG = Logger.getLogger(WorkspaceAttributes.class);
    private static final String DELETE_FROM_WORKSPACE_ATTRIBUTES = "DELETE FROM workspace_attributes WHERE workspace_id = ?";

    @Inject
    DataSource dataSource;

    public void delete(final String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + WorkspaceAttributes.class);
            try (PreparedStatement deleteFromWorkspace = connection.prepareStatement(DELETE_FROM_WORKSPACE_ATTRIBUTES)) {
                deleteFromWorkspace.setString(1, id);
                deleteFromWorkspace.execute();
            }
        }
    }
}
