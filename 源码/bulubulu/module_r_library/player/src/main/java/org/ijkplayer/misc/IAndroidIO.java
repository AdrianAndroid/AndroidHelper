package org.ijkplayer.misc;

import java.io.IOException;

@SuppressWarnings("RedundantThrows")
public interface IAndroidIO {
    int  open(String url) throws IOException;
    int  read(byte[] buffer, int size) throws IOException;
    long seek(long offset, int whence) throws IOException;
    int  close() throws IOException;
}
