package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Orders_Activity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private FirebaseFirestore fStore;
    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter adapter;
    String Name,email,Phone,faddress,daddress,material,capacity,date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_);
        fStore = FirebaseFirestore.getInstance();
        arrayList = new ArrayList();
        listView = findViewById(R.id.listorder);
        listView.setBackgroundColor(Color.parseColor("#BACCD1"));
        fStore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                arrayList.clear();
                for(DocumentChange snapshot : documentSnapshots.getDocumentChanges()){
                    if(snapshot.getType()==DocumentChange.Type.ADDED){
                        arrayList.add(snapshot.getDocument().getString("Email"));
                    }
                }
                adapter = new ArrayAdapter(Orders_Activity.this, android.R.layout.simple_list_item_1, arrayList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
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
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Orders_Activity.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Orders_Activity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
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
