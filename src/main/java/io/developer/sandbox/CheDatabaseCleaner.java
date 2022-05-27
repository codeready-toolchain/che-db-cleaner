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
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;


@Path("")
public class CheDatabaseCleaner {
	
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@DELETE
	public Response deleteUserData(String id) {
		return Response.ok(id, MediaType.TEXT_PLAIN).build();
	}

}
