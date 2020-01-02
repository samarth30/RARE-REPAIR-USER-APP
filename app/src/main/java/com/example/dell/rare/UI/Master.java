package com.example.dell.rare.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dell.rare.R;
import com.example.dell.rare.helper.ScrollHandler;
import com.example.dell.rare.home.HomeFragment;
import com.example.dell.rare.orders.OrderFragment;
import com.example.dell.rare.dashboard.Dashboard;
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

        final BottomNavigationView mMainNav = findViewById(R.id.nav_view);


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
                        return true;

                    case R.id.navigation_orders:
                        setFragment(orderFragment);
                        return true;

                    case R.id.navigation_dashboard:
                        setFragment(dashboardFrament);
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



}
