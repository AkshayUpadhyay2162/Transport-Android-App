package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class T_login extends AppCompatActivity {

    EditText editText1, editText2;
    String email, password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_login);
        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.pass);
        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulogin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void Home(MenuItem item) {
        Intent intent = new Intent(T_login.this, MainActivity.class);
        startActivity(intent);
    }

    public void Login2(View view) {
        email = editText1.getText().toString();
        password = editText2.getText().toString();
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(T_login.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Transport.class));
                }
                else {
                    Toast.makeText(T_login.this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void Register(View view) {
        startActivity(new Intent(getApplicationContext(),T_register.class));
    }
}