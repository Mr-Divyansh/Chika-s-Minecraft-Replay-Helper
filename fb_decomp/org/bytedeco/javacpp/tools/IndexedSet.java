/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.util.Iterator;
import java.util.LinkedHashMap;

class IndexedSet<E>
extends LinkedHashMap<E, Integer>
implements Iterable<E> {
    IndexedSet() {
    }

    public int index(E e) {
        Integer i = (Integer)this.get(e);
        if (i == null) {
            i = this.size();
            this.put(e, i);
        }
        return i;
    }

    @Override
    public Iterator<E> iterator() {
        return this.keySet().iterator();
    }
}

