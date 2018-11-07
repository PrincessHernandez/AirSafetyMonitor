package com.air.safety.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email = (EditText) findViewById(R.id.et_email);
        pass = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email.getText().toString().equals("humber@gmail.com") &&
                                pass.getText().toString().equals("password")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }
                });

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                    }
                });
    }
}


