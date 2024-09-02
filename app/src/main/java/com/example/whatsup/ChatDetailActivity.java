package com.example.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsup.databinding.ActivityChatDetailBinding;
import com.example.whatsup.databinding.ActivityMainBinding;
import com.example.whatsup.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import Adapter.ChattingAdapter;
import Fragments.ChatsFragment;
import Models.MesssageModel;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle("WhatsUp");
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.write);
//        actionBar.setBackgroundDrawable(getDrawable(R.drawable.backgroundactionbar));
        getSupportActionBar().hide();

        final String senderId = auth.getUid();
        String recieverId = getIntent().getStringExtra("userId");
        String profilepic = getIntent().getStringExtra("profilepic");
        String username = getIntent().getStringExtra("username");

        binding.profileName.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.prof).into(binding.profileImage);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MesssageModel> messsageModels = new ArrayList<>();

        final ChattingAdapter chattingAdapter = new ChattingAdapter(messsageModels, this);
        binding.recyclerView2.setAdapter(chattingAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView2.setLayoutManager(linearLayoutManager);

        final String senderroom = senderId + recieverId;
        final String recieverroom = recieverId + senderId;

        database.getReference().child("chats")
                        .child(senderroom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        messsageModels.clear();
                                        for(DataSnapshot snapshot1:snapshot.getChildren())
                                        {
                                            MesssageModel messsageModel=snapshot1.getValue(MesssageModel.class);
                                            messsageModels.add(messsageModel);
                                            chattingAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.chat.getText().toString();
                final MesssageModel messsageModel = new MesssageModel(senderId, message);
                messsageModel.setTime(new Date().getTime());
                binding.chat.setText("");

                database.getReference().child("chats")
                        .child(senderroom)
                        .push()
                        .setValue(messsageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(recieverroom)
                                        .push()
                                        .setValue(messsageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });
    }
}