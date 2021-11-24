package com.example.zoomsoft;
import android.util.Log;
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

public class PendingRequestsFirebase {

    public String  email = MainPageTabs.email; //"a@gmail.com"
    Source source = Source.SERVER;
    ListView pendingRequestsListView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    interface PendingRequestsInterface {
        void callPendingRequests(ArrayList<String> pendingfriends);
    }

    public PendingRequestsFirebase() {
    }

    /**
     * Gets the list of friends for each user.
     * @param pendingRequestsInterface
     */

    public void getPendingRequests(PendingRequestsInterface pendingRequestsInterface){
        final CollectionReference collectionReference = db.collection("Pending Requests");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        ArrayList<String>  pendingRequests = (ArrayList<String>) map.get("pending_requests"); //an arraylist of pending_requests
                        pendingRequestsInterface.callPendingRequests(pendingRequests);
                    }
                }
            }
        });
    }
}