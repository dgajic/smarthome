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
package org.eclipse.smarthome.binding.unipievok.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;

@NonNullByDefault
public class UniPiDigitalInputsHandler extends UniPiAbstractHandler<Digitalnput> {

    public UniPiDigitalInputsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public synchronized void onUpdate(Digitalnput digitalInput) {
        OnOffType state = digitalInput.isSet() ? OnOffType.ON : OnOffType.OFF;
        updateState(new ChannelUID(getThing().getUID(), "dinput"), state);
        logger.debug("Digital input updated to {}, for device with id {}", state, digitalInput.getId());
    }

}
