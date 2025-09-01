package com.example.caloriecare.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import com.example.caloriecare.newAccount.LogIn;
import com.example.caloriecare.R;
import com.example.caloriecare.database.ClientBioDataDB;
import com.example.caloriecare.database.UserInfoDB;


public class UploadPrescription extends AppCompatActivity {

    UserInfoDB database;
    ClientBioDataDB clientBioDataDB;
    ArrayList<String> genderType, diabeticStatus, allergyStatus, dietStatus;

    Button buttonGoBack, buttonReset, buttonSubmit;
    Spinner spinnerGender, spinnerDiabetic, spinnerAllergy, spinnerDiet;
    EditText editTextAge, editTextHeight, editTextWeight, editTextOccupation;
    String userAge, userHeight, userWeight, userOccupation;
    String selectedGenderType, selectedDiabeticStatus, selectedAllergyStatus, selectedDietStatus;

    //static imported through LogIn
    /*int userId =  LogIn.loginUserId;*/
    String userName = LogIn.loginUserName;
    String userEmail = LogIn.loginUserEmail;

    TextView output;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_uploadprescription);

        output = findViewById(R.id.output_UploadPrescription);

        editTextAge = findViewById(R.id.editText_age_UploadPrescription);
        editTextHeight = findViewById(R.id.editText_height_UploadPrescription);
        editTextWeight = findViewById(R.id.editText_weight_UploadPrescription);
        editTextOccupation = findViewById(R.id.editText_occupation_UploadPrescription);

        spinnerGender = findViewById(R.id.spinner_gender_UploadPrescription);
        spinnerDiabetic = findViewById(R.id.spinner_diabetic_UploadPrescription);
        spinnerAllergy = findViewById(R.id.spinner_allergy_UploadPrescription);
        spinnerDiet = findViewById(R.id.spinner_diet_UploadPrescription);

        buttonGoBack = findViewById(R.id.button_goBackFrom_UploadPrescription);
        buttonReset = findViewById(R.id.button_reset_UploadPrescription);
        buttonSubmit = findViewById(R.id.button_submit_UploadPrescription);

        buttonGoBack.setOnClickListener(v -> {
            Toast.makeText(this, "Go Back", Toast.LENGTH_SHORT).show();
            Intent goBackToHomepage = new Intent(UploadPrescription.this, ClientHomePage.class);
            startActivity(goBackToHomepage);
        });

        buttonReset.setOnClickListener(v -> {
            editTextAge.setText("");
            editTextHeight.setText("");
            editTextWeight.setText("");
            editTextOccupation.setText("");
            Toast.makeText(this, "Screen Clear", Toast.LENGTH_SHORT).show();
        });

        buttonSubmit.setOnClickListener(v -> {
            database = new UserInfoDB(this);
            clientBioDataDB = new ClientBioDataDB(this);

            userAge = editTextAge.getText().toString();
            userHeight = editTextHeight.getText().toString();
            userWeight = editTextWeight.getText().toString();
            userOccupation = editTextOccupation.getText().toString();

            if(TextUtils.isEmpty(userAge) || TextUtils.isEmpty(userHeight) || TextUtils.isEmpty(userWeight) || TextUtils.isEmpty(userOccupation)){
                Toast.makeText(this, "Input in all fields", Toast.LENGTH_SHORT).show();
                return;
            }


            // Try to store all Data
            try{
                long rowUpdated = database.updateUserInfo(userName, userEmail, userAge, userHeight, userWeight, userOccupation, selectedGenderType, selectedDiabeticStatus, selectedAllergyStatus, selectedDietStatus, "No");
                if(rowUpdated == -1){
                    Toast.makeText(this, "Store Unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "Stored Successfully", Toast.LENGTH_SHORT).show();
                Cursor cursor = database.fetchAllData(userName, userEmail);

                // Try to store individual data to a new table so that 'Nutritionist' could get those easily
                if(cursor != null && cursor.moveToFirst()){
                    String getAge = cursor.getString(cursor.getColumnIndexOrThrow("age"));
                    String getHeight = cursor.getString(cursor.getColumnIndexOrThrow("height"));
                    String getWeight = cursor.getString(cursor.getColumnIndexOrThrow("weight"));
                    String getOccupation = cursor.getString(cursor.getColumnIndexOrThrow("occupation"));
                    String getDiabeticStatus = cursor.getString(cursor.getColumnIndexOrThrow("diabetic_status"));
                    String getAllergyType = cursor.getString(cursor.getColumnIndexOrThrow("allergy_status"));
                    String getDietType = cursor.getString(cursor.getColumnIndexOrThrow("diet_status"));
                    String getUpdatedByNutritionistStatus = cursor.getString(cursor.getColumnIndexOrThrow("updated_by_nutritionist_status"));
                    String getTime = cursor.getString(cursor.getColumnIndexOrThrow("time"));


                    // name, email, age, gender
                    String storeCurrentInfo ="Time: " + getTime + "\nAge: " + getAge +
                            "\nHeight: " + getHeight +"\nWeight: " + getWeight + "\nOccupation: " + getOccupation +
                            "\nDiabetic Status: " + getDiabeticStatus +
                            "\nAllergy Status: " + getAllergyType + "\nDiet Status: " + getDietType + "\n\n";

                    // Storing data
                    long dataStoredFlag = clientBioDataDB.storeAllData(userEmail, storeCurrentInfo);
                    if(dataStoredFlag == -1){
                        Toast.makeText(this, "Store Unsuccessful", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    output.setText(storeCurrentInfo);



                    cursor.close();

                    Toast.makeText(this, "Fetch Successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Fetch Unsuccessful", Toast.LENGTH_SHORT).show();


            }catch (Exception ignored){}

        });















        // Spinner Gender
        genderType = new ArrayList<>();
        selectedGenderType = "Male";
        genderType.add("Male");
        genderType.add("Female");
        genderType.add("Others");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderType);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedGenderType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        // Spinner Diabetic
        diabeticStatus = new ArrayList<>();
        selectedDiabeticStatus = "No";
        diabeticStatus.add("No");
        diabeticStatus.add("Yes");

        ArrayAdapter<String> diabeticAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diabeticStatus);
        diabeticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiabetic.setAdapter(diabeticAdapter);

        spinnerDiabetic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDiabeticStatus = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Spinner Allergy
        allergyStatus = new ArrayList<>();
        selectedAllergyStatus = "No";
        allergyStatus.add("No");
        allergyStatus.add("Yes");

        ArrayAdapter<String> allergyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allergyStatus);
        allergyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAllergy.setAdapter(allergyAdapter);

        spinnerAllergy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedAllergyStatus = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Spinner Diet
        dietStatus = new ArrayList<>();
        selectedDietStatus = "Non-Veg";
        dietStatus.add("Non-Veg");
        dietStatus.add("Veg");

        ArrayAdapter<String> dietAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dietStatus);
        dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiet.setAdapter(dietAdapter);

        spinnerDiet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDietStatus = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


    }
}
