package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class PlaceOrder extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText editText1,editText2,editText3,editText4;
    String Name,Email,Phone,fromAddress,toAddress,material,capacity,date,time;
    FirebaseAuth fAuth;
    String UserID;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        editText1 = findViewById(R.id.pickupAddress);
        editText2 = findViewById(R.id.DeliveryAddress);
        editText3 = findViewById(R.id.Material);
        editText4 = findViewById(R.id.capacity);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public void Order(View view) {
        fromAddress = editText1.getText().toString();
        toAddress = editText2.getText().toString();
        material = editText3.getText().toString();
        capacity = editText4.getText().toString();
        UserID = fAuth.getCurrentUser().getUid();
        if(TextUtils.isEmpty(fromAddress)){
            editText1.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(toAddress)){
            editText2.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(material)){
            editText3.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(capacity)){
            editText4.setError("This field is required");
            return;
        }

        fStore.collection("Shopkeepers").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                    DocumentReference documentReference = fStore.collection("Orders").document(key);
                    Map<String, Object> order = new HashMap<>();
                    order.put("Name",Name);
                    order.put("Email",Email);
                    order.put("Contact number",Phone);
                    order.put("Pickup Address",fromAddress);
                    order.put("Delivery Address",toAddress);
                    order.put("Material type",material);
                    order.put("Capacity",capacity+" Kg");
                    order.put("Date",date);
                    order.put("Time",time);
                    documentReference.set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG,"onsuccess: order placed "+UserID);
                        }
                    });
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceOrder.this);
        alertDialogBuilder.setTitle("Result");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PlaceOrder.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ShopkeeperActivity.class));
            }
        });
        alertDialogBuilder.setMessage("Order Placed! You will get notification in 1 hour.");
        alertDialogBuilder.show();
    }
}
