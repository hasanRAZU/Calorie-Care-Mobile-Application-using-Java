package com.example.caloriecare.nutritionist;

import static com.example.caloriecare.nutritionist.NutritionistClientList.userEmailToSuggest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.caloriecare.R;
import com.example.caloriecare.database.ClientBioDataDB;
import com.example.caloriecare.database.DailyPlanDB;
import com.example.caloriecare.database.UserInfoDB;

import java.util.List;

public class NutritionistSuggestToClient extends AppCompatActivity {
    DailyPlanDB dailyPlanDB;
    ClientBioDataDB clientBioDataDB;
    UserInfoDB userInfoDB;
    String userEmail = userEmailToSuggest;
    EditText editTextFruit, editTextVegetable, editTextWholeGrain, editTextProtein, editTextDairy, editTextHydration, editTextExercise1, editTextExercise2, editTextExercise3;

    TextView textViewOutput;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_suggest_to_client);

        dailyPlanDB = new DailyPlanDB(this);
        clientBioDataDB = new ClientBioDataDB(this);
        userInfoDB = new UserInfoDB(this);

        editTextFruit = findViewById(R.id.editText_fruit) ;
        editTextVegetable = findViewById(R.id.editText_vegetable);
        editTextWholeGrain = findViewById(R.id.editText_wholeGrain);
        editTextProtein = findViewById(R.id.editText_protein);
        editTextDairy = findViewById(R.id.editText_dairy);
        editTextHydration = findViewById(R.id.editText_hydration);
        editTextExercise1 = findViewById(R.id.editText_exercise1);
        editTextExercise2 = findViewById(R.id.editText_exercise2);
        editTextExercise3 = findViewById(R.id.editText_exercise3);


        textViewOutput = findViewById(R.id.textView_output_SuggestToClient);
        StringBuilder userBioData= new StringBuilder();
        List<String> storedData = clientBioDataDB.fetchAllData(userEmail);
        for(String str : storedData){
            userBioData.append(str);
        }
        textViewOutput.setText(userBioData);

    }

    public void buttonGoBack(View view){
        Intent goBackToPatientList = new Intent(this, NutritionistClientList.class);
        startActivity(goBackToPatientList);
    }

    public void submitDailyPlan(View view){
        String fruit, vegetable, wholeGrain, protein, dairy, hydration, exercise1, exercise2, exercise3;

        fruit = editTextFruit.getText().toString();
        vegetable = editTextVegetable.getText().toString();
        wholeGrain = editTextWholeGrain.getText().toString();
        protein = editTextProtein.getText().toString();
        dairy = editTextDairy.getText().toString();
        hydration = editTextHydration.getText().toString();
        exercise1 = editTextExercise1.getText().toString();
        exercise2 = editTextExercise2.getText().toString();
        exercise3 = editTextExercise3.getText().toString();

        String dailyPlanString = "Fruit: " +fruit + "\nVegetable: " + vegetable + "\nWholeGrain: " + wholeGrain +
                "\nProtein: " + protein + "\nDairy: " + dairy + "\nHydration: " + hydration +
                "\nExercise: " + exercise1 + "\nExercise: " + exercise2 + "\nExercise: " + exercise3 +"\n";
        try{
            long updateStatus = dailyPlanDB.storeAllData(userEmail, dailyPlanString);
            if(updateStatus == -1){
                Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            userInfoDB.updateNutritionistStatus(userEmail, "Yes");
        }catch (Exception ignored){}
    }
}
