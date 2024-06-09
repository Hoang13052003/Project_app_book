package com.example.project_app_book.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_book.R;
import com.example.project_app_book.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextPassword, editTextEmail;

    private TextView tvDangKy;
    private Button btnLogin;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeControls();
        initializeEvents();
    }

    private void initializeControls() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        userRef = db.getReference("user");
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvDangKy = findViewById(R.id.tvRegister);
    }


    private void initializeEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (validateInputs(email, password)) {
            authenticateUser(email, password);
        }
    }

    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void authenticateUser(final String email, final String password) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean loginSuccess = false;
                User loggedInUser = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dbEmail = snapshot.child("email").getValue(String.class);
                    String dbPassword = snapshot.child("password").getValue(String.class);
                    if (email.equals(dbEmail) && password.equals(dbPassword)) {
                        String userID = snapshot.getKey();
                        String address = snapshot.child("address").getValue(String.class);
                        String dateOfBirth = snapshot.child("dateOfBirth").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);

                        Map<String, Boolean> favouriteMap = new HashMap<>();
                        DataSnapshot favouritesSnapshot = snapshot.child("favourite");
                        for (DataSnapshot favourite : favouritesSnapshot.getChildren()) {
                            favouriteMap.put(favourite.getKey(), favourite.getValue(Boolean.class));
                        }

                        loggedInUser = new User(userID,address, new ArrayList<>(favouriteMap.values()), dateOfBirth, dbEmail, name, dbPassword, phone);
                        loginSuccess = true;
                        break;
                    }
                }

                if (loginSuccess) {
                    navigateToMainActivity(loggedInUser);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
        finish();
    }
}
