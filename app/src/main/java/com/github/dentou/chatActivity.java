package com.github.dentou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.github.dentou.interfaces.IOChannelListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chatActivity extends AppCompatActivity implements IOChannelListener{
    EditText et_chatstring,et_channel;
    String channel_name;
    TextView display_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //connect to channel

        addControl();
        String channel = et_channel.getText().toString();
        Button connect = (Button) findViewById(R.id.button5);
        connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                channel_name = et_channel.getText().toString();
                sendMessage("JOIN " + channel_name);
                //connect to channel name #abcxyz
            }
        });
        //back button
        Button back = (Button) findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(chatActivity.this, infoActivity.class);
                startActivity(myIntent);
            }
        });
        // chat
        Button send = (Button) findViewById(R.id.button8);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if (et_chatstring.getText().toString() == null) {
                   Toast.makeText(getApplicationContext(), "Enter something here to send !", Toast.LENGTH_LONG).show();
                   // do something here later
                }else {
                   sendMessage("PRIVMSG " + channel_name + " :" + et_chatstring.getText().toString());
               }
            }
        });
    }

    private void addControl() {
        et_channel = findViewById(R.id.editText3);
        et_chatstring = findViewById(R.id.editText4);
        display_view = findViewById(R.id.textViewshowchat);
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
        String currentText = display_view.getText().toString();
        display_view.setText(currentText + '\n' + outputString);

        // classify(outputString);
    }

    private void classify(String outputString) {

        // ignore
    }

    public static List<String> parseMessage(String message) {
        List<String> parts = new ArrayList<>();
        Matcher m = Pattern.compile("(?<=:).+|[^ :]+").matcher(message.substring(1));
        while (m.find()) {
            parts.add(m.group());
        }
        return parts;
    }

    private void sendMessage(String inputString) {
        IOChannel.getInstance().input(inputString);
    }
}