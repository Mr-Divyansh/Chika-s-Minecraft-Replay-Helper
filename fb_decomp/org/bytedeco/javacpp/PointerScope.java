/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.tools.Logger;

public class PointerScope
implements AutoCloseable {
    private static final Logger logger = Logger.create(PointerScope.class);
    static final ThreadLocal<Deque<PointerScope>> scopeStack = new ThreadLocal<Deque<PointerScope>>(){

        @Override
        protected Deque initialValue() {
            return new ArrayDeque();
        }
    };
    Deque<Pointer> pointerStack = new ArrayDeque<Pointer>();
    Class<? extends Pointer>[] forClasses = null;
    boolean extend = false;

    public static PointerScope getInnerScope() {
        return scopeStack.get().peek();
    }

    public static Iterator<PointerScope> getScopeIterator() {
        return scopeStack.get().iterator();
    }

    public PointerScope() {
        this(null);
    }

    public PointerScope(Class<? extends Pointer> ... forClasses) {
        if (logger.isDebugEnabled()) {
            logger.debug("Opening " + this);
        }
        this.forClasses = forClasses;
        scopeStack.get().push(this);
    }

    public Class<? extends Pointer>[] forClasses() {
        return this.forClasses;
    }

    public PointerScope attach(Pointer p) {
        if (logger.isDebugEnabled()) {
            logger.debug("Attaching " + p + " to " + this);
        }
        if (this.forClasses != null && this.forClasses.length > 0) {
            boolean found = false;
            for (Class<? extends Pointer> c : this.forClasses) {
                if (c == null || !c.isInstance(p)) continue;
                found = true;
                break;
            }
            if (!found) {
                throw new IllegalArgumentException(p + " is not an instance of a class in forClasses: " + Arrays.toString(this.forClasses));
            }
        }
        this.pointerStack.push(p);
        p.retainReference();
        return this;
    }

    public PointerScope detach(Pointer p) {
        if (logger.isDebugEnabled()) {
            logger.debug("Detaching " + p + " from " + this);
        }
        this.pointerStack.remove(p);
        p.releaseReference();
        return this;
    }

    public PointerScope extend() {
        if (logger.isDebugEnabled()) {
            logger.debug("Extending " + this);
        }
        this.extend = true;
        return this;
    }

    @Override
    public void close() {
        if (logger.isDebugEnabled()) {
            logger.debug("Closing " + this);
        }
        if (this.extend) {
            this.extend = false;
        } else {
            while (this.pointerStack.size() > 0) {
                this.pointerStack.pop().releaseReference();
            }
        }
        scopeStack.get().remove(this);
    }

    public void deallocate() {
        if (logger.isDebugEnabled()) {
            logger.debug("Deallocating " + this);
        }
        while (this.pointerStack.size() > 0) {
            this.pointerStack.pop().deallocate();
        }
    }
}

