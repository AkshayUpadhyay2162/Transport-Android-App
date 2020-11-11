package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class S_Profile extends AppCompatActivity {
    TextView S_Name,S_Email,S_Phone,S_ShopAddress,S_City,S_State;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;
    String sname,semail,sphone,saddress,scity,sstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__profile);
        S_Name = findViewById(R.id.S_name);
        S_Email = findViewById(R.id.S_email);
        S_Phone = findViewById(R.id.S_contact);
        S_ShopAddress = findViewById(R.id.S_shopaddress);
        S_City = findViewById(R.id.S_city);
        S_State = findViewById(R.id.S_state);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();

        fStore.collection("Shopkeepers").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                sname = documentSnapshot.getString("name");
                semail = documentSnapshot.getString("email");
                sphone = documentSnapshot.getString("contact");
                saddress = documentSnapshot.getString("Shop address");
                scity = documentSnapshot.getString("city");
                sstate = documentSnapshot.getString("state");
                S_Name.setText(sname);
                S_Email.setText(semail);
                S_Phone.setText(sphone);
                S_ShopAddress.setText(saddress);
                S_City.setText(scity);
                S_State.setText(sstate);
                }
            }
        });
    }

    public void S_Logout(View view) {
        fAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),S_login.class));
    }
}
