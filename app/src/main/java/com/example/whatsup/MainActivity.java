package com.example.whatsup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsup.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import Adapter.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("WhatsUp");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.write);
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.backgroundtablayout));
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.backgroundactionbar));
        auth=FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewPager);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.logout)
        {
            auth.signOut();
            Intent intent=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.settings)
        {
            Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.newgrp)
        {
            Toast.makeText(MainActivity.this, "New Group", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}