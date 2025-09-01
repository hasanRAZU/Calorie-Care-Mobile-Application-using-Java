package com.example.caloriecare.client;

import static com.example.caloriecare.newAccount.LogIn.loginUserPremiumMemberStatus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.database.SubscriptionDB;
import com.example.caloriecare.newAccount.LogIn;
import com.example.caloriecare.R;

public class ClientHomePage extends AppCompatActivity {
    SubscriptionDB subscriptionDB;


    TextView uploadPrescription, generateRecipe, healthStatus, textViewSubscriptionStatus;
    Button buttonGoBack;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_homepage);

        uploadPrescription = findViewById(R.id.textView_uploadPrescription);
        generateRecipe = findViewById(R.id.textView_generateRecipe);
        healthStatus = findViewById(R.id.textView_healthStatus);
        buttonGoBack = findViewById(R.id.button_goBack_homePage);

        textViewSubscriptionStatus = findViewById(R.id.textView_subscriptionStatus_ClientHomePage);

        uploadPrescription.setOnClickListener(v -> {
            Intent intent_uploadPrescription = new Intent(ClientHomePage.this, UploadPrescription.class);
            startActivity(intent_uploadPrescription);
        });

        generateRecipe.setOnClickListener(v -> {
            Intent intent_generateRecipe = new Intent(ClientHomePage.this, GetSuggestionFromNutritionist.class);
            startActivity(intent_generateRecipe);
        });

        healthStatus.setOnClickListener(v -> {
            Intent intent_healthStatus = new Intent(ClientHomePage.this, HealthStatus.class);
            startActivity(intent_healthStatus);
        });

        buttonGoBack.setOnClickListener(v ->{
            Intent goToMainActivity = new Intent(ClientHomePage.this, LogIn.class);
            startActivity(goToMainActivity);
        } );


        textViewSubscriptionStatus.setText("Premium User: " + loginUserPremiumMemberStatus);
    }

    public void talkToNutritionist(View view){
        if(loginUserPremiumMemberStatus.equals("No")) {
            Toast.makeText(this, "Only Premium User Can Use This Feature", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent goToClientLiveConsultation = new Intent(this, ClientLiveConsultation.class);
        startActivity(goToClientLiveConsultation);

    }
}

