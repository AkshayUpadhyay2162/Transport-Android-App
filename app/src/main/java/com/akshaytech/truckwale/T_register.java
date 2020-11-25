package com.akshaytech.truckwale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class T_register extends AppCompatActivity {
    ImageView profilepic;
    public Uri imageUri;
    public static final String TAG = "TAG";
    EditText editText1, editText2, editText3, editText4, editText6, editText7, editText8, editText9, editText10,editText11;
    String fullname, email, address, city, state, contact, VT, VN,Permit , password, cpassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_register);
        editText1 = findViewById(R.id.T_username);
        editText2 = findViewById(R.id.T_Email);
        editText3 = findViewById(R.id.VT);
        editText4 = findViewById(R.id.VN);
        editText6 = findViewById(R.id.T_Address);
        editText7 = findViewById(R.id.T_City);
        editText8 = findViewById(R.id.T_State);
        editText9 = findViewById(R.id.T_CN);
        editText10 = findViewById(R.id.T_Password);
        editText11 = findViewById(R.id.T_CPassword);
        profilepic = findViewById(R.id.t_profile_pic);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Transport.class));
            finish();
        }
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosepicture();
            }
        });
    }

    private void choosepicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilepic.setImageURI(imageUri);
        }
    }

    public void Registration(View view) {
        fullname = editText1.getText().toString();
        email = editText2.getText().toString();
        VT = editText3.getText().toString();
        VN = editText4.getText().toString();
        address = editText6.getText().toString();
        city = editText7.getText().toString();
        state = editText8.getText().toString();
        contact = editText9.getText().toString();
        password = editText10.getText().toString();
        cpassword = editText11.getText().toString();
        if(TextUtils.isEmpty(fullname)){
            editText1.setError("This field is required!");
            return;
        }

        if(TextUtils.isEmpty(email)){
            editText2.setError("This field is required!");
            return;
        }
        if(TextUtils.isEmpty(VT)){
            editText3.setError("This field is required!");
            return;
        }
        if(TextUtils.isEmpty(VN)){
            editText4.setError("This field is required!");
            return;
        }

        if(TextUtils.isEmpty(address)){
            editText6.setError("This field is required!");
            return;
        }

        if(TextUtils.isEmpty(city)){
            editText7.setError("This field is required!");
            return;
        }
        if(TextUtils.isEmpty(state)){
            editText8.setError("This field is required!");
            return;
        }
        if(TextUtils.isEmpty(contact)){
            editText9.setError("This field is required!");
            return;
        }
        if(TextUtils.isEmpty(password)){
            editText10.setError("This field is required!");
            return;
        }

        if(password.length()<6){
            editText10.setError("Password must be greater than or equal to 6!");
            return;
        }
        if(TextUtils.isEmpty(cpassword)){
            editText11.setError("This field is required!");
            return;
        }
        if(password.equals(cpassword)) {
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        UserID = fAuth.getCurrentUser().getUid();
                        uploadPicture();
                        DocumentReference documentReference = fStore.collection("Transporter").document(UserID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", fullname);
                        user.put("email", email);
                        user.put("Vehicle type", VT);
                        user.put("Total no. of vehicles", VN);
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
                                fAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), T_login.class));
                            }
                        });
                        alertDialogBuilder.setMessage("Registered successfully.");
                        alertDialogBuilder.show();


                    } else {
                        Snackbar.make(findViewById(android.R.id.content),"Registration failed!",Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            editText11.setError("Password does not match!");
            return;
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();
        UserID = fAuth.getCurrentUser().getUid();
        StorageReference riversRef = storageReference.child("images/"+UserID);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Image uploaded...",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(T_register.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                pd.setMessage("Percentage " + (int)progressPercent + "%");
            }
        });
    }
}

