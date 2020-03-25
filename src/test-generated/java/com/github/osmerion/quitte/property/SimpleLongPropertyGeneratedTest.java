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
 * Generated tests for {@link SimpleLongProperty}.
 *
 * @author  Leon Linhart
 */
public final class SimpleLongPropertyGeneratedTest {

    @Test
    public void testInitialGetConsistency() {
        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_2);
        assertEquals(TestValues.LongValue_2, property.get());
    }

    @Test
    public void testSetGetConsistency() {
        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        assertEquals(TestValues.LongValue_1, property.get());

        property.set(TestValues.LongValue_2);
        assertEquals(TestValues.LongValue_2, property.get());
    }

    @Test
    public void testSetReturn() {
        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        assertEquals(TestValues.LongValue_1, property.set(TestValues.LongValue_2));
    }

    @Test
    public void testChangeListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.LongValue_1, oldValue);
            assertEquals(TestValues.LongValue_2, newValue);
            assertEquals(TestValues.LongValue_2, property.get());
        });

        property.set(TestValues.LongValue_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.LongValue_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        property.addListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.LongValue_2, property.get());
        }));

        property.set(TestValues.LongValue_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        property.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.LongValue_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_2);
        wrapper.addListener((observable, oldValue, newValue) -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> {
                    assertEquals(TestValues.LongValue_2, oldValue);
                    assertEquals(TestValues.LongValue_1, newValue);
                    assertEquals(TestValues.LongValue_1, property.get());
                }
                case 1 -> {
                    assertEquals(TestValues.LongValue_1, oldValue);
                    assertEquals(TestValues.LongValue_2, newValue);
                    assertEquals(TestValues.LongValue_2, property.get());
                }
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.LongValue_2);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_1);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_2);
        wrapper.addListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.LongValue_1, property.get());
                case 1 -> assertEquals(TestValues.LongValue_2, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.LongValue_2);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_1);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_1);
        wrapper.bindTo(property);

        wrapper.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.LongValue_1, oldValue);
            assertEquals(TestValues.LongValue_2, newValue);
            assertEquals(TestValues.LongValue_2, property.get());
        });

        property.set(TestValues.LongValue_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_1);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.LongValue_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_1);
        wrapper.bindTo(property);

        wrapper.addListener(observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.LongValue_2, property.get());
        });

        property.set(TestValues.LongValue_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        SimpleLongProperty wrapper = new SimpleLongProperty(TestValues.LongValue_1);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.LongValue_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
        property.addListener(new LongChangeListener() {

            @Override
            public void onChanged(ObservableLongValue observable, long oldValue, long newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.LongValue_2);
        property.set(TestValues.LongValue_1);

        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleLongProperty property = new SimpleLongProperty(TestValues.LongValue_1);
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
        property.set(TestValues.LongValue_2);
        property.set(TestValues.LongValue_1);

        assertEquals(1, callCounter.get());
    }

}