package com.example.caloriecare.nutritionist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.R;
import com.example.caloriecare.database.SubscriptionDB;

public class SubscriptionHandler extends AppCompatActivity {
    SubscriptionDB subscriptionDB;
    EditText editTextSubscription;
    TextView textViewAllSubscriber;
    String userEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_subscriptionhandler);

        editTextSubscription = findViewById(R.id.editText_subscription_SubscriptionHandler);
        textViewAllSubscriber = findViewById(R.id.textView_SubscriptionHandler);

    }

    public void buttonGoBack(View view){
        Intent goBackToNutritionistHomePage = new Intent(this, NutritionistHomePage.class);
        startActivity(goBackToNutritionistHomePage);
    }

    public void buttonAddSubscription(View view){
        subscriptionDB = new SubscriptionDB(this);
        userEmail = editTextSubscription.getText().toString();

        // Store in database
        int status = subscriptionDB.updateSubscriptionStatus(userEmail, "Yes");

        if(status != 0){
            Toast.makeText(this, "Subscription Successful", Toast.LENGTH_SHORT).show();
        }

        // testing [show in this page]
        textViewAllSubscriber.setText(subscriptionDB.fetchAllTheTableData());
    }
    public void buttonRemoveSubscription(View view){
        subscriptionDB = new SubscriptionDB(this);
        userEmail = editTextSubscription.getText().toString();

        // Remove form Database
        int status = subscriptionDB.updateSubscriptionStatus(userEmail, "No");
        if(status != 0){
            Toast.makeText(this, "Subscription Removed", Toast.LENGTH_SHORT).show();
        }

        // testing [show in this page]
        textViewAllSubscriber.setText(subscriptionDB.fetchAllTheTableData());
    }

}
