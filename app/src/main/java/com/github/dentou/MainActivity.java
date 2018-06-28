package com.github.dentou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.dentou.chat.IRCClient;
import com.github.dentou.interfaces.IOChannelListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IOChannelListener {

    private IRCClient ircClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: hostname & port setup -> After setup -> establish connection
        String hostname = "localhost";
        int port = 6667;

        establishConnection(hostname, port);
    }


    private void establishConnection(String hostname, int port) {
        try {
            IOChannel.getInstance().setIoChannelListener(this);
            ircClient = new IRCClient(hostname, port);

        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void receive(final String outputString) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onReceiveMessage(outputString);
            }
        });
    }


    @Override
    protected void onDestroy() {
        ircClient.exit();
        super.onDestroy();
    }



    // TODO: Receive Messages template
    private void onReceiveMessage(String outputString) {
        Toast.makeText(MainActivity.this, outputString, Toast.LENGTH_SHORT).show();
    }


    // TODO: Send Messages template
    private void sendMessage(String inputString) {
        IOChannel.getInstance().input("USER tlvu * * :TranLeVu");
    }
}
