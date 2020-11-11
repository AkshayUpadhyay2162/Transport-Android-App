package com.akshaytech.truckwale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_Login extends AppCompatActivity {
    EditText editText1, editText2;
    String email, pass;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);
        editText1 = findViewById(R.id.Admin_email);
        editText2 = findViewById(R.id.Admin_pass);
    }

    public void AdminLogin(View view) {
        email = editText1.getText().toString();
        pass = editText2.getText().toString();
        if(TextUtils.isEmpty(email)){
            editText1.setError("This field is required!");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            editText2.setError("This field is required!");
            return;
        }
        if(pass.length()<6){
            editText2.setError("Password length should be greater than or eqaual to 6!");
            return;
        }

        if(email.equals("akakakak")&&pass.equals("akakakak")){
            Toast.makeText(this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Admin.class));
        }
        else {
            Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            count++;
        }

        if(count>3){
            Toast.makeText(this, "Redirecting to homepage...", Toast.LENGTH_SHORT).show();
            finish();
            System.exit(0);
        }

    }
}
