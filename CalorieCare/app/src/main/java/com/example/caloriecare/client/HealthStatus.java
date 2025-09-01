package com.example.caloriecare.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.R;

public class HealthStatus extends AppCompatActivity {
    Button goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_healthstatus);


        goBack = findViewById(R.id.button_goBackFrom_HealthStatus);
        Intent intent_homepage = new Intent(HealthStatus.this, ClientHomePage.class);

        goBack.setOnClickListener(v -> {
            startActivity(intent_homepage);
        });
    }
}
