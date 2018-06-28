package com.github.dentou.chat;

import com.github.dentou.IOChannel;

import java.io.IOException;
import java.util.Scanner;

public class SocketSpeaker implements Runnable{
    private IRCSocket ircSocket;
    private IRCClient ircClient;
    private volatile boolean isClosed = false;

    public SocketSpeaker(IRCSocket ircSocket, IRCClient ircClient) {
        this.ircSocket = ircSocket;
        this.ircClient = ircClient;
    }

    @Override
    public void run() {
        while (!isClosed) {
            while (IOChannel.getInstance().getInputQueueSize() != 0) {
                String message = IOChannel.getInstance().getInput();
                if (message.equals("none")) {
                    continue;
                }

                if (message.equals("exit") || isClosed) {
                    ircClient.exit();
                    return;
                }

                try {
                    sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            if (isClosed) {
                ircClient.exit();
                return;
            }
        }
    }

    private void sendMessage(String message) throws IOException{
        synchronized (ircSocket) {
            System.out.println("Speaking...");
            ircSocket.enqueue(message);
            ircSocket.sendMessages();
        }
    }

    public void close() {
        isClosed = true;
    }
    public boolean isClosed() {
        return isClosed;
    }
}
