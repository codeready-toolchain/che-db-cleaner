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

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UUIDValidatorTest {

    public static final String VALID_UUID = "1c8d64e1-6068-47be-8338-091364f98791";
    public static final String INVALID_UUID = "1c8d64e1-6068-47be-8338-091364f98791;";
    public static final String SQL_INJECTION_UUID = "1c8d64e1-6068-47be-8338-091364f98791; DROP TABLE usr";

    @Inject
    UUIDValidator validator;

    @Test
    public void randomUUID() {
        validator.validate(UUID.randomUUID().toString());
    }

    @Test
    public void validUUID() {
        validator.validate(VALID_UUID);
    }

    @Test
    public void invalidUUID() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(INVALID_UUID);
        });
    }

    @Test
    public void sqlInjectionUUID() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(SQL_INJECTION_UUID);
        });
    }

}
