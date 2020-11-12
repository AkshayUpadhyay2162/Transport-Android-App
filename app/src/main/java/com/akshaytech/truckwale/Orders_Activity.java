package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Orders_Activity extends AppCompatActivity {
    ListView listView;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String Name,Email,Phone,faddress,daddress,material,capacity,date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.orderlist);
        arrayList = new ArrayList();
        listView.setBackgroundColor(Color.parseColor("#BACCD1"));
        fStore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                arrayList.clear();
                for(DocumentSnapshot snapshot : documentSnapshots){
                    arrayList.add(snapshot.getString("Email"));
                }
                arrayAdapter = new ArrayAdapter(Orders_Activity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                fStore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                        String Email = (String) arrayList.get(position);
                        for(DocumentSnapshot snapshot : documentSnapshots){
                            if(Email.equals(snapshot.getString("Email"))){
                                faddress = snapshot.getString("Pickup Address");
                                daddress = snapshot.getString("Delivery Address");
                                material = snapshot.getString("Material type");
                                Name = snapshot.getString("Name");
                                Email = snapshot.getString("Email");
                                Phone = snapshot.getString("Contact number");
                                capacity = snapshot.getString("Capacity");
                                date = snapshot.getString("Date");
                                time = snapshot.getString("Time");
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Orders_Activity.this);
                                alertDialogBuilder.setTitle("Order");
                                alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Orders_Activity.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                                       }
                                });
                                alertDialogBuilder.setMessage("Name: "+Name+
                                        "\nEmail: "+Email+
                                        "\nContact: "+Phone+
                                        "\nPickup address: "+faddress
                                        +"\nDelivery address: "+daddress
                                        +"\nMaterial: "+material+
                                        "\nCapacity: "+capacity+
                                        "\nDate: "+date+
                                        "\nTime: "+time);
                                alertDialogBuilder.show();
                            }

                        }
                    }
                });
            }
        });
        

    }

}
