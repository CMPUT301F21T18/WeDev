package com.example.zoomsoft;


import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomsoft.databinding.AddFriendBinding;
import com.example.zoomsoft.loginandregister.User;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;



import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class AddFriends extends AppCompatActivity {


    AddFriendBinding binding;
    ListView userList;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userDataList;
    Button addFriendButton;
    EditText addUserEditText;
    FirebaseFirestore db;

    public static String email = MainPageTabs.email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AddFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userList = findViewById(R.id.user_list);
        addFriendButton = findViewById(R.id.add_friend_button); //null
        addUserEditText = findViewById(R.id.add_friend_field);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("User");
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = addUserEditText.getText().toString();
                if (userName.length() > 0) {
                    collectionReference
                            .document(userName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.getResult().exists()) {
                                        Toast.makeText(AddFriends.this, "Follow request sent", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(AddFriends.this, "This user does not exist in the database", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    // update the pending requests list of current user
                    DocumentReference documentRef = db.collection("Pending Requests").document(MainPageTabs.email);
                    documentRef.update("pending_requests", FieldValue.arrayUnion(userName));

                    // update the received requests list of other user
                    DocumentReference documentRef1 = db.collection("Received Requests").document(userName);
                    documentRef1.update("Received Requests", FieldValue.arrayUnion(MainPageTabs.email));



                }
            }
        });

        userDataList = new ArrayList<>();
        userAdapter = new UserCustomList(AddFriends.this, userDataList);
        userList.setAdapter(userAdapter);
    }
}
