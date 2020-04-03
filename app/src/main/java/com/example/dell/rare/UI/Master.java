package com.example.dell.rare.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dell.rare.R;
import com.example.dell.rare.helper.BottomNavigationViewHelper;
import com.example.dell.rare.home.HomeFragment;
import com.example.dell.rare.orders.OrderFragment;
import com.example.dell.rare.dashboard.Dashboard;
import com.example.dell.rare.utils.ViewAnimation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class Master extends AppCompatActivity {

    FrameLayout mMainFrame;

    HomeFragment homeFragment;
    Dashboard dashboardFrament;
    OrderFragment orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);


        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login.class));
        }

        final BottomNavigationView mMainNav = findViewById(R.id.nav_view);
        BottomNavigationViewHelper.disableShiftMode(mMainNav);

        mMainFrame = (FrameLayout)findViewById(R.id.nav_host_fragment);
        homeFragment = new HomeFragment();
        dashboardFrament = new Dashboard();
        orderFragment = new OrderFragment();


        setFragment(homeFragment);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(homeFragment);
                        ViewAnimation.fadeOutIn(mMainFrame);
                        return true;

                    case R.id.navigation_orders:
                        setFragment(orderFragment);
                        ViewAnimation.fadeOutIn(mMainFrame);
                        return true;

                    case R.id.navigation_dashboard:
                        setFragment(dashboardFrament);
                        ViewAnimation.fadeOutIn(mMainFrame);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
    }
}
