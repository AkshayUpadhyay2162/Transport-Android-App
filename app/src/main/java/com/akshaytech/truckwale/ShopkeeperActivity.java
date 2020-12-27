package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ShopkeeperActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView textView;
    ImageView dp;
    String UserId;
    String profilename;
    FirebaseStorage storage;
    StorageReference storageReference;
    CardView cardView1,cardView2,cardView3,cardView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        textView = findViewById(R.id.textname);
        cardView1 = findViewById(R.id.cv1);
        cardView2 = findViewById(R.id.cv2);
        cardView3 = findViewById(R.id.cv3);
        cardView4 = findViewById(R.id.cv4);
        dp = findViewById(R.id.sdp);
        UserId = fAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("images/"+UserId);

        try {
            final File local = File.createTempFile(UserId+"","jpeg");
            storageReference.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                    dp.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ShopkeeperActivity.this, "Profile picture couldn't load", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }


        fStore.collection("Shopkeepers").document(UserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
           if(task.isSuccessful()){
               DocumentSnapshot documentSnapshot = task.getResult();
               profilename = documentSnapshot.getString("name");
               textView.setText(profilename);
           }
            }
        });
    }


    public void S_Logout(View view) {
        Animation animation = AnimationUtils.loadAnimation(ShopkeeperActivity.this ,R.anim.bounce);
        cardView4.startAnimation(animation);
        fAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),S_login.class));
    }

    public void placeOrder(View view) {
        Animation animation = AnimationUtils.loadAnimation(ShopkeeperActivity.this ,R.anim.bounce);
        cardView1.startAnimation(animation);
        startActivity(new Intent(getApplicationContext(),PlaceOrder.class));
    }

    public void Profile(View view) {
        Animation animation = AnimationUtils.loadAnimation(ShopkeeperActivity.this ,R.anim.bounce);
        cardView2.startAnimation(animation);
        startActivity(new Intent(getApplicationContext(),S_Profile.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void Contactus(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),Contact_us.class));
    }

    public void Home(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void ContactUS(View view) {
        Animation animation = AnimationUtils.loadAnimation(ShopkeeperActivity.this ,R.anim.bounce);
        cardView3.startAnimation(animation);
        startActivity(new Intent(getApplicationContext(),Contact_us.class));
    }
}
