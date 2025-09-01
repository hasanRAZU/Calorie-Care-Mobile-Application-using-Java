package com.example.caloriecare.client;

import static com.example.caloriecare.newAccount.LogIn.loginUserEmail;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.R;
import com.example.caloriecare.database.DailyPlanDB;

public class GetSuggestionFromNutritionist extends AppCompatActivity {
    DailyPlanDB dailyPlanDB;
    String userEmail = loginUserEmail;
    Button goBack;
    TextView textViewOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_getsuggestionfromnutritionist);
        dailyPlanDB = new DailyPlanDB(this);
        textViewOutput = findViewById(R.id.textView_output_getSuggestion);


        goBack = findViewById(R.id.button_goBackFrom_GenerateRecipe);
        Intent intent_homepage = new Intent(GetSuggestionFromNutritionist.this, ClientHomePage.class);

        goBack.setOnClickListener(v -> {
            startActivity(intent_homepage);
        });



        Cursor cursor = dailyPlanDB.fetchDataByEmail(userEmail);
        if (cursor != null && cursor.moveToFirst()) {
            String output = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            output += cursor.getString(cursor.getColumnIndexOrThrow("time"));
            cursor.close();
            textViewOutput.setText(output);
        }
    }
}
