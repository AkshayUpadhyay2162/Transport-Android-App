package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class PlaceOrder extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText editText1,editText2,editText3,editText4;
    String Name,Email,Phone,fromAddress,toAddress,material,capacity;
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

        fStore.collection("Shopkeepers").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Name = documentSnapshot.getString("name");
                    Email = documentSnapshot.getString("email");
                    Phone = documentSnapshot.getString("contact");
                    DocumentReference documentReference = fStore.collection("Orders").document(UserID);
                    Map<String, Object> order = new HashMap<>();
                    order.put("Name",Name);
                    order.put("Email",Email);
                    order.put("Contact number",Phone);
                    order.put("Pickup Address",fromAddress);
                    order.put("Delivery Address",toAddress);
                    order.put("Material type",material);
                    order.put("Capacity",capacity);
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
                Toast.makeText(PlaceOrder.this, "Redirecting to login page...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PlaceOrder.class));
            }
        });
        alertDialogBuilder.setMessage("Order Placed! You will get notification in 1 hour.");
        alertDialogBuilder.show();


    }
}
