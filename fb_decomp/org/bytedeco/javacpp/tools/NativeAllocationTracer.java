/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.lang.ref.PhantomReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.tools.Logger;

public class NativeAllocationTracer {
    private static final Logger logger = Logger.create(NativeAllocationTracer.class);
    private static final HashMap<Location, Site> sites = new HashMap();
    private static final WeakHashMap<Pointer, Location> pointerLocations = new WeakHashMap();
    private static final WeakHashMap<PhantomReference<Pointer>, Location> pointerReferenceLocations = new WeakHashMap();

    public static Collection<Site> getSites() {
        return sites.values();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static synchronized void markPointer(Pointer pointer) {
        Location location = NativeAllocationTracer.captureCreationLocation(pointer.getClass());
        if (location == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not capture creation location for " + pointer);
            }
            return;
        }
        Class<NativeAllocationTracer> clazz = NativeAllocationTracer.class;
        synchronized (NativeAllocationTracer.class) {
            if (!sites.containsKey(location)) {
                Site site = new Site(location, pointer.getClass());
                sites.put(location, site);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Mark location for " + pointer + ": " + location);
            }
            pointerLocations.put(pointer, location);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    private static synchronized void markReference(PhantomReference<Pointer> pointerReference, Pointer pointer) {
        Location location = pointerLocations.get(pointer);
        if (location == null && (location = NativeAllocationTracer.captureCreationLocation(pointer.getClass())) == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not get creation location for " + pointerReference);
            }
            return;
        }
        if (!sites.containsKey(location)) {
            Site site = new Site(location, pointer.getClass());
            sites.put(location, site);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Mark location for " + pointerReference + ": " + location);
        }
        pointerReferenceLocations.put(pointerReference, location);
    }

    private static synchronized void recordAllocation(PhantomReference<Pointer> pointerReference, long size) {
        Location location = pointerReferenceLocations.get(pointerReference);
        Site site = sites.get(location);
        if (site == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find allocation site for " + pointerReference + ": " + location);
            }
            return;
        }
        site.totalCounts.incrementAndGet();
        site.totalBytes.addAndGet(size);
        site.liveCounts.incrementAndGet();
        site.liveBytes.addAndGet(size);
    }

    private static synchronized void recordDeallocation(PhantomReference<Pointer> pointerReference, long size) {
        Location location = pointerReferenceLocations.get(pointerReference);
        Site site = sites.get(location);
        if (site == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find allocation site for " + pointerReference + ": " + location);
            }
            return;
        }
        site.liveCounts.decrementAndGet();
        site.liveBytes.addAndGet(-size);
    }

    private static synchronized void recordCollection(PhantomReference<Pointer> pointerReference, long size) {
        Location location = pointerReferenceLocations.get(pointerReference);
        Site site = sites.get(location);
        if (site == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find allocation site for " + pointerReference + ": " + location);
            }
            return;
        }
        site.collectedCounts.incrementAndGet();
        site.collectedBytes.addAndGet(size);
    }

    private static Location captureCreationLocation(Class<?> createdClass) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean found = false;
        for (int i = 2; i < stackTrace.length; ++i) {
            StackTraceElement element = stackTrace[i];
            String elementMethod = element.getMethodName();
            Class<?> elementClass = null;
            try {
                elementClass = Class.forName(element.getClassName());
            }
            catch (ClassNotFoundException ignored) {
                continue;
            }
            if (elementMethod.equals("<init>") && elementClass == createdClass) {
                found = true;
                continue;
            }
            if (elementMethod.equals("init")) {
                found = true;
                continue;
            }
            if (!found || element.isNativeMethod()) continue;
            return new Location(element);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("###### StackTraceElement for " + createdClass + " ######");
            for (StackTraceElement element : stackTrace) {
                logger.debug(element.toString());
            }
        }
        return null;
    }

    public static final class Site {
        private final Location location;
        private final Class<? extends Pointer> pointerType;
        private final AtomicLong totalCounts;
        private final AtomicLong totalBytes;
        private final AtomicLong liveCounts;
        private final AtomicLong liveBytes;
        private final AtomicLong collectedCounts;
        private final AtomicLong collectedBytes;

        Site(Location location, Class<? extends Pointer> pointerType) {
            this.location = location;
            this.pointerType = pointerType;
            this.totalCounts = new AtomicLong(0L);
            this.totalBytes = new AtomicLong(0L);
            this.liveCounts = new AtomicLong(0L);
            this.liveBytes = new AtomicLong(0L);
            this.collectedCounts = new AtomicLong(0L);
            this.collectedBytes = new AtomicLong(0L);
        }

        public Location getLocation() {
            return this.location;
        }

        public Class<? extends Pointer> getPointerType() {
            return this.pointerType;
        }

        public long getTotalCounts() {
            return this.totalCounts.get();
        }

        public long getTotalBytes() {
            return this.totalBytes.get();
        }

        public long getLiveCounts() {
            return this.liveCounts.get();
        }

        public long getLiveBytes() {
            return this.liveBytes.get();
        }

        public long getCollectedCounts() {
            return this.collectedCounts.get();
        }

        public long getCollectedBytes() {
            return this.collectedBytes.get();
        }

        public boolean equals(Object object) {
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            Site site = (Site)object;
            return this.location.equals(site.location);
        }

        public int hashCode() {
            return this.location.hashCode();
        }

        public String toString() {
            return "Site[location=" + this.location.toString() + ",pointerType=" + this.pointerType.getName() + ",totalCounts=" + this.totalCounts.get() + ",totalBytes=" + this.totalBytes.get() + ",liveCounts=" + this.liveCounts.get() + ",liveBytes=" + this.liveBytes.get() + ",collectedCounts=" + this.collectedCounts.get() + ",collectedBytes=" + this.collectedBytes.get() + "]";
        }
    }

    public static class Location {
        private final String className;
        private final String methodName;
        private final String fileName;
        private final int lineNumber;

        Location(StackTraceElement element) {
            this.className = element.getClassName();
            this.methodName = element.getMethodName();
            this.fileName = element.getFileName();
            this.lineNumber = element.getLineNumber();
        }

        public String getClassName() {
            return this.className;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public String getFileName() {
            return this.fileName;
        }

        public int getLineNumber() {
            return this.lineNumber;
        }

        public boolean isNativeMethod() {
            return this.lineNumber == -2;
        }

        public String toString() {
            String physicalLocation = this.fileName != null && this.lineNumber >= 0 ? this.fileName + ":" + this.lineNumber : (this.isNativeMethod() ? "Native Method" : "Unknown Source");
            return this.className + "." + this.methodName + "(" + physicalLocation + ")";
        }

        public boolean equals(Object object) {
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            Location location = (Location)object;
            return this.lineNumber == location.lineNumber && Objects.equals(this.className, location.className) && Objects.equals(this.methodName, location.methodName) && Objects.equals(this.fileName, location.fileName);
        }

        public int hashCode() {
            return Objects.hash(this.className, this.methodName, this.fileName, this.lineNumber);
        }
    }
}

