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
package com.github.osmerion.quitte.expression;

import java.util.IdentityHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Nullable;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.internal.wrappers.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a generic expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObjectExpression<T> implements Expression<T>, ObservableObjectValue<T> {

    private final transient CopyOnWriteArraySet<ObjectChangeListener<T>> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient IdentityHashMap<Observable, WeakInvalidationListener> dependencies;

    // package-private constructor for an effectively sealed class
    AbstractObjectExpression() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(ObjectChangeListener<T> listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedListener(ChangeListener<T> listener) {
        if (this.changeListeners.stream().anyMatch(it -> it instanceof WrappingObjectChangeListener && ((WrappingObjectChangeListener<T>) it).isWrapping(listener))) return false;
        return this.changeListeners.add(ObjectChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(ObjectChangeListener<T> listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedListener(ChangeListener<T> listener) {
        return this.changeListeners.removeIf(it -> it instanceof WrappingObjectChangeListener && ((WrappingObjectChangeListener<T>) it).isWrapping(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    public final boolean addListener(InvalidationListener listener) {
        return this.invalidationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    public final boolean removeListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    @Nullable
    public T get() {
        return this.getImpl();
    }

    /** <b>This method must provide raw setter access and should not be called directly.</b> */
    @Nullable
    abstract T getImpl();

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(@Nullable T value);

    /**
     * Invalidates the result of this expression.
     *
     * @since   0.1.0
     */
    protected final void invalidate() {
        this.doInvalidate();
    }

    void doInvalidate() {
        if (this.updateValue(this.recomputeValue(), false)) this.notifyInvalidationListeners();
    }

    final void notifyInvalidationListeners() {
        for (var listener : this.invalidationListeners) {
            if (listener.isInvalid()) {
                this.invalidationListeners.remove(listener);
                continue;
            }

            listener.onInvalidation(this);
            if (listener.isInvalid()) this.invalidationListeners.remove(listener);
        }
    }

    /**
     * Adds a dependency for this expression. This expression will be invalidated when the given {@link Observable} is
     * invalidated.
     *
     * @param observable    the observable on which this expression should depend
     *
     * @throws IllegalArgumentException if this expression already depends on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final void addDependency(Observable observable) {
        if (this.dependencies == null) this.dependencies = new IdentityHashMap<>();

        WeakInvalidationListener listener = new WeakInvalidationListener(ignored -> this.doInvalidate());
        this.dependencies.compute(observable, (key, oldValue) -> {
            if (oldValue != null) throw new IllegalArgumentException("Expression already depends on observable: " + observable);

            observable.addListener(listener);
            return listener;
        });
    }

    /**
     * Adds a dependency for this expression. This expression will be invalidated when the given {@link Observable} is
     * invalidated.
     *
     * <p>The given {@link Runnable} is executed when the given {@link Observable} is invalidated and may be used to
     * implemented side effects.</p>
     *
     * @param observable    the observable on which this expression should depend
     * @param action        the action that should be performed when the dependency is invalidated but before the value
     *                      of this action is recomputed
     *
     * @throws IllegalArgumentException if this expression already depends on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final void addDependency(Observable observable, Runnable action) {
        if (this.dependencies == null) this.dependencies = new IdentityHashMap<>();

        WeakInvalidationListener listener = new WeakInvalidationListener(ignored -> {
            action.run();
            this.doInvalidate();
        });
        this.dependencies.compute(observable, (key, oldValue) -> {
            if (oldValue != null) throw new IllegalArgumentException("Expression already depends on observable: " + observable);

            observable.addListener(listener);
            return listener;
        });
    }

    /**
     * Removes a dependency for this expression.
     *
     * @param observable    the observable on which this expression should not longer depend
     *
     * @throws IllegalArgumentException if this expression does not depend on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final void removeDependency(Observable observable) {
        if (this.dependencies == null) throw new IllegalArgumentException("Expression does not depend on observable: " + observable);

        WeakInvalidationListener listener = this.dependencies.remove(observable);
        if (listener == null) throw new IllegalArgumentException("Expression does not depend on observable: " + observable);

        observable.removeListener(listener);
    }

    @Nullable
    protected abstract T recomputeValue();

    final boolean updateValue(@Nullable T value, boolean notifyListeners) {
        var prev = this.getImpl();
        var changed = prev != value;

        if (changed) {
            this.setImpl(value);
            notifyListeners = true;
        }

        if (notifyListeners) {
            if (this.onChangedInternal(prev, value) && !changed) return changed;
            this.onChanged(prev, value);

            for (var listener : this.changeListeners) {
                if (listener.isInvalid()) {
                    this.changeListeners.remove(listener);
                    continue;
                }

                listener.onChanged(this, prev, this.getImpl());
                if (listener.isInvalid()) this.changeListeners.remove(listener);
            }
        }

        return changed;
    }

    boolean onChangedInternal(@Nullable T oldValue, @Nullable T newValue) {
        return true;
    }

    /**
     * Called when this property's value has changed.
     *
     * @param oldValue  the old value
     * @param newValue  the new value
     *
     * @since   0.1.0
     */
    protected void onChanged(@Nullable T oldValue, @Nullable T newValue) {}

    /**
     * Called when this property was invalidated.
     *
     * @since   0.1.0
     */
    protected void onInvalidated() {}

}