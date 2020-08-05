package com.akshaytech.truckwale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class S_login extends AppCompatActivity {
    EditText editText1,editText2;
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_login);
        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.pass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulogin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void Home(MenuItem item) {
        Intent intent = new Intent(S_login.this,MainActivity.class);
        startActivity(intent);
    }
    public void Login(View view) {
    email = editText1.getText().toString();
    password = editText2.getText().toString();

    if(email.equals("akshay@gmail.com") && password.equals("12345")){
        Intent intent = new Intent(S_login.this,Shopkeeper.class);
        startActivity(intent);
        Toast.makeText(this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
    }
    else Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
    }
}
