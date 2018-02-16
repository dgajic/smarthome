/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.binding.unipievok.internal;

/**
 *
 * @author Dragan Gajic
 *
 */
public class UniPiServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8193158391923634841L;

    public UniPiServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniPiServiceException(String message) {
        super(message);
    }

}
