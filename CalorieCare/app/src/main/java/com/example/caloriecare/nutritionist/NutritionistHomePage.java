package com.example.caloriecare.nutritionist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.R;
import com.example.caloriecare.newAccount.LogIn;

public class NutritionistHomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_homepage);

    }

    public void patientList(View view){
        Intent goToPatientList = new Intent(this, NutritionistClientList.class);
        startActivity(goToPatientList);
    }

    public void subscription(View view){
        Intent goToSubscriptionHandler = new Intent(this, SubscriptionHandler.class);
        startActivity(goToSubscriptionHandler);
    }
    public void logOutButton(View view){
        Intent goToLogIn = new Intent(this, LogIn.class);
        startActivity(goToLogIn);
    }
}
