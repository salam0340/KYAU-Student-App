package com.example.kyaustudentapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.fragments.AttendanceFragment;
import com.example.kyaustudentapp.fragments.CourseFragment;
import com.example.kyaustudentapp.fragments.FinanceFragment;
import com.example.kyaustudentapp.fragments.HomeFragment;
import com.example.kyaustudentapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Default fragment
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_attendance) {
                fragment = new AttendanceFragment();
            } else if (id == R.id.nav_course) {
                fragment = new CourseFragment();
            } else if (id == R.id.nav_finance) {
                fragment = new FinanceFragment();
            } else if (id == R.id.nav_profile) {
                fragment = new ProfileFragment();
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}