package com.github.dentou.interfaces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.nio.channels.SocketChannel;

public interface OpenConnectionResult {
    void onSuccess(SocketChannel socketChannel);
}
