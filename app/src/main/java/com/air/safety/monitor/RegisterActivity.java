package com.air.safety.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText pass;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
     //   name = (EditText) findViewById(R.id.et_name);
      //  email = (EditText) findViewById(R.id.et_email);
      //  pass = (EditText) findViewById(R.id.et_password);
        signup = (Button) findViewById(R.id.btn_signup);

        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email.getText().toString().equals("") &&
                                pass.getText().toString().equals("") &&
                                name.getText().toString().equals("")) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
