/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.PointerScope;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.annotation.Raw;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class Pointer
implements AutoCloseable {
    private static final Logger logger;
    private static final ReferenceQueue<Pointer> referenceQueue;
    static final Thread deallocatorThread;
    static final long maxBytes;
    static final long maxPhysicalBytes;
    static final int maxRetries;
    static final boolean nativeAllocationTracerEnabled;
    static final Method tracerMarkPointerMethod;
    static final Method tracerMarkReferenceMethod;
    static final Method tracerRecordAllocationMethod;
    static final Method tracerRecordDeallocationMethod;
    static final Method tracerRecordCollectionMethod;
    protected long address = 0L;
    protected long position = 0L;
    protected long limit = 0L;
    protected long capacity = 0L;
    private Deallocator deallocator = null;

    public Pointer() {
    }

    public Pointer(Pointer p) {
        if (p != null) {
            this.address = p.address;
            this.position = p.position;
            this.limit = p.limit;
            this.capacity = p.capacity;
            if (p.deallocator != null) {
                this.deallocator = new ProxyDeallocator(this, p);
            }
        }
    }

    public Pointer(Buffer b) {
        if (b != null) {
            this.allocate(b);
        }
        if (!this.isNull()) {
            this.address -= (long)(b.position() * this.sizeof());
            this.position = b.position();
            this.limit = b.limit();
            this.capacity = b.capacity();
            this.deallocator = new ProxyDeallocator(this, b);
        }
    }

    private native void allocate(Buffer var1);

    void init(long allocatedAddress, long allocatedCapacity, long ownerAddress, long deallocatorAddress) {
        this.address = allocatedAddress;
        this.position = 0L;
        this.limit = allocatedCapacity;
        this.capacity = allocatedCapacity;
        if (ownerAddress != 0L && deallocatorAddress != 0L) {
            if (nativeAllocationTracerEnabled) {
                try {
                    tracerMarkPointerMethod.invoke(null, this);
                }
                catch (ReflectiveOperationException exception) {
                    logger.error("Unable to invoke NativeAllocationTracer method via reflection: " + exception);
                }
            }
            this.deallocator(new NativeDeallocator(this, ownerAddress, deallocatorAddress));
        }
    }

    protected <P extends Pointer> P offsetAddress(long i) {
        this.address += i * (long)this.sizeof();
        this.limit = Math.max(0L, this.limit - i);
        this.capacity = Math.max(0L, this.capacity - i);
        return (P)this;
    }

    protected static <P extends Pointer> P withDeallocator(P p) {
        return p.deallocator(new CustomDeallocator(p));
    }

    public static String formatBytes(long bytes) {
        if (bytes < 102400L) {
            return bytes + "";
        }
        if ((bytes /= 1024L) < 102400L) {
            return bytes + "K";
        }
        if ((bytes /= 1024L) < 102400L) {
            return bytes + "M";
        }
        if ((bytes /= 1024L) < 102400L) {
            return bytes + "G";
        }
        return bytes / 1024L + "T";
    }

    public static long parseBytes(String string, long relativeMultiple) throws NumberFormatException {
        int i;
        for (i = 0; i < string.length() && Character.isDigit(string.charAt(i)); ++i) {
        }
        long size = Long.parseLong(string.substring(0, i));
        switch (string.substring(i).trim().toLowerCase()) {
            case "%": {
                size = size * relativeMultiple / 100L;
                break;
            }
            case "t": 
            case "tb": {
                size *= 1024L;
            }
            case "g": 
            case "gb": {
                size *= 1024L;
            }
            case "m": 
            case "mb": {
                size *= 1024L;
            }
            case "k": 
            case "kb": {
                size *= 1024L;
            }
            case "": {
                break;
            }
            default: {
                throw new NumberFormatException("Cannot parse into bytes: " + string);
            }
        }
        return size;
    }

    public static void interruptDeallocatorThread() {
        if (deallocatorThread != null) {
            deallocatorThread.interrupt();
        }
    }

    public static void deallocateReferences() {
        DeallocatorReference r;
        while (referenceQueue != null && (r = (DeallocatorReference)referenceQueue.poll()) != null) {
            r.clear();
            r.remove();
        }
    }

    public static long maxBytes() {
        return maxBytes;
    }

    public static long totalBytes() {
        return DeallocatorReference.totalBytes;
    }

    public static long totalCount() {
        return DeallocatorReference.totalCount;
    }

    public static long maxPhysicalBytes() {
        return maxPhysicalBytes;
    }

    @Name(value={"JavaCPP_trimMemory"})
    public static native boolean trimMemory();

    @Name(value={"JavaCPP_physicalBytes"})
    public static native long physicalBytes();

    @Name(value={"JavaCPP_physicalBytes"})
    public static native long physicalBytesInaccurate(long var0);

    @Name(value={"JavaCPP_totalPhysicalBytes"})
    public static native long totalPhysicalBytes();

    @Name(value={"JavaCPP_availablePhysicalBytes"})
    public static native long availablePhysicalBytes();

    @Name(value={"JavaCPP_getDirectBufferAddress"})
    protected static native long getDirectBufferAddress(@Raw(withEnv=true) Buffer var0);

    public static boolean isNull(Pointer p) {
        return p == null || p.address == 0L;
    }

    public boolean isNull() {
        return this.address == 0L;
    }

    public void setNull() {
        this.address = 0L;
    }

    public long address() {
        return this.address;
    }

    public long position() {
        return this.position;
    }

    public <P extends Pointer> P position(long position) {
        this.position = position;
        return (P)this;
    }

    public long limit() {
        return this.limit;
    }

    public <P extends Pointer> P limit(long limit) {
        this.limit = limit;
        return (P)this;
    }

    public long capacity() {
        return this.capacity;
    }

    public <P extends Pointer> P capacity(long capacity) {
        this.limit = capacity;
        this.capacity = capacity;
        return (P)this;
    }

    protected Deallocator deallocator() {
        return this.deallocator;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected <P extends Pointer> P deallocator(Deallocator deallocator) {
        Iterator<PointerScope> it;
        if (this.deallocator != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Predeallocating " + this);
            }
            this.deallocator.deallocate();
            this.deallocator = null;
        }
        if (deallocator == null) return (P)this;
        if (deallocator.equals(null)) return (P)this;
        DeallocatorReference r = deallocator instanceof DeallocatorReference ? (DeallocatorReference)deallocator : new DeallocatorReference(this, deallocator);
        this.deallocator = r;
        if (referenceQueue != null) {
            long lastPhysicalBytes;
            block20: {
                int count;
                block19: {
                    Class<DeallocatorThread> clazz = DeallocatorThread.class;
                    // MONITORENTER : org.bytedeco.javacpp.Pointer$DeallocatorThread.class
                    count = 0;
                    lastPhysicalBytes = 0L;
                    try {
                        if (maxPhysicalBytes <= 0L) break block19;
                        try {
                            lastPhysicalBytes = Pointer.physicalBytesInaccurate(maxPhysicalBytes);
                        }
                        catch (UnsatisfiedLinkError e) {
                            if (logger.isDebugEnabled()) {
                                logger.debug(e.getMessage());
                            }
                            lastPhysicalBytes = Pointer.physicalBytes();
                        }
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break block20;
                    }
                    catch (UnsatisfiedLinkError e) {
                        if (!logger.isDebugEnabled()) break block20;
                        logger.debug(e.getMessage());
                        break block20;
                    }
                }
                while (count++ < maxRetries && (maxBytes > 0L && DeallocatorReference.totalBytes + r.bytes > maxBytes || maxPhysicalBytes > 0L && lastPhysicalBytes > maxPhysicalBytes)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Calling System.gc() and Pointer.trimMemory() in " + this);
                    }
                    System.gc();
                    Thread.sleep(100L);
                    Pointer.trimMemory();
                    lastPhysicalBytes = maxPhysicalBytes > 0L ? Pointer.physicalBytes() : 0L;
                }
            }
            if (maxBytes > 0L && DeallocatorReference.totalBytes + r.bytes > maxBytes) {
                this.deallocate();
                throw new OutOfMemoryError("Failed to allocate memory within limits: totalBytes (" + Pointer.formatBytes(DeallocatorReference.totalBytes) + " + " + Pointer.formatBytes(r.bytes) + ") > maxBytes (" + Pointer.formatBytes(maxBytes) + ")");
            }
            if (maxPhysicalBytes > 0L && lastPhysicalBytes > maxPhysicalBytes) {
                this.deallocate();
                throw new OutOfMemoryError("Physical memory usage is too high: physicalBytes (" + Pointer.formatBytes(lastPhysicalBytes) + ") > maxPhysicalBytes (" + Pointer.formatBytes(maxPhysicalBytes) + ")");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Registering " + this);
            }
            r.add();
            // MONITOREXIT : clazz
        }
        if ((it = PointerScope.getScopeIterator()) == null) return (P)this;
        while (it.hasNext()) {
            try {
                it.next().attach(this);
                return (P)this;
            }
            catch (IllegalArgumentException e) {
            }
        }
        return (P)this;
    }

    @Override
    public void close() {
        this.releaseReference();
    }

    public void deallocate() {
        this.deallocate(true);
    }

    public void deallocate(boolean deallocate) {
        DeallocatorReference r = (DeallocatorReference)this.deallocator;
        if (deallocate && this.deallocator != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Deallocating " + this);
            }
            this.deallocator.deallocate();
            this.deallocator = null;
            this.address = 0L;
        }
        if (r != null) {
            r.deallocator = null;
            r.clear();
            r.remove();
            r.deallocator = this.deallocator;
        }
    }

    public <P extends Pointer> P retainReference() {
        ReferenceCounter r = (ReferenceCounter)((Object)this.deallocator);
        if (r != null) {
            r.retain();
        }
        return (P)this;
    }

    public boolean releaseReference() {
        DeallocatorReference r = (DeallocatorReference)this.deallocator;
        if (r != null && r.release()) {
            this.deallocator = null;
            this.address = 0L;
            r.clear();
            r.remove();
            return true;
        }
        return false;
    }

    public int referenceCount() {
        ReferenceCounter r = (ReferenceCounter)((Object)this.deallocator);
        return r != null ? r.count() : -1;
    }

    public static int offsetof(Class<? extends Pointer> type, String member) {
        return Loader.offsetof(type, member);
    }

    public int offsetof(String member) {
        int offset = -1;
        try {
            Class<?> c = this.getClass();
            if (c != Pointer.class) {
                offset = Loader.offsetof(c, member);
            }
        }
        catch (ClassCastException | NullPointerException e) {
            return offset;
        }
        return offset;
    }

    public static int sizeof(Class<? extends Pointer> type) {
        return Loader.sizeof(type);
    }

    public int sizeof() {
        Class<?> c = this.getClass();
        if (c == Pointer.class || c == BytePointer.class) {
            return 1;
        }
        return this.offsetof("sizeof");
    }

    private native ByteBuffer asDirectBuffer();

    public ByteBuffer asByteBuffer() {
        if (this.isNull()) {
            return null;
        }
        if (this.limit > 0L && this.limit < this.position) {
            throw new IllegalArgumentException("limit < position: (" + this.limit + " < " + this.position + ")");
        }
        int size = this.sizeof();
        Pointer p = new Pointer();
        p.address = this.address;
        return super.asDirectBuffer().order(ByteOrder.nativeOrder());
    }

    public Buffer asBuffer() {
        return this.asByteBuffer();
    }

    public static native Pointer malloc(long var0);

    public static native Pointer calloc(long var0, long var2);

    public static native Pointer realloc(Pointer var0, long var1);

    public static native void free(Pointer var0);

    public static native Pointer memchr(Pointer var0, int var1, long var2);

    public static native int memcmp(Pointer var0, Pointer var1, long var2);

    public static native Pointer memcpy(Pointer var0, Pointer var1, long var2);

    public static native Pointer memmove(Pointer var0, Pointer var1, long var2);

    public static native Pointer memset(Pointer var0, int var1, long var2);

    public <P extends Pointer> P getPointer() {
        return this.getPointer(0L);
    }

    public <P extends Pointer> P getPointer(long i) {
        return (P)this.getPointer(this.getClass(), i);
    }

    public <P extends Pointer> P getPointer(Class<P> type) {
        return this.getPointer(type, 0L);
    }

    public <P extends Pointer> P getPointer(Class<P> type, long i) {
        try {
            Pointer p = (Pointer)type.getDeclaredConstructor(Pointer.class).newInstance(this);
            p.position = this.position != 0L ? Math.max(0L, this.position * (long)this.sizeof() / (long)p.sizeof()) : 0L;
            p.limit = this.limit != 0L ? Math.max(0L, this.limit * (long)this.sizeof() / (long)p.sizeof()) : 0L;
            p.capacity = this.capacity != 0L ? Math.max(0L, this.capacity * (long)this.sizeof() / (long)p.sizeof()) : 0L;
            return p.offsetAddress(i);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public <P extends Pointer> P put(Pointer p) {
        if (p.limit > 0L && p.limit < p.position) {
            throw new IllegalArgumentException("limit < position: (" + p.limit + " < " + p.position + ")");
        }
        int size = this.sizeof();
        int psize = p.sizeof();
        long length = (long)psize * (p.limit <= 0L ? 1L : p.limit - p.position);
        this.position *= (long)size;
        p.position *= (long)psize;
        Pointer.memcpy(this, p, length);
        this.position /= (long)size;
        p.position /= (long)psize;
        return (P)this;
    }

    public <P extends Pointer> P fill(int b) {
        if (this.limit > 0L && this.limit < this.position) {
            throw new IllegalArgumentException("limit < position: (" + this.limit + " < " + this.position + ")");
        }
        int size = this.sizeof();
        long length = (long)size * (this.limit <= 0L ? 1L : this.limit - this.position);
        this.position *= (long)size;
        Pointer.memset(this, b, length);
        this.position /= (long)size;
        return (P)this;
    }

    public <P extends Pointer> P zero() {
        return this.fill(0);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return this.isNull();
        }
        if (obj.getClass() != this.getClass() && obj.getClass() != Pointer.class && this.getClass() != Pointer.class) {
            return false;
        }
        Pointer other = (Pointer)obj;
        return this.address == other.address && this.position == other.position;
    }

    public int hashCode() {
        return (int)this.address;
    }

    public String toString() {
        return this.getClass().getName() + "[address=0x" + Long.toHexString(this.address) + ",position=" + this.position + ",limit=" + this.limit + ",capacity=" + this.capacity + ",deallocator=" + this.deallocator + "]";
    }

    static {
        String s;
        block19: {
            long maxMemory;
            logger = Logger.create(Pointer.class);
            s = System.getProperty("org.bytedeco.javacpp.nopointergc", "false").toLowerCase();
            if ((s = System.getProperty("org.bytedeco.javacpp.noPointerGC", s).toLowerCase()).equals("true") || s.equals("t") || s.equals("")) {
                referenceQueue = null;
                deallocatorThread = null;
            } else {
                referenceQueue = new ReferenceQueue();
                deallocatorThread = new DeallocatorThread();
            }
            long m = maxMemory = Runtime.getRuntime().maxMemory();
            s = System.getProperty("org.bytedeco.javacpp.maxbytes");
            s = System.getProperty("org.bytedeco.javacpp.maxBytes", s);
            if (s != null && s.length() > 0) {
                try {
                    m = Pointer.parseBytes(s, maxMemory);
                }
                catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            m = (maxBytes = m) > 0L ? maxBytes + 3L * maxMemory : 0L;
            s = System.getProperty("org.bytedeco.javacpp.maxphysicalbytes");
            if ((s = System.getProperty("org.bytedeco.javacpp.maxPhysicalBytes", s)) != null && s.length() > 0) {
                try {
                    m = Pointer.parseBytes(s, maxMemory);
                }
                catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            maxPhysicalBytes = m;
            int n = 10;
            s = System.getProperty("org.bytedeco.javacpp.maxretries");
            if ((s = System.getProperty("org.bytedeco.javacpp.maxRetries", s)) != null && s.length() > 0) {
                try {
                    n = Integer.parseInt(s);
                }
                catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            maxRetries = n;
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block19;
                logger.debug("Could not load Pointer: " + t);
            }
        }
        String mx = System.getProperty("org.bytedeco.javacpp.mxbean", "false").toLowerCase();
        if (mx.equals("true") || mx.equals("t") || mx.equals("")) {
            try {
                Class<?> c = Class.forName("org.bytedeco.javacpp.tools.PointerBufferPoolMXBean");
                Method method = c.getDeclaredMethod("register", new Class[0]);
                method.invoke(null, new Object[0]);
            }
            catch (ReflectiveOperationException e) {
                logger.warn("Could not register PointerBufferPoolMXBean: " + e);
            }
        }
        s = System.getProperty("org.bytedeco.javacpp.nativeallocationtracer", "false").toLowerCase();
        boolean enabled = (s = System.getProperty("org.bytedeco.javacpp.nativeAllocationTracer", s).toLowerCase()).equals("true") || s.equals("t");
        Method markPointerMethod = null;
        Method markReferenceMethod = null;
        Method recordAllocationMethod = null;
        Method recordDeallocationMethod = null;
        Method recordCollectionMethod = null;
        if (enabled) {
            try {
                Class<?> tracerClass = Class.forName("org.bytedeco.javacpp.tools.NativeAllocationTracer");
                markPointerMethod = tracerClass.getDeclaredMethod("markPointer", Pointer.class);
                markReferenceMethod = tracerClass.getDeclaredMethod("markReference", PhantomReference.class, Pointer.class);
                recordAllocationMethod = tracerClass.getDeclaredMethod("recordAllocation", PhantomReference.class, Long.TYPE);
                recordDeallocationMethod = tracerClass.getDeclaredMethod("recordDeallocation", PhantomReference.class, Long.TYPE);
                recordCollectionMethod = tracerClass.getDeclaredMethod("recordCollection", PhantomReference.class, Long.TYPE);
                markPointerMethod.setAccessible(true);
                markReferenceMethod.setAccessible(true);
                recordAllocationMethod.setAccessible(true);
                recordDeallocationMethod.setAccessible(true);
                recordCollectionMethod.setAccessible(true);
            }
            catch (ReflectiveOperationException exception) {
                logger.error("Could not enable NativeAllocationTracer: " + exception);
                enabled = false;
            }
        }
        nativeAllocationTracerEnabled = enabled;
        tracerMarkPointerMethod = markPointerMethod;
        tracerMarkReferenceMethod = markReferenceMethod;
        tracerRecordAllocationMethod = recordAllocationMethod;
        tracerRecordDeallocationMethod = recordDeallocationMethod;
        tracerRecordCollectionMethod = recordCollectionMethod;
    }

    static class DeallocatorThread
    extends Thread {
        DeallocatorThread() {
            super("JavaCPP Deallocator");
            this.setPriority(10);
            this.setDaemon(true);
            this.setContextClassLoader(null);
            this.start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    NativeDeallocator deallocator;
                    DeallocatorReference r = (DeallocatorReference)referenceQueue.remove();
                    if (nativeAllocationTracerEnabled && r.deallocator instanceof NativeDeallocator && (deallocator = (NativeDeallocator)r.deallocator).ownerAddress != 0L && deallocator.deallocatorAddress != 0L) {
                        try {
                            tracerRecordCollectionMethod.invoke(null, deallocator, r.bytes);
                        }
                        catch (ReflectiveOperationException exception) {
                            logger.error("Unable to invoke NativeAllocationTracer method via reflection: " + exception);
                        }
                    }
                    r.clear();
                    r.remove();
                }
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    static class DeallocatorReference
    extends PhantomReference<Pointer>
    implements Deallocator,
    ReferenceCounter {
        static volatile DeallocatorReference head = null;
        volatile DeallocatorReference prev = this;
        volatile DeallocatorReference next = this;
        Deallocator deallocator;
        static volatile long totalBytes = 0L;
        static volatile long totalCount = 0L;
        long bytes;
        AtomicInteger count;

        DeallocatorReference(Pointer p, Deallocator deallocator) {
            super(p, referenceQueue);
            this.deallocator = deallocator;
            this.bytes = p.capacity != 0L && referenceQueue != null ? p.capacity * (long)p.sizeof() : 0L;
            this.count = new AtomicInteger(0);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        final void add() {
            Class<DeallocatorReference> clazz = DeallocatorReference.class;
            synchronized (DeallocatorReference.class) {
                if (head == null) {
                    head = this;
                    this.next = null;
                    this.prev = null;
                } else {
                    this.prev = null;
                    this.next = head;
                    this.next.prev = head = this;
                }
                totalBytes += this.bytes;
                ++totalCount;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        final void remove() {
            if (this.prev == this && this.next == this) {
                return;
            }
            Class<DeallocatorReference> clazz = DeallocatorReference.class;
            synchronized (DeallocatorReference.class) {
                if (this.prev == null) {
                    head = this.next;
                } else {
                    this.prev.next = this.next;
                }
                if (this.next != null) {
                    this.next.prev = this.prev;
                }
                this.prev = this.next = this;
                totalBytes -= this.bytes;
                --totalCount;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        }

        @Override
        public void clear() {
            super.clear();
            if (this.deallocator != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Collecting " + this);
                }
                this.deallocate();
            }
        }

        @Override
        public void deallocate() {
            if (this.deallocator != null) {
                this.deallocator.deallocate();
                this.deallocator = null;
            }
        }

        @Override
        public void retain() {
            if (this.deallocator != null) {
                this.count.incrementAndGet();
            }
        }

        @Override
        public boolean release() {
            if (this.deallocator != null && this.count.decrementAndGet() <= 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Releasing " + this);
                }
                this.deallocate();
                return true;
            }
            return false;
        }

        @Override
        public int count() {
            return this.deallocator != null ? this.count.get() : -1;
        }

        public String toString() {
            return this.getClass().getName() + "[deallocator=" + this.deallocator + ",count=" + this.count + "]";
        }
    }

    static class ProxyDeallocator
    extends DeallocatorReference {
        Buffer buffer;
        Pointer pointer;

        public ProxyDeallocator(Pointer p, Buffer b) {
            super(p, (Deallocator)null);
            this.deallocator = this;
            this.buffer = b;
        }

        public ProxyDeallocator(Pointer p, Pointer p2) {
            super(p, (Deallocator)null);
            this.deallocator = this;
            this.pointer = p2;
        }

        @Override
        public void deallocate() {
            this.buffer = null;
            if (this.pointer != null) {
                this.pointer.deallocate();
            }
        }

        @Override
        public void retain() {
            if (this.pointer != null) {
                this.pointer.retainReference();
            }
        }

        @Override
        public boolean release() {
            return this.pointer != null ? this.pointer.releaseReference() : false;
        }

        @Override
        public int count() {
            return this.pointer != null ? this.pointer.referenceCount() : -1;
        }

        @Override
        public String toString() {
            return this.getClass().getName() + "[buffer=" + this.buffer + ",pointer=" + this.pointer + "]";
        }
    }

    protected static class NativeDeallocator
    extends DeallocatorReference {
        private long ownerAddress;
        private long deallocatorAddress;

        NativeDeallocator(Pointer p, long ownerAddress, long deallocatorAddress) {
            super(p, (Deallocator)null);
            this.deallocator = this;
            this.ownerAddress = ownerAddress;
            this.deallocatorAddress = deallocatorAddress;
            if (nativeAllocationTracerEnabled) {
                try {
                    tracerMarkReferenceMethod.invoke(null, this, p);
                    tracerRecordAllocationMethod.invoke(null, this, this.bytes);
                }
                catch (ReflectiveOperationException exception) {
                    logger.error("Unable to invoke NativeAllocationTracer method via reflection: " + exception);
                }
            }
        }

        public long ownerAddress() {
            return this.ownerAddress;
        }

        public long deallocatorAddress() {
            return this.deallocatorAddress;
        }

        @Override
        public void deallocate() {
            if (this.ownerAddress != 0L && this.deallocatorAddress != 0L) {
                this.deallocate(this.ownerAddress, this.deallocatorAddress);
                this.deallocatorAddress = 0L;
                this.ownerAddress = 0L;
                if (nativeAllocationTracerEnabled) {
                    try {
                        tracerRecordDeallocationMethod.invoke(null, this, this.bytes);
                    }
                    catch (ReflectiveOperationException exception) {
                        logger.error("Unable to invoke NativeAllocationTracer method via reflection: " + exception);
                    }
                }
            }
        }

        private native void deallocate(long var1, long var3);

        @Override
        public String toString() {
            return this.getClass().getName() + "[ownerAddress=0x" + Long.toHexString(this.ownerAddress) + ",deallocatorAddress=0x" + Long.toHexString(this.deallocatorAddress) + "]";
        }
    }

    protected static class CustomDeallocator
    extends DeallocatorReference {
        Pointer pointer = null;
        Method method = null;

        public CustomDeallocator(Pointer p) {
            super(p, (Deallocator)null);
            this.deallocator = this;
            Class<?> cls = p.getClass();
            for (Method m : cls.getDeclaredMethods()) {
                Class<?>[] parameters = m.getParameterTypes();
                if (!Modifier.isStatic(m.getModifiers()) || !m.getReturnType().equals(Void.TYPE) || !m.getName().equals("deallocate") || parameters.length != 1 || !Pointer.class.isAssignableFrom(parameters[0])) continue;
                m.setAccessible(true);
                this.method = m;
                break;
            }
            if (this.method == null) {
                throw new RuntimeException(new NoSuchMethodException("static void " + cls.getCanonicalName() + ".deallocate(" + Pointer.class.getCanonicalName() + ")"));
            }
            try {
                Constructor<?> c = cls.getConstructor(Pointer.class);
                c.setAccessible(true);
                this.pointer = (Pointer)c.newInstance(p);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public void deallocate() {
            try {
                this.method.invoke(null, this.pointer);
                this.pointer.setNull();
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public String toString() {
            return this.getClass().getName() + "[pointer=" + this.pointer + ",method=" + this.method + "]";
        }
    }

    protected static interface ReferenceCounter {
        public void retain();

        public boolean release();

        public int count();
    }

    protected static interface Deallocator {
        public void deallocate();
    }
}

