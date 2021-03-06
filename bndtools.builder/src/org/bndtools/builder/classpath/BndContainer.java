package org.bndtools.builder.classpath;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import org.bndtools.api.BndtoolsConstants;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;

public class BndContainer implements IClasspathContainer, Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DESCRIPTION = "Bnd Bundle Path";
    private final IClasspathEntry[] entries;
    private final AtomicLong lastModified;

    BndContainer(IClasspathEntry[] entries, long lastModified) {
        this.entries = entries;
        this.lastModified = new AtomicLong(lastModified);
    }

    @Override
    public IClasspathEntry[] getClasspathEntries() {
        return entries;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getKind() {
        return IClasspathContainer.K_APPLICATION;
    }

    @Override
    public IPath getPath() {
        return BndtoolsConstants.BND_CLASSPATH_ID;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    long lastModified() {
        return lastModified.get();
    }

    boolean updateLastModified(long time) {
        for (long current = lastModified.get(); time > current; current = lastModified.get()) {
            if (lastModified.compareAndSet(current, time)) {
                return true;
            }
        }
        return false;
    }
}
