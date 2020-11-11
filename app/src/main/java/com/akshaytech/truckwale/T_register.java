package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class T_register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10,editText11;
    String fullname, email, address, city, state, contact, VT, VN,Permit , password, cpassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_register);
        editText1 = findViewById(R.id.T_username);
        editText2 = findViewById(R.id.T_Email);
        editText3 = findViewById(R.id.VT);
        editText4 = findViewById(R.id.VN);
        editText5 = findViewById(R.id.T_Permit);
        editText6 = findViewById(R.id.T_Address);
        editText7 = findViewById(R.id.T_City);
        editText8 = findViewById(R.id.T_State);
        editText9 = findViewById(R.id.T_CN);
        editText10 = findViewById(R.id.T_Password);
        editText11 = findViewById(R.id.T_CPassword);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Transport.class));
            finish();
        }
    }

    public void Registration(View view) {
        fullname = editText1.getText().toString();
        email = editText2.getText().toString();
        VT = editText3.getText().toString();
        VN = editText4.getText().toString();
        Permit = editText5.getText().toString();
        address = editText6.getText().toString();
        city = editText7.getText().toString();
        state = editText8.getText().toString();
        contact = editText9.getText().toString();
        password = editText10.getText().toString();
        cpassword = editText11.getText().toString();
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Transporter").document(UserID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", fullname);
                    user.put("email", email);
                    user.put("Vehicle type", VT);
                    user.put("Vehicle number", VN);
                    user.put("Permit", Permit);
                    user.put("Address", address);
                    user.put("city", city);
                    user.put("state", state);
                    user.put("contact", contact);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onsuccess: user profile is created for " + UserID);
                        }
                    });

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(T_register.this);
                    alertDialogBuilder.setTitle("Result");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(T_register.this, "Redirecting to login page...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), T_login.class));
                        }
                    });
                    alertDialogBuilder.setMessage("Registered successfully.");
                    alertDialogBuilder.show();

                } else {
                    Toast.makeText(T_register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

