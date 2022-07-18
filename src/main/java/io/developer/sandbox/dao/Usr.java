package io.developer.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class Usr {
    private static final Logger LOG = Logger.getLogger(Usr.class);
    private static final String ID_COLUMN_LABEL = "id";
    private static final String SELECT_FROM_USR = "SELECT * FROM usr WHERE id = ?";
    private static final String SELECT_FROM_WORKER = "SELECT id FROM che_worker WHERE user_id = ?";
    private static final String SELECT_FROM_WORKSPACE = "SELECT id FROM workspace WHERE accountid = ?";
    private static final String DELETE_FROM_USR = "DELETE FROM usr WHERE id = ?";

    @Inject
    DataSource dataSource;

    @Inject
    PreferencePreferences preferencePreferences;

    @Inject
    Preference preference;

    @Inject
    CheWorker cheWorker;

    @Inject
    Account account;

    @Inject
    WorkspaceAttributes workspaceAttributes;

    @Inject
    WorkspaceConfig workspaceConfig;

    @Inject
    CheWorkspaceActivity cheWorkspaceActivity;

    @Inject
    Workspace workspace;
    
    @Inject
    SSHKeyPair sshKeyPair;

    @Inject
    CheWorkerActions cheWorkerActions;

    @Inject
    CheSignKeyPair cheSignKeyPair;

    @Inject
    Profile profile;

    @Inject
    UserDevfile userDevfile;

    @Inject
    ProfileAttributes profileAttributes;

    public void delete(final String uuid) throws SQLException {
        deletePreferences(uuid);
        deleteSSHKeyPair(uuid);
        deleteCheWorkerActions(uuid);
        deleteCheWorker(uuid);
        deleteUserDevfile(uuid);

        deleteWorkspaces(uuid);

        deleteProfile(uuid);
        deleteAccount(uuid);
        deleteUsr(uuid);

    } 

    public boolean exists(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained for: " + Usr.class);
            try (PreparedStatement selectFromUsr = connection.prepareStatement(SELECT_FROM_USR)) {
                ResultSet usr = selectFromUsr.executeQuery();
                return usr.next();
            }
        }
    }

    private void deletePreferences(final String uuid) throws SQLException {
        preferencePreferences.delete(uuid);
        preference.delete(uuid);
    }
    
    private void deleteSSHKeyPair(final String uuid) throws SQLException {
        sshKeyPair.delete(uuid);
    }

    private void deleteProfile(final String uuid) throws SQLException {
        profileAttributes.delete(uuid);
        profile.delete(uuid);
    }

    private void deleteCheWorker(final String uuid) throws SQLException {
        cheWorker.delete(uuid);
    }

    private void deleteAccount(final String uuid) throws SQLException {
        account.delete(uuid);
    }

    private void deleteUserDevfile(final String uuid) throws SQLException {
        userDevfile.delete(uuid);
    }

    private void deleteUsr(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Removing the entry from the 'usr' table
            LOG.info("Connection has been obtained for: " + Usr.class);
            try (PreparedStatement deleteFromUsr = connection.prepareStatement(DELETE_FROM_USR)) {
                deleteFromUsr.setString(1, uuid);
                LOG.info("Deleting user: " + uuid);
                deleteFromUsr.execute();
            }
        }
    }

    private void deleteCheWorkerActions(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // getting che_worker ids related to the user uuid
            try (PreparedStatement selectFromWorker = connection.prepareStatement(SELECT_FROM_WORKER)) {
                selectFromWorker.setString(1, uuid);
                ResultSet workers = selectFromWorker.executeQuery();
                while (workers.next()) {
                    // deleting entries from the 'che_worker_actions' based on the che_worker id
                    String workerId = workers.getString(ID_COLUMN_LABEL);
                    LOG.info("Deleting Worker: " + workerId);
                    cheWorkerActions.delete(workerId);
                }
            }
        }
    }

    private void deleteWorkspaces(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // getting workspaces ids related to the user
            try (PreparedStatement selectFromWorkspace = connection.prepareStatement(SELECT_FROM_WORKSPACE)) {
                selectFromWorkspace.setString(1, uuid);
                ResultSet workspaces = selectFromWorkspace.executeQuery();
                while (workspaces.next()) {
                    // deleting entries from the 'workspace' based on the workspace id
                    String workspaceId = workspaces.getString(ID_COLUMN_LABEL);
                    LOG.info("Deleting Workspace: " + workspaceId);

                    workspaceAttributes.delete(workspaceId);
                    cheWorkspaceActivity.delete(workspaceId);
                    // TODO: also cleanup cheSignKey ?
                    cheSignKeyPair.delete(workspaceId);
                    workspaceConfig.delete(workspaceId);
                    workspace.delete(workspaceId);
                }
            }
        }
    }

}
