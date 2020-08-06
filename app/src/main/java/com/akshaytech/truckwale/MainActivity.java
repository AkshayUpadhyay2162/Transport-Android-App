package com.akshaytech.truckwale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.transporter);
        button2 = findViewById(R.id.shopkeeper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menustart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void TransportLogin(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this ,R.anim.bounce);
        button1.startAnimation(animation);
        Toast.makeText(this, "Transport Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,T_login.class);
        startActivity(intent);
    }

    public void shopkeeperLogin(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this ,R.anim.bounce);
        button2.startAnimation(animation);
        Toast.makeText(this, "Shopkeeper Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,S_login.class);
        startActivity(intent);
    }

    public void AdminLogin(MenuItem item) {
        Toast.makeText(this, "Admin Login", Toast.LENGTH_SHORT).show();
    }
}
