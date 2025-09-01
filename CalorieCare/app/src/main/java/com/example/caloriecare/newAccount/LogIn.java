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
import com.example.caloriecare.client.ClientHomePage;
import com.example.caloriecare.database.AuthenticatorDB;
import com.example.caloriecare.database.SubscriptionDB;
import com.example.caloriecare.nutritionist.NutritionistHomePage;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity{

    public static  int loginUserId;
    public static String loginUserName;
    public static String loginUserEmail;
    public static String loginUserPremiumMemberStatus;



    // Declare Variable
    EditText editTextEmail, editTextPassword;
    Button buttonSignUp, buttonSignIn;
    CheckBox checkboxShowPassword;
    Spinner spinner;
    String selectedUser;
    List<String> clientType;

    TextView out;


    @SuppressLint({"Range", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login);



        // Locate All Field from .xml file
        editTextEmail = findViewById(R.id.editText_email_login);
        editTextPassword = findViewById(R.id.editText_password_login);
        buttonSignUp = findViewById(R.id.button_signUp_login);
        buttonSignIn = findViewById(R.id.button_signIn_login);
        checkboxShowPassword = findViewById(R.id.checkBox_showPassword_login);
        spinner = findViewById(R.id.spinner_clientType_login);




        // SignIn Button
        buttonSignIn.setOnClickListener(v -> {
            AuthenticatorDB authenticatorDB = new AuthenticatorDB(this);
            SubscriptionDB subscriptionDB = new SubscriptionDB(this);


            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "Enter both Email and Password", Toast.LENGTH_SHORT).show();
                return;
            }

            try{
                // Sending Data To Database
                Cursor cursor = authenticatorDB.logIn(email, password);


                // If Matched, invoke to 'if'
                if(cursor.moveToFirst()){

                    // Testing : Printout all the info
                    out = findViewById(R.id.output_login);
                    String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String userPass = cursor.getString(cursor.getColumnIndexOrThrow("password"));String userPhone = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String userType = cursor.getString(cursor.getColumnIndexOrThrow("user_type"));
                    String securityQuestion = cursor.getString(cursor.getColumnIndexOrThrow("security_question"));
                    String securityAnswer = cursor.getString(cursor.getColumnIndexOrThrow("security_answer"));
                    out.setText("Name: " + userName + "\nEmail: " + userEmail + "\nPhone; " + userPhone +"\nPassword: " + userPass + "\nDesignation: " + userType + "\nSecurityQuestion: " + securityQuestion + "\nSecurityAnswer: " + securityAnswer + "\n");

                    /*loginUserId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));*/
                    loginUserName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    loginUserEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));


                   if(selectedUser.equals("Client")  && selectedUser.equals(userType)){
                       loginUserPremiumMemberStatus = subscriptionDB.getSubscriptionStatus(loginUserEmail);
                       Toast.makeText(this, "Client Login Successful", Toast.LENGTH_SHORT).show();
                       Intent goToClientHomePage = new Intent(LogIn.this, ClientHomePage.class);
                       startActivity(goToClientHomePage);
                    }else if (selectedUser.equals("Nutritionist") && selectedUser.equals(userType)){
                        Toast.makeText(this, "Nutritionist Login Successful", Toast.LENGTH_SHORT).show();
                        Intent goToNutritionistHomePage = new Intent(LogIn.this, NutritionistHomePage.class);
                        startActivity(goToNutritionistHomePage);
                    }else Toast.makeText(this, "Enter Valid Info & Select User Type", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }catch (Exception ignored){}
        });






        // SignUp Button
        buttonSignUp.setOnClickListener(v -> {
            Intent goToRegistration = new Intent(LogIn.this, Registration.class);
            startActivity(goToRegistration);
        });





        // CheckBox to Show Password
        checkboxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show Password
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Hide Password
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });









        // Spinner info
        clientType = new ArrayList<>();
        clientType.add("Nutritionist");
        clientType.add("Client");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedUser = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }
}