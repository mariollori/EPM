package com.example.examenparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.examenparcial.fragments.addfragment;
import com.example.examenparcial.fragments.homefragment;
import com.example.examenparcial.fragments.listfragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Principal extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        bottomNavigation = findViewById(R.id.bottom_navigation);



        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.home:
                        openFragment(new homefragment());
                        return true;
                    case R.id.list_item:
                        openFragment(new listfragment());
                        return true;
                    case R.id.add_movie:
                        openFragment(new addfragment());
                        return true;

                }
                return false;
            }
        });

        openFragment(new homefragment());




    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}