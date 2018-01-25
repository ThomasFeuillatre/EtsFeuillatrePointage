package com.thomas.etsfeuillatrepointage.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thomas.etsfeuillatrepointage.R;
import com.thomas.etsfeuillatrepointage.api.APIService;
import com.thomas.etsfeuillatrepointage.api.APIUrl;
import com.thomas.etsfeuillatrepointage.model.Result;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //TODO add alarm si pas rempli le soir
    //TODO add possibility for more palce -> array()
    TextView dateTitle;
    AppCompatButton validateBtn, addPlace;
    int nbLine = 1;
    AppCompatEditText editAmStart, editAmEnd, editPmStart, editPmEnd, editPlace, editObserv;
    SharedPreferences sharedPreferences;
    boolean statut;
    private DatePickerDialog datePickerDialog;
    int idWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);

        dateTitle = (TextView) findViewById(R.id.dateTitle);
        validateBtn = (AppCompatButton) findViewById(R.id.validateBtn);
        validateBtn.setOnClickListener(this);
        addPlace = (AppCompatButton) findViewById(R.id.addPlace);
        addPlace.setOnClickListener(this);

        editAmStart = (AppCompatEditText) findViewById(R.id.editAmStart);
        editAmEnd = (AppCompatEditText) findViewById(R.id.editAmEnd);
        editPmStart = (AppCompatEditText) findViewById(R.id.editPmStart);
        editPmEnd = (AppCompatEditText) findViewById(R.id.editPmEnd);
        editPlace = (AppCompatEditText) findViewById(R.id.editPlace);
        editObserv = (AppCompatEditText) findViewById(R.id.editObserv);
         idWorker = sharedPreferences.getInt("idWorker",0);

        DateFormat df = new SimpleDateFormat("dd M yyyy", Locale.FRANCE);
        String nowTime = df.format(Calendar.getInstance().getTime());
        dateTitle.setText(nowTime);

        checkStatut();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),SignInActivity.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addPlace:
                if(nbLine==5)
                {
                    Toast.makeText(this, "Vous ne pouvez pas rajouter plus de chantiers", Toast.LENGTH_SHORT).show();
                    break;
                }
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearPlace);

                AppCompatEditText myEditText = new AppCompatEditText(this); // Pass it an Activity or Context
                myEditText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
                myEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                myEditText.setHint("Chantier"+nbLine);
                myEditText.setId(nbLine);
                linearLayout.addView(myEditText);
                nbLine++;
                break;
            case R.id.validateBtn:
                try {
                    saveDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageCalendar:
                openDatePicker();
                break;
        }
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,month+1,dayOfMonth);
                java.sql.Date date = new java.sql.Date(c.getTimeInMillis());
                dateTitle.setText(dayOfMonth+" "+(month+1)+" "+year);
                getDataClockPoint(date, idWorker);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void getDataClockPoint(Date date, int idWorker) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.getDataClockPoint(date, idWorker);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDate() throws ParseException {
        java.sql.Date dayTyme = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        String amStart, amEnd, pmStart, pmEnd, observation;
        String place;

        amStart = editAmStart.getText().toString();
        amEnd = editAmEnd.getText().toString();
        pmStart = editPmStart.getText().toString();
        pmEnd = editPmEnd.getText().toString();
        place = editPlace.getText().toString();

        observation = editObserv.getText().toString();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Result> call = service.saveTimeClock(idWorker, dayTyme, amStart, amEnd, pmStart, pmEnd, place ,1, observation);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(!response.body().getError())
                {
                    Toast.makeText(MainActivity.this, "Pointage enregistré", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkStatut() {
        java.sql.Date dayTyme = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Result> call = service.checkStatut(idWorker, dayTyme);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                statut = response.body().getStatut();
                validateBtn.setEnabled(!statut);//true = always validate / false = not validate
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
