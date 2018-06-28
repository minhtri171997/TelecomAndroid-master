package com.github.dentou.interfaces;

import java.nio.channels.SocketChannel;

public interface OpenConnectionResult {
    void onSuccess(SocketChannel socketChannel);
}
