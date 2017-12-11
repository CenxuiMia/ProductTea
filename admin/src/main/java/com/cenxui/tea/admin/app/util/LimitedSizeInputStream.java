package com.cenxui.tea.admin.app.util;

import java.io.IOException;
import java.io.InputStream;

public class LimitedSizeInputStream extends InputStream {

    private final InputStream original;
    private final long maxSize;
    private long total;

    public LimitedSizeInputStream(InputStream original, long maxSize) {
        this.original = original;
        this.maxSize = maxSize;
    }

    @Override
    public int read() throws IOException {
        int i = original.read();
        if (i>=0) incrementCounter(1);
        return i;
    }

    @Override
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        int i = original.read(b, off, len);
        if (i>=0) incrementCounter(i);
        return i;
    }

    private void incrementCounter(int size) throws IOException {
        total += size;
        if (total>maxSize) throw new IOException("InputStream exceeded maximum size in bytes.");
    }

}