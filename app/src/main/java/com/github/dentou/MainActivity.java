package com.github.dentou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.github.dentou.interfaces.IOChannelListener;


public class MainActivity extends AppCompatActivity implements IOChannelListener {
    EditText serveradd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        serveradd = (EditText)findViewById(R.id.editText);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String set_hostname = serveradd.getText().toString();
                if(set_hostname == "localhost")
                {
                    //check for server first
                    Intent myIntent = new Intent(MainActivity.this, infoActivity.class);
                    startActivity(myIntent);
                }else if (set_hostname.equals("18.216.182.124")){
                    // connect to this server
                    int port = 6667;
                    establishConnection(set_hostname, port);
                    Intent myIntent = new Intent(MainActivity.this, infoActivity.class);
                    startActivity(myIntent);
                }
            }
        });
        // TODO: hostname & port setup -> After setup -> establish connection
        //String hostname = "192.168.1.5";
        String hostname = serveradd.getText().toString();
       // int port = 6667;

       // establishConnection(hostname, port);

        // TODO: must start working after establishConnection()
        start();
    }

    private void start() {
        // TODO: sendMessage template
        sendMessage("NICK tlvu");
        sendMessage("USER tlvu * * :TranLeVu");
    }


    private void establishConnection(String hostname, int port) {
        if (!IRCClientManager.getInstance().init(hostname, port)) {
            // TODO: Can't establish connection to server
        } else {
            // TODO: Established connection to server successfully
        }
    }


    @Override
    protected void onDestroy() {
        IRCClientManager.getInstance().exit();
        super.onDestroy();
    }


    // TODO: Copy 5 methods below to any new activities
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
    protected void onResume() {
        IOChannel.getInstance().registerIoChannelListener(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        IOChannel.getInstance().unRegisterIoChannelListener();
        super.onPause();
    }

    private void onReceiveMessage(String outputString) {
        // TODO: Receive Messages template
        Toast.makeText(MainActivity.this, outputString, Toast.LENGTH_SHORT).show();
    }

    private void sendMessage(String inputString) {
        IOChannel.getInstance().input(inputString);
    }
}
