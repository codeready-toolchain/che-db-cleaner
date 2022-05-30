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
package io.developer.sandbox.validator;

import java.util.UUID;

import javax.inject.Singleton;

@Singleton
public class UUIDValidator {


    public void validate(final String uuid) {
        UUID.fromString(uuid);
    }

}
