/*
 * Copyright (c) 2018-2021 Leon Linhart,
 * All rights reserved.
 * MACHINE GENERATED FILE, DO NOT EDIT
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.osmerion.quitte.internal.wrappers;

import com.osmerion.quitte.*;
import com.osmerion.quitte.property.*;
import com.osmerion.quitte.value.change.*;

/**
 * A specialized read-only {@code double} property.
 *
 * @author  Leon Linhart
 */
public final class ReadOnlyDoubleProperty implements ReadableDoubleProperty {

    protected final ReadableDoubleProperty property;

    public ReadOnlyDoubleProperty(ReadableDoubleProperty property) {
        this.property = property;
    }

    @Override
    public ReadableDoubleProperty asReadOnlyProperty() {
        return this;
    }

    @Override
    public double get() {
        return this.property.get();
    }

	@Override
    public Double getValue() {
        return this.property.getValue();
    }

	@Override
    public boolean isBound() {
        return this.property.isBound();
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean addListener(DoubleChangeListener listener) {
        return this.property.addListener(listener);
    }

	@Override
    public boolean addBoxedListener(ChangeListener<Double> listener) {
        return this.property.addBoxedListener(listener);
    }

    @Override
    public boolean removeListener(DoubleChangeListener listener) {
        return this.property.removeListener(listener);
    }

    @Override
    public boolean removeBoxedListener(ChangeListener<Double> listener) {
        return this.property.removeBoxedListener(listener);
    }

    @Override
    public final boolean addListener(InvalidationListener listener) {
        return this.property.addListener(listener);
    }

    @Override
    public final boolean removeListener(InvalidationListener listener) {
        return this.property.removeListener(listener);
    }

}