package com.example.ladyrei.gameloggery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoggeryLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggery_login);

        final Button loginButton = (Button) findViewById(R.id.Login_Button);
        //final EditText emailTextField = (EditText) findViewById(R.id.LoginName_TextField);
        //final EditText passwordTextField = (EditText) findViewById(R.id.LoginPassword_TextField);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //should check some database for email & password instead, but I didn't set one up.
                //String email = String.valueOf(emailTextField.getText());
                //String pw = String.valueOf(passwordTextField.getText());

                //if .... etc

                startActivity(new Intent(LoggeryLoginActivity.this, GameListViewActivity.class));
            }
        });
    }
}
