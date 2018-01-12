package com.thomas.etsfeuillatrepointage.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thomas.etsfeuillatrepointage.R;
import com.thomas.etsfeuillatrepointage.api.APIService;
import com.thomas.etsfeuillatrepointage.api.APIUrl;
import com.thomas.etsfeuillatrepointage.model.Preferences;
import com.thomas.etsfeuillatrepointage.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editName, editPassword;
    AppCompatButton loginBtn;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences",MODE_PRIVATE);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        loginBtn = (AppCompatButton)    findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this);


    }
     private void signIn()
     {

         String name = editName.getText().toString().trim();
         String password = editPassword.getText().toString().trim();

         final SharedPreferences.Editor editor = sharedPreferences.edit();

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         APIService service = retrofit.create(APIService.class);

         Call<Result> call = service.loginUser(name, password);
         call.enqueue(new Callback<Result>() {
             @Override
             public void onResponse(Call<Result> call, Response<Result> response) {
                 if(!response.body().getError())
                 {
                     editor.putInt("idWorker",response.body().getIdWorker());
                     editor.apply();
                     finish();
                     startActivity(new Intent(SignInActivity.this,MainActivity.class));
                 }
                 else {
                     Toast.makeText(SignInActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                 }
             }
             @Override
             public void onFailure(Call<Result> call, Throwable t) {
                 Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
         }
         });
     }
    @Override
    public void onClick(View v) {
        if(v == loginBtn)
        {
            signIn();
        }
    }
}
