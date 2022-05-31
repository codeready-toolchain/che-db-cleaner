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
package io.developer.sandbox;

import javax.ws.rs.Produces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jboss.logging.Logger;

import io.developer.sandbox.validator.UUIDValidator;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.MediaType;

@Path("")
public class CheDatabaseCleaner {
    private static final Logger LOG = Logger.getLogger(CheDatabaseCleaner.class);

    @Inject
    DataSource dataSource;

    @Inject
    UUIDValidator validator;

    @Path("{uuid}")
    @Produces(MediaType.TEXT_PLAIN)
    @DELETE
    public Response deleteUserData(final String uuid) {
        try {
            validator.validate(uuid);
        try (Connection connection = dataSource.getConnection()) {
            LOG.info("Connection has been obtained");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM usr where id=" + uuid);
                while (resultSet.next()) {
                    LOG.info("Data: " + resultSet.getString(1));
                }
            } catch (SQLException e) {
                LOG.error("Error processing statement", e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException e) {
            LOG.error("Error processing connection", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        } catch (IllegalArgumentException e) {
            LOG.error("{} is not valid UUID", uuid, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok("Data for user '" + uuid + "' has been deleted", MediaType.TEXT_PLAIN).build();
    }

}
