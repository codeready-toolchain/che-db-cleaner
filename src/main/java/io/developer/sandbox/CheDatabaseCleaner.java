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

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.logging.Logger;

import io.developer.sandbox.dao.Usr;

@Singleton
public class CheDatabaseCleaner {
    private static final Logger LOG = Logger.getLogger(CheDatabaseCleaner.class);

    @Inject
    Usr usr;

    public void clean(final String uuid) throws SQLException {
        LOG.info("Deleting the data for user: " + uuid + "...");
        usr.delete(uuid);
    }

}
