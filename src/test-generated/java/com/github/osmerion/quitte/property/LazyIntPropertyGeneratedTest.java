/*
 * Copyright (c) 2018-2020 Leon Linhart,
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
package com.github.osmerion.quitte.property;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link LazyIntProperty}.
 *
 * @author  Leon Linhart
 */
public final class LazyIntPropertyGeneratedTest {

    @Test
    public void testInitialGetConsistencyForPrimaryCtor() {
        var property = new LazyIntProperty(TestValues.IntValue_H);
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(TestValues.IntValue_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
    }

    @Test
    public void testInitialGetConsistencyForLazyInitialization() {
        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(TestValues.IntValue_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
    }

    @Test
    public void testSetReturnForPrimaryCtor() {
        var property = new LazyIntProperty(TestValues.IntValue_H);
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(TestValues.IntValue_H, property.set(TestValues.IntValue_L));
        assertEquals(LazyValue.State.INVALID, property.getState());
    }

    @Test
    public void testSetReturnForLazyInitialization() {
        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(TestValues.IntValue_N, property.set(TestValues.IntValue_L));
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
    }

    @Test
    public void testSetGetStateLifecycleForPrimaryCtor() {
        var propertyInvalidatedCallCounter = new AtomicInteger(0);
        var stateChangedCallCounter = new AtomicInteger(0);
        var stateInvalidatedCallCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener((observable -> propertyInvalidatedCallCounter.getAndIncrement()));

        var state = property.stateProperty();
        state.addListener(((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement()));
        state.addListener(((observable) -> stateInvalidatedCallCounter.getAndIncrement()));

        assertEquals(LazyValue.State.INITIALIZED, property.getState());

        property.set(TestValues.IntValue_L);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.IntValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());

        property.set(TestValues.IntValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(4, stateChangedCallCounter.get());
        assertEquals(4, stateInvalidatedCallCounter.get());
    }

    @Test
    public void testSetGetStateLifecycleForLazyInitialization() {
        var propertyInvalidatedCallCounter = new AtomicInteger(0);
        var stateChangedCallCounter = new AtomicInteger(0);
        var stateInvalidatedCallCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        property.addListener((observable -> propertyInvalidatedCallCounter.getAndIncrement()));

        var state = property.stateProperty();
        state.addListener(((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement()));
        state.addListener(((observable) -> stateInvalidatedCallCounter.getAndIncrement()));

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());

        property.set(TestValues.IntValue_L);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(0, propertyInvalidatedCallCounter.get());
        assertEquals(0, stateChangedCallCounter.get());
        assertEquals(0, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(0, propertyInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.set(TestValues.IntValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.IntValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());
    }

    @Test
    public void testChangeListenerSetGetConsistencyForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.VALID, property.getState());
            assertEquals(TestValues.IntValue_H, oldValue);
            assertEquals(TestValues.IntValue_L, newValue);
            assertEquals(TestValues.IntValue_L, property.get());
        });

        property.set(TestValues.IntValue_L);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.IntValue_L, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSetGetConsistencyForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.INITIALIZED, property.getState());
            assertEquals(TestValues.IntValue_N, oldValue);
            assertEquals(TestValues.IntValue_L, newValue);
            assertEquals(TestValues.IntValue_L, property.get());
        });

        property.set(TestValues.IntValue_L);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.IntValue_L, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnSetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.IntValue_H);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnSetForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.IntValue_H);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSetGetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());

        property.set(TestValues.IntValue_H);
        assertEquals(TestValues.IntValue_H, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerInitForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(1, callCounter.get());
            assertEquals(LazyValue.State.INITIALIZED, property.getState());
            assertEquals(TestValues.IntValue_N, oldValue);
            assertEquals(TestValues.IntValue_H, newValue);
        });

        assertEquals(TestValues.IntValue_H, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnSetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener(observable -> {
            callCounter.getAndIncrement();
            assertEquals(LazyValue.State.INVALID, property.getState());
            assertEquals(TestValues.IntValue_L, property.get());
        });

        property.set(TestValues.IntValue_L);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSetForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.IntValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener(new IntChangeListener() {

            @Override
            public void onChanged(ObservableIntValue observable, int oldValue, int newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });

        property.set(TestValues.IntValue_L);
        assertEquals(TestValues.IntValue_L, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        property.addListener(new InvalidationListener() {

            @Override
            public void onInvalidation(Observable observable) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });

        property.set(TestValues.IntValue_L);
        assertEquals(TestValues.IntValue_L, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnBindingCreated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_H);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnGetForBindingWithSameValue() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_H);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(TestValues.IntValue_H, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_L);
        wrapper.addListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.IntValue_H, property.get());
                case 1 -> assertEquals(TestValues.IntValue_L, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);
        assertEquals(1, callCounter.get());
        assertEquals(TestValues.IntValue_H, wrapper.get());

        property.set(TestValues.IntValue_L);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_H);
        wrapper.bindTo(property);

        wrapper.addListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(TestValues.IntValue_H, oldValue);
            assertEquals(TestValues.IntValue_L, newValue);
            assertEquals(TestValues.IntValue_L, property.get());
        });

        property.set(TestValues.IntValue_L);
        assertEquals(TestValues.IntValue_L, wrapper.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnBindingUpdated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_H);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.IntValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(() -> TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_H);
        wrapper.bindTo(property);

        wrapper.addListener(observable -> callCounter.getAndIncrement());

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());
        
        assertEquals(TestValues.IntValue_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.IntValue_H, wrapper.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());

        property.set(TestValues.IntValue_L);
        assertEquals(1, callCounter.get());

        assertEquals(TestValues.IntValue_L, property.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.IntValue_L, wrapper.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());
    }

    @Test
    public void testInvalidationListenerDeferredOnBindingUpdated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyIntProperty(TestValues.IntValue_H);
        var wrapper = new LazyIntProperty(TestValues.IntValue_H);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.IntValue_H);
        assertEquals(0, callCounter.get());
    }

}