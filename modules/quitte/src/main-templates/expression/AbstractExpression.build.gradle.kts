/*
 * Copyright (c) 2018-2021 Leon Linhart,
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
val packageName = "com.osmerion.quitte.expression"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<T>" else ""

    template("${packageName.replace('.', '/')}/Abstract${type.abbrevName}Expression") {
        """package $packageName;

import java.util.concurrent.CopyOnWriteArraySet;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.osmerion.quitte.internal.wrappers.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * ${if (type === Type.OBJECT)
            "A basic implementation for a generic expression."
        else
            "A basic implementation for a specialized {@code ${type.raw}} expression."
        }
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class Abstract${type.abbrevName}Expression$typeParams extends AbstractExpression implements ValueExpression<${type.box}>, Observable${type.abbrevName}Value$typeParams {

    private final transient CopyOnWriteArraySet<${type.abbrevName}ChangeListener$typeParams> changeListeners = new CopyOnWriteArraySet<>();

    // package-private constructor for an effectively sealed class
    Abstract${type.abbrevName}Expression() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(${type.abbrevName}ChangeListener$typeParams listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedListener(ChangeListener<${type.box}> listener) {
        if (this.changeListeners.stream().anyMatch(it -> it instanceof Wrapping${type.abbrevName}ChangeListener && ((Wrapping${type.abbrevName}ChangeListener$typeParams) it).isWrapping(listener))) return false;
        return this.changeListeners.add(${type.abbrevName}ChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(${type.abbrevName}ChangeListener$typeParams listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedListener(ChangeListener<${type.box}> listener) {
        return this.changeListeners.removeIf(it -> it instanceof Wrapping${type.abbrevName}ChangeListener && ((Wrapping${type.abbrevName}ChangeListener$typeParams) it).isWrapping(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    public ${type.raw} get() {
        return this.getImpl();
    }

    /** <b>This method must provide raw setter access and should not be called directly.</b> */${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    abstract ${type.raw} getImpl();

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value);

    @Override
    void doInvalidate() {
        if (this.updateValue(this.recomputeValue(), false)) this.notifyInvalidationListeners();
    }
${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    protected abstract ${type.raw} recomputeValue();

    final boolean updateValue(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value, boolean notifyListeners) {
        var prev = this.getImpl();
        var changed = prev != value;

        if (changed) {
            this.setImpl(value);
            notifyListeners = true;
        }

        if (notifyListeners) {
            if (this.onChangedInternal(prev, value) && !changed) return false;
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

    boolean onChangedInternal(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} oldValue, ${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} newValue) {
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
    protected void onChanged(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} oldValue, ${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} newValue) {}

    /**
     * Called when this property was invalidated.
     *
     * @since   0.1.0
     */
    protected void onInvalidated() {}

}"""
    }
}