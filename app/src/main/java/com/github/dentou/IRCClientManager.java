package com.github.dentou;

import com.github.dentou.chat.IRCClient;

import java.io.IOException;

public class IRCClientManager {

    private boolean status = false;
    private IRCClient ircClient;


    /* Singleton */
    private IRCClientManager() {}

    private static class IRCClientManageHolder {
        private static final IRCClientManager INSTANCE = new IRCClientManager();
    }

    public static IRCClientManager getInstance() {
        return IRCClientManager.IRCClientManageHolder.INSTANCE;
    }
    /*************/

    public boolean getStatus() {
        return status;
    }

    public boolean init(String hostname, int port) {
        try {
            ircClient = new IRCClient(hostname, port);
            status = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public void exit() {
        ircClient.exit();
    }
}
