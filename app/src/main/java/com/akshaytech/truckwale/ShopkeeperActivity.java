package com.akshaytech.truckwale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ShopkeeperActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper);
        fAuth = FirebaseAuth.getInstance();
    }

    public void S_Logout(View view) {
        fAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),S_login.class));
    }

    public void placeOrder(View view) {
        startActivity(new Intent(getApplicationContext(),PlaceOrder.class));
    }

    public void Profile(View view) {
    startActivity(new Intent(getApplicationContext(),S_Profile.class));
    }
}
