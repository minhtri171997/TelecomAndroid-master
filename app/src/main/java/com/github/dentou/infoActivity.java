package com.github.dentou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class infoActivity extends AppCompatActivity {
    EditText et_nickname;
    EditText et_username;
    EditText et_fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        addControl();
        String nickname = et_nickname.getText().toString();
        String username = et_username.getText().toString();
        String fullname = et_fullname.getText().toString();
        Button button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent myIntent = new Intent(infoActivity.this, chatActivity.class);
                    startActivity(myIntent);
            }
        });
        Button button2 = (Button) findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(infoActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void addControl() {
        et_nickname = (EditText)findViewById(R.id.editText_nickname);
        et_username = (EditText)findViewById(R.id.editText_username);
        et_fullname = (EditText)findViewById(R.id.editText_fullname);
    }
}
