package com.example.whatsup;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsup.databinding.ActivityChatDetailBinding;
import com.example.whatsup.databinding.ActivityMainBinding;
import com.example.whatsup.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle("WhatsUp");
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.write);
//        actionBar.setBackgroundDrawable(getDrawable(R.drawable.backgroundactionbar));
        getSupportActionBar().hide();

        String senderId=auth.getUid();
        String recieverId=getIntent().getStringExtra("userId");
        String profilepic=getIntent().getStringExtra("profilepic");
        String username=getIntent().getStringExtra("username");

        binding.profileName.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.prof).into(binding.profileImage);

    }
}