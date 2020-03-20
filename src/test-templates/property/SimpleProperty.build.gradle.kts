/*
 * Copyright (c) 2018-2020 Leon Linhart,
 * All rights reserved.
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
val packageName = "com.github.osmerion.quitte.property"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<Object>" else ""
    val typeDiamonds = if (type === Type.OBJECT) "<>" else ""
    val valAnno = if (type === Type.OBJECT) "@Nullable " else ""

    template("${packageName.replace('.', '/')}/Simple${type.abbrevName}PropertyGeneratedTest", isTest = true) {
        """package $packageName;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link Simple${type.abbrevName}Property}.
 *
 * @author  Leon Linhart
 */
public final class Simple${type.abbrevName}PropertyGeneratedTest {

    @Test
    public void testChangeListener() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        property.addListener((observable, oldValue, newValue) -> {
            flag.set(true);
            assertEquals(TestValues.${type.abbrevName}Value_1, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_2, newValue);
        });
        
        property.set(TestValues.${type.abbrevName}Value_2);
        assertTrue(flag.get());
    }

    @Test
    public void testChangeListenerInvalidation() {
        AtomicInteger counter = new AtomicInteger(0);
    
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        property.addListener(new ${type.abbrevName}ChangeListener$typeDiamonds() {

            @Override
            public void onChanged(Observable${type.abbrevName}Value$typeParams observable, $valAnno${if (type === Type.OBJECT) "Object" else type.raw} oldValue, $valAnno${if (type === Type.OBJECT) "Object" else type.raw} newValue) {
                counter.incrementAndGet();
            }
            
            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.${type.abbrevName}Value_2);
        property.set(TestValues.${type.abbrevName}Value_1);

        assertEquals(1, counter.get());
    }

    @Test
    @DisplayName("ChangeListener on set invocation with previous value")
    public void testChangeListenerSkipped() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        property.addListener((observable, oldValue, newValue) -> flag.set(true));
        
        property.set(TestValues.${type.abbrevName}Value_1);
        assertFalse(flag.get());
    }

    @Test
    public void testInvalidationListener() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        property.addListener(observable -> flag.set(true));
        
        property.set(TestValues.${type.abbrevName}Value_2);
        assertTrue(flag.get());
    }

    @Test
    public void testInvalidationListenerInvalidation() {
        AtomicInteger counter = new AtomicInteger(0);
    
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        property.addListener(new InvalidationListener() {

            @Override
            public void onInvalidation(Observable observable) {
                counter.incrementAndGet();
            }
            
            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.${type.abbrevName}Value_2);
        property.set(TestValues.${type.abbrevName}Value_1);

        assertEquals(1, counter.get());
    }

    @Test
    @DisplayName("InvalidationListener on set invocation with previous value")
    public void testInvalidationListenerSkipped() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        property.addListener(observable -> flag.set(true));
        
        property.set(TestValues.${type.abbrevName}Value_1);
        assertFalse(flag.get());
    }

    @Test
    @DisplayName("set-get consistency")
    public void testSetGetConsistency() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        assertEquals(TestValues.${type.abbrevName}Value_1, property.get());
        
        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
    }

    @Test
    @DisplayName("Simple${type.abbrevName}Property#set() return value")
    public void testSetReturn() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamonds(TestValues.${type.abbrevName}Value_1);
        assertEquals(TestValues.${type.abbrevName}Value_1, property.set(TestValues.${type.abbrevName}Value_2));
    }

}"""
    }
}