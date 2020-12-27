package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class T_Profile extends AppCompatActivity {
    TextView T_Name,T_Email,T_VT,T_NOV,T_Phone,T_Address,T_City,T_State;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;
    String tname,temail,tvt,NOV,tphone,taddress,tcity,tstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t__profile);
        T_Name = findViewById(R.id.t_name);
        T_Email = findViewById(R.id.t_email);
        T_VT = findViewById(R.id.T_VT);
        T_NOV = findViewById(R.id.t_NOV);
        T_Phone = findViewById(R.id.t_contact);
        T_Address = findViewById(R.id.t_address);
        T_City = findViewById(R.id.t_city);
        T_State = findViewById(R.id.t_state);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        fStore.collection("Transporter").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    tname = documentSnapshot.getString("name");
                    temail = documentSnapshot.getString("email");
                    tvt = documentSnapshot.getString("Vehicle type");
                    NOV = documentSnapshot.getString("Total no. of vehicles");
                    tphone = documentSnapshot.getString("contact");
                    taddress = documentSnapshot.getString("Address");
                    tcity = documentSnapshot.getString("city");
                    tstate = documentSnapshot.getString("state");
                    T_Name.setText(tname);
                    T_Email.setText(temail);
                    T_VT.setText(tvt);
                    T_NOV.setText(NOV);
                    T_Phone.setText(tphone);
                    T_Address.setText(taddress);
                    T_City.setText(tcity);
                    T_State.setText(tstate);
//                    Snackbar.make(findViewById(android.R.id.content),""+tnov,Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void T_Logout(View view) {
     fAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),T_login.class));
    }
}
