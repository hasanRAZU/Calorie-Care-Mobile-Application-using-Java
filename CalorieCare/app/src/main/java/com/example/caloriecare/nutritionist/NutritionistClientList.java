package com.example.caloriecare.nutritionist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecare.R;
import com.example.caloriecare.database.UserInfoDB;

import java.util.List;

public class NutritionistClientList extends AppCompatActivity {
    static int userIdToSuggest;
    static String userEmailToSuggest;
    UserInfoDB userInfoDB;
    TextView textViewPatientList;
    EditText editTextUserId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_clientlist);

        textViewPatientList = findViewById(R.id.scrollView_textView_NutritionistPatientList);
        editTextUserId = findViewById(R.id.editText_patientId_NutritionistPatientList);

        StringBuilder allData = new StringBuilder();
        userInfoDB = new UserInfoDB(this);
        List<String> fetchedInfo = userInfoDB.fetchAllUserInfo();
        for (String str : fetchedInfo){
            allData.append(str);
        }

        textViewPatientList.setText(allData.toString());
    }

    public void buttonGoBack(View view){
        Intent goBackToNutritionistHomePage = new Intent(this, NutritionistHomePage.class);
        startActivity(goBackToNutritionistHomePage);
    }

    public void buttonSuggestToClient(View view){
        if(TextUtils.isEmpty(editTextUserId.getText().toString())) {
            Toast.makeText(this, "Enter UserId", Toast.LENGTH_SHORT).show();
            return;
        }

        userIdToSuggest = Integer.parseInt(editTextUserId.getText().toString());
        try{
            Cursor cursor = userInfoDB.getEmailById(userIdToSuggest);
            if (cursor != null && cursor.moveToFirst()) {
                userEmailToSuggest = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                cursor.close();
                Intent goBackToNutritionistSuggestToClient = new Intent(this, NutritionistSuggestToClient.class);
                startActivity(goBackToNutritionistSuggestToClient);
                Toast.makeText(this, userEmailToSuggest, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Enter Valid UserId", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ignored){}
    }
}
