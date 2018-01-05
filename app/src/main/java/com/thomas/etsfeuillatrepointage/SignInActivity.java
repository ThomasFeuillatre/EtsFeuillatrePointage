package com.thomas.etsfeuillatrepointage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editLogin, editPassword;
    AppCompatButton loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);
        loginBtn = (AppCompatButton)    findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this);
    }
     private void signIn()
     {

         String login = editLogin.getText().toString().trim();
         String password = editPassword.getText().toString().trim();

     }

    @Override
    public void onClick(View v) {
        if(v == loginBtn)
        {
            signIn();
        }
    }
}
