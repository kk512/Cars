package com.application.cars.cars.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.application.cars.cars.R;
import com.application.cars.cars.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportFragmentManager().beginTransaction().add(R.id.content, new HomeFragment(), "Home").addToBackStack("Home").commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (currentFragment instanceof HomeFragment)
            finish();
    }
}
