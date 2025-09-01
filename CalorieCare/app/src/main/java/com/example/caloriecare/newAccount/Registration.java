package com.example.caloriecare.newAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.R;
import com.example.caloriecare.database.AuthenticatorDB;
import com.example.caloriecare.database.SubscriptionDB;
import com.example.caloriecare.database.UserInfoDB;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity {

    AuthenticatorDB authenticationDB;
    SubscriptionDB subscriptionDb;
    UserInfoDB userInfoDB;
    EditText editTextName, editTextEmail, editTextPhone, editTextPassword, editTextSecurityQuestion, editTextSecurityAnswer;
    CheckBox checkBoxShowPassword;
    Button buttonLogin, buttonReset, buttonSubmit;
    Spinner spinner;
    static String selectedUserType;
    List<String> clientType;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Locate EditText, Button, Checkbox
        editTextName = findViewById(R.id.editText_name);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextPassword = findViewById(R.id.editText_password);
        editTextSecurityQuestion = findViewById(R.id.editText_security_question);
        editTextSecurityAnswer = findViewById(R.id.editText_security_answer);

        buttonLogin = findViewById(R.id.button_login);
        buttonReset = findViewById(R.id.button_reset);
        buttonSubmit = findViewById(R.id.button_submit);

        checkBoxShowPassword = findViewById(R.id.checkBox_showPassword);
        spinner = findViewById(R.id.spinner_clientType_registration);





        // Login Button
        buttonLogin.setOnClickListener(v -> {
            Intent goToMainActivity = new Intent(Registration.this, LogIn.class);
            startActivity(goToMainActivity);
        });






        // Reset Button
        buttonReset.setOnClickListener(v -> {
            editTextName.setText("");
            editTextEmail.setText("");
            editTextPhone.setText("");
            editTextPassword.setText("");
            editTextSecurityQuestion.setText("");
            editTextSecurityAnswer.setText("");
        });





        // Submit Button
        buttonSubmit.setOnClickListener(v -> {
            // Declare Database
            authenticationDB = new AuthenticatorDB(this);
            subscriptionDb = new SubscriptionDB(this);
            userInfoDB = new UserInfoDB(this);

            String userName, userEmail, userPhone, userPassword, userQuestion, userAnswer;

            userName = editTextName.getText().toString();
            userEmail = editTextEmail.getText().toString();
            userPhone = editTextPhone.getText().toString();
            userPassword = editTextPassword.getText().toString();
            userQuestion = editTextSecurityQuestion.getText().toString();
            userAnswer = editTextSecurityAnswer.getText().toString();


            if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPhone)
                || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userQuestion) || TextUtils.isEmpty(userAnswer)){

                Toast.makeText(this, "Input In All Field", Toast.LENGTH_SHORT).show();
                return;
            }


            // Check Duplicate Account
            Cursor getData = authenticationDB.checkDuplicateAccount(userEmail, userPhone);
            if(getData.moveToFirst()){
                Toast.makeText(this, "Duplicate Email or Phone", Toast.LENGTH_SHORT).show();
                return;
            }

            // If no Duplicate, try to store
            long storeFlagAuthenticationDB = authenticationDB.signUp(userName, userEmail, userPhone, userPassword, userQuestion, userAnswer, selectedUserType);

            // Store in UserInfoDB
            if(selectedUserType.equals("Client")){
                userInfoDB.storeUserInfo(userName, userEmail, " ", " ", " ", " ", " ", " ", " "," ", "No", "No");
                subscriptionDb.addSubscription(userEmail, "No");
            }

            if(storeFlagAuthenticationDB != -1){
                Toast.makeText(this, "Stored Successful", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Stored Unsuccessful", Toast.LENGTH_SHORT).show();
            }

            // testing [Fetch Data to check if Data Entry is correct]
            Cursor cursor = authenticationDB.logIn(userEmail, userPassword);
            if(cursor.moveToFirst()){
                TextView out = findViewById(R.id.output);
                String nm = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String eml = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String ph = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String s_q = cursor.getString(cursor.getColumnIndexOrThrow("security_question"));
                String s_a = cursor.getString(cursor.getColumnIndexOrThrow("security_answer"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("user_type"));
                out.setText("Name: " + nm + "\nEmail: " + eml + "\nPhone; " + ph +"\nPassword: " + pass + "\nSecurityQuestion: " + s_q + "\nSecurityAnswer: " + s_a + "\nUserType: " + type);
            }
            cursor.close();
        });

        // CheckBox to Show Password
        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show Password
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Hide Password
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });







        // Spinner
        clientType = new ArrayList<>();
        clientType.add("Client");
        clientType.add("Nutritionist");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedUserType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }
}
