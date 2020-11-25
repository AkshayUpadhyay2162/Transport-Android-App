package com.akshaytech.truckwale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void Orderslist(View view) {
    startActivity(new Intent(getApplicationContext(),Orders_Activity.class));
    }

    public void Admin_logout(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void shopkeepers_list(View view) {
        startActivity(new Intent(getApplicationContext(),shopkeepers_list.class));
    }

    public void transporters_list(View view) {
        startActivity(new Intent(getApplicationContext(),Transporters_list.class));
    }
}
