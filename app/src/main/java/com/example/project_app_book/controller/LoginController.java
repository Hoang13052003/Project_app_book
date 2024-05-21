package com.example.project_app_book.controller;

import android.app.Activity;
import android.widget.Toast;

import com.example.project_app_book.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginController {
    private Activity activity;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public LoginController(Activity activity) {
        this.activity = activity;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User logged in successfully, you can perform further actions
                            Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Login failed
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void checkUserExistence(String email) {
        DatabaseReference userRef = databaseReference.child("users").child(email.replace(".", ","));
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        // User exists in the database
                        // You can perform further actions here, like comparing passwords, etc.
                    }
                } else {
                    // User does not exist
                    Toast.makeText(activity, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error occurred, handle it appropriately
            }
        });
    }
}
