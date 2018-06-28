package com.github.dentou.chat;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.github.dentou.interfaces.OpenConnectionResult;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;


public class IRCClient implements OpenConnectionResult {
    private IRCSocket ircSocket;

    private SocketListener socketListener;
    private SocketSpeaker socketSpeaker;

    private volatile boolean isExited = false;

    public IRCClient(String serverAddress, int serverPort) throws IOException {

        OpenConnection openConnection = new OpenConnection();
        openConnection.openConnectionResult = this;
        openConnection.execute(new ServerInfo(serverAddress, serverPort));
    }

    public void start() throws IOException{
        this.socketListener = new SocketListener(ircSocket, this);
        this.socketSpeaker = new SocketSpeaker(ircSocket, this);

        Thread listenerThread = new Thread(socketListener);
        Thread speakerThread = new Thread(socketSpeaker);

        listenerThread.start();
        speakerThread.start();
    }

    public void exit() {
        isExited = true;
        socketSpeaker.close();
        socketListener.close();
        try {
            ircSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(SocketChannel socketChannel) {
        System.out.print("Connection established");
        try {
            this.ircSocket = new IRCSocket(socketChannel, false);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class ServerInfo {
        public String address;
        public int port;

        ServerInfo(String address, int port) {
            this.address = address;
            this.port = port;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class OpenConnection extends AsyncTask<ServerInfo, Void, SocketChannel> {

        OpenConnectionResult openConnectionResult = null;

        @Override
        protected SocketChannel doInBackground(ServerInfo... serverInfos) {
            try {
                return SocketChannel.open(new InetSocketAddress(serverInfos[0].address, serverInfos[0].port));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SocketChannel socketChannel) {
            openConnectionResult.onSuccess(socketChannel);
        }
    }
}
