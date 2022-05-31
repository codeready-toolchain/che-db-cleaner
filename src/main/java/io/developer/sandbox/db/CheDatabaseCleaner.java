/*
 * Copyright (c) 2022 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package io.developer.sandbox.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

@Singleton
public class CheDatabaseCleaner {
    private static final String DELETE_FROM_PROFILE = "SELECT * FROM profile WHERE id = ?";
    private static final String DELETE_FROM_USR = "SELECT * FROM usr WHERE id = ?";
    private static final String DELETE_FROM_PREFERENCE = "SELECT * FROM preference WHERE id = ?";
    private static final String DELETE_FROM_PREFERENCE_PREFERENCES = "SELECT * FROM preference_preferences WHERE id = ?";

    private static final Logger LOG = Logger.getLogger(CheDatabaseCleaner.class);

    @Inject
    DataSource dataSource;

    public void clean(final String uuid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained");

            PreparedStatement deleteFromProfile = connection.prepareStatement(DELETE_FROM_PROFILE);
            deleteFromProfile.setString(1, uuid);
            deleteFromProfile.execute();

            LOG.info("Data has been deleted from 'profile' table for user " + uuid);

            PreparedStatement deleteFromPreference = connection.prepareStatement(DELETE_FROM_PREFERENCE);
            deleteFromPreference.setString(1, uuid);
            deleteFromPreference.execute();

            LOG.info("Data has been deleted from 'preference' table for user " + uuid);

            PreparedStatement deleteFromPreferencePreferences = connection
                    .prepareStatement(DELETE_FROM_PREFERENCE_PREFERENCES);
            deleteFromPreferencePreferences.setString(1, uuid);
            deleteFromPreferencePreferences.execute();

            LOG.info("Data has been deleted from 'preference_preferences' table for user " + uuid);

            PreparedStatement deleteFromUsr = connection.prepareStatement(DELETE_FROM_USR);
            deleteFromUsr.setString(1, uuid);
            deleteFromUsr.execute();

            LOG.info("Data has been deleted from 'usr' table for user " + uuid );
        }
    }

}
