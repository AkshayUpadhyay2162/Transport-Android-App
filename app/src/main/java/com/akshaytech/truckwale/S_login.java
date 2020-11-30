package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class S_login extends AppCompatActivity {
    CheckBox checkBox;
    EditText editText1,editText2;
    ProgressBar progressBar;
    String email, password;
    FirebaseAuth fAuth;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_login);
        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.pass);
        textView = findViewById(R.id.S_register);
        checkBox = findViewById(R.id.rememberme);
        progressBar = findViewById(R.id.slogin_pb);
        progressBar.setVisibility(View.GONE);
        fAuth = FirebaseAuth.getInstance();

        Snackbar.make(findViewById(android.R.id.content),"Shopkeeper login",Snackbar.LENGTH_LONG).show();

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
        progressBar.setVisibility(View.VISIBLE);
        email = editText1.getText().toString();
        password = editText2.getText().toString();

    if(TextUtils.isEmpty(email)){
        editText1.setError("This field is required!");
        return;
    }
    if(TextUtils.isEmpty(password)){
        editText2.setError("This field is required!");
        return;
    }

    if(password.length()<6){
        editText2.setError("Password should be greater than or equal to 6!");
        return;
    }

    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Toast.makeText(S_login.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),ShopkeeperActivity.class));
            }
            else {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content),"Incorrect email or password!",Snackbar.LENGTH_LONG).show();
            }
        }
    });
    }

    public void Register(View view) {
        textView.setTextColor(Color.GRAY);
        Intent intent = new Intent(S_login.this,S_Register.class);
        startActivity(intent);
    }

}
