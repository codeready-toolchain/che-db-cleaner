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

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import io.agroal.api.AgroalDataSource;

import javax.ws.rs.core.MediaType;

@Path("")
public class CheDatabaseCleaner {
    private static final Logger LOG = Logger.getLogger(CheDatabaseCleaner.class);

    @Inject
    AgroalDataSource dataSource;

    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @DELETE
    public Response deleteUserData(String id) {

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connection has been created: " + connection);
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM usr");
                resultSet.next();
                LOG.info("Result set:" + resultSet);
            } catch (SQLException e) {
                LOG.error("Error processing statement: " + e);
            }
        } catch (SQLException e) {
            LOG.error("Error processing connection: " + e);
        }

        return Response.ok(id, MediaType.TEXT_PLAIN).build();
    }

}
