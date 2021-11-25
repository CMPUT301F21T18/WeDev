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

public class ReceivedRequestsFirebase {

    public String  email = "you@gmail.com";//MainPageTabs.email; //
    Source source = Source.SERVER;
    ListView recievedRequestsListView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    interface ReceivedRequestsInterface {
        void callReceivedRequests(ArrayList<String> requestfriends);
    }

    public ReceivedRequestsFirebase() {
    }

    /**
     * Gets the list of friends for each user.
     * @param receivedRequestsInterface
     */

    public void getReceivedRequests(ReceivedRequestsInterface receivedRequestsInterface){
        final CollectionReference collectionReference = db.collection("Received Requests");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());

                        ArrayList<String>  receivedRequests = (ArrayList<String>) map.get("Received Requests"); //an arraylist of received requests
                        receivedRequestsInterface.callReceivedRequests(receivedRequests);
                    }
                }
            }
        });
    }
}





