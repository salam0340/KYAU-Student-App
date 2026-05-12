package com.example.kyaustudentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kyaustudentapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(SplashActivity.this,
                            DashboardActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this,
                            LoginActivity.class));
                }
            } catch (Exception e) {
                startActivity(new Intent(SplashActivity.this,
                        LoginActivity.class));
            }
            finish();
        }, 3000);
    }
}