package com.example.project_app_book.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project_app_book.R;
import com.example.project_app_book.model.AnimationUtil;

public class StartActivity extends AppCompatActivity {
    private Button btnBatDau, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        addControls();

        addEvents();
    }

    private void addControls(){
        btnBatDau = findViewById(R.id.btnBatDau);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private void addEvents(){
        btnBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getApplicationContext(), btnBatDau);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.applyScaleAnimation(getApplicationContext(), btnLogin);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}