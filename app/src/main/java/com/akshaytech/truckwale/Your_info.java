package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Your_info extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText editText1,editText2,editText3,editText4,editText5;
    String Name,Email,Phone,currentLoc,Destination,Gtype,date,time,permit,vehiclenumber;
    FirebaseAuth fAuth;
    String UserID;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_info);
        editText1 = findViewById(R.id.currentLocation);
        editText2 = findViewById(R.id.Destination);
        editText3 = findViewById(R.id.GoodsType);
        editText4 = findViewById(R.id.vehiclenumber);
        editText5 = findViewById(R.id.permit);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public void SubmitLocation(View view) {
        currentLoc = editText1.getText().toString();
        Destination = editText2.getText().toString();
        Gtype = editText3.getText().toString();
        vehiclenumber = editText4.getText().toString();
        permit = editText5.getText().toString();
        UserID = fAuth.getCurrentUser().getUid();
        if(TextUtils.isEmpty(currentLoc)){
            editText1.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(Destination)){
            editText2.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(Gtype)){
            editText3.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(vehiclenumber)){
            editText4.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(permit)){
            editText5.setError("This field is required");
            return;
        }
        fStore.collection("Transporter").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Name = documentSnapshot.getString("name");
                    Email = documentSnapshot.getString("email");
                    Phone = documentSnapshot.getString("contact");
                    date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
                    time = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                    final String key = Email+"("+UUID.randomUUID().toString()+")";
                    DocumentReference documentReference = fStore.collection("Current Location").document(key);
                    Map<String, Object> currentLocation = new HashMap<>();
                    currentLocation.put("Name",Name);
                    currentLocation.put("Email",Email);
                    currentLocation.put("Contact number",Phone);
                    currentLocation.put("Current Location",currentLoc);
                    currentLocation.put("Destination",Destination);
                    currentLocation.put("Goods type",Gtype);
                    currentLocation.put("Vehicle number",vehiclenumber);
                    currentLocation.put("Permit",permit);
                    currentLocation.put("Date",date);
                    currentLocation.put("Time",time);
                    documentReference.set(currentLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG,"onsuccess: location submitted"+UserID);
                        }
                    });
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Your_info.this);
        alertDialogBuilder.setTitle("Result");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Your_info.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Transport.class));
            }
        });
        alertDialogBuilder.setMessage("Location submitted successfully.");
        alertDialogBuilder.show();
    }
}
