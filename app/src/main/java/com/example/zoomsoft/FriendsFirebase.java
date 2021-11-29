package com.example.zoomsoft;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Map;

/**
 * The class that implements firebase for a list of friends.
 */
public class FriendsFirebase {
    public String  email = MainPageTabs.email; //"a@gmail.com"
    Source source = Source.SERVER;
    ListView friendsListView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    interface FriendsInterface{
        void callBackFriends(ArrayList<String> friends );
    }

    public FriendsFirebase() {

    }

    /**
     * Gets the list of friends for each user.
     * @param friendsInterface
     */
    public void getFriend(FriendsInterface friendsInterface){
        final CollectionReference collectionReference = db.collection("Friends");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        ArrayList<String>  friends = (ArrayList<String>) map.get("friends"); //an arraylist of friends
                        friendsInterface.callBackFriends(friends);
                    }
                }
            }
        });

    }
}