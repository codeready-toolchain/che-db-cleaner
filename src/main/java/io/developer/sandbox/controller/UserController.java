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
package io.developer.sandbox.controller;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import io.developer.sandbox.CheDatabaseCleaner;
import io.developer.sandbox.validator.UUIDValidator;

@Path("")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Inject
    UUIDValidator validator;

    @Inject
    CheDatabaseCleaner cleaner;

    @Path("{uuid}")
    @Produces(MediaType.TEXT_PLAIN)
    @DELETE
    public Response deleteUserData(final String uuid) {
        try {
            validator.validate(uuid);
            cleaner.clean(uuid);
        } catch (IllegalArgumentException e) {
            LOG.error("{} is not valid UUID", uuid, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (SQLException e) {
            LOG.error("Error processing database connection / statement", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            LOG.error("User '" + uuid + "' not found in the databse", e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOG.info("Data for user '" + uuid + "' has been succesfully deleted");
        return Response.ok("Data for user '" + uuid + "' has been succesfully deleted", MediaType.TEXT_PLAIN).build();
    }

}
