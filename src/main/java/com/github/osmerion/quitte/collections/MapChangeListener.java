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
package com.github.osmerion.quitte.collections;

import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableMap ObservableMaps}.
 *
 * @param <K>   the type of an observed map's keys
 * @param <V>   the type of an observed map's values
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface MapChangeListener<K, V> {

    /**
     * Called after a change has been made to an observed map.
     *
     * @param change    the change that was made
     *
     * @since   0.1.0
     */
    void onChanged(Change<? extends K, ? extends V> change);

    /**
     * Returns whether or not this listener is invalid.
     *
     * @return  whether or not this listener is invalid
     *
     * @since   0.1.0
     */
    default boolean isInvalid() {
        return false;
    }

    /**
     * A change done to an {@link ObservableMap}.
     *
     * @param <K>   the type of the map's elements
     * @param <V>   the type of the map's elements
     *
     * @since   0.1.0
     */
    final class Change<K, V> {

        private final Map<K, V> added, removed;

        Change(@Nullable Map<K, V> added, @Nullable Map<K, V> removed) {
            this.added = added != null ? Collections.unmodifiableMap(added) : Collections.emptyMap();
            this.removed = removed != null ? Collections.unmodifiableMap(removed) : Collections.emptyMap();
        }

        /**
         * Returns the entries that were added to the observed map as part of this change.
         *
         * @return  the entries that were added to the observed map as part of this change
         *
         * @since   0.1.0
         */
        public Map<K, V> getAddedElements() {
            return this.added;
        }

        /**
         * Returns the entries that were removed from the observed map as part of this change.
         *
         * @return  the entries that were removed from the observed map as part of this change
         *
         * @since   0.1.0
         */
        public Map<K, V> getRemovedElements() {
            return this.removed;
        }

    }

}