package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomsoft.loginandregister.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddFriends extends AppCompatActivity {
    ListView userList;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userDataList;
    Button addFriendButton;
    EditText addUserEditText;
    UserCustomList customList;
    FirebaseFirestore db;
    final String TAG = "Sample";
    private String myUser = "you@gmail.com";
    public static String email = MainPageTabs.email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends_fragment);

        userList = findViewById(R.id.user_list);
        addFriendButton = findViewById(R.id.add_friend_button); //null
        addUserEditText = findViewById(R.id.add_friend_field);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("User");



        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = addUserEditText.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                if (userName.length()>0) {
                    data.put("UserName", userName);
                    collectionReference
                            .document(userName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.getResult().exists()){
                                        Toast.makeText(AddFriends.this, "Follow request sent",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddFriends.this, Profile.class));

                                    }else{
                                        Toast.makeText(AddFriends.this, "This user does not exist in the database",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        userDataList = new ArrayList<User>();
        userAdapter = new UserCustomList(this, userDataList);
        userList.setAdapter(userAdapter);

    }



}