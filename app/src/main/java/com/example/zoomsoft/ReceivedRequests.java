package com.example.zoomsoft;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReceivedRequests extends AppCompatActivity {
    ListView receivedRequestsListView;
    FirebaseFirestore db;
    ArrayList<String> receivedRequestsDataList = new ArrayList<>();
    ReceivedRequestsArrayAdapter receivedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received_requests);


        receivedRequestsListView = findViewById(R.id.recieved_requests);
        receivedAdapter = new ReceivedRequestsArrayAdapter(this, receivedRequestsDataList);
        receivedRequestsListView.setAdapter(receivedAdapter);


        ReceivedRequestsFirebase receivedRequestsFirebase = new ReceivedRequestsFirebase();
        receivedRequestsFirebase.getReceivedRequests(receivedRequests -> {
            ReceivedRequestsArrayAdapter receivedRequestsArrayAdapter = new ReceivedRequestsArrayAdapter(getApplicationContext(),receivedRequests);
            ListView receivedRequestsList = findViewById(R.id.recieved_requests);
            receivedRequestsList.setAdapter(receivedRequestsArrayAdapter);

        });


        receivedRequestsListView.setOnItemClickListener((parent, view, position, id) -> {
            // setup of edit menu to show on long-click of entries.
            // motivation: https://www.tutlane.com/tutorial/android/android-popup-menu-with-examples 2021/09/25
            PopupMenu popup = new PopupMenu(getApplicationContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.received_request_menu, popup.getMenu());
            db = db.getInstance();
            Context context = getApplicationContext();


            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.accept_request:
                        // update the friends list of current user
                        String selectedFromList = (String) (receivedRequestsListView.getItemAtPosition(position));
                        DocumentReference documentReference = db.collection("Friends").document(MainPageTabs.email);
                        documentReference.update("friends", FieldValue.arrayUnion(selectedFromList));

                        // update the pending requests list of other user
                        DocumentReference documentRef = db.collection("Pending Requests").document(selectedFromList);
                        documentRef.update("pending_requests", FieldValue.arrayRemove(MainPageTabs.email));

                        // update the received requests list of current user
                        DocumentReference documentRef2 = db.collection("Received Requests").document(MainPageTabs.email);
                        documentRef2.update("Received Requests", FieldValue.arrayRemove(selectedFromList));

                        // toast message
                        Toast.makeText(context,"Request Accepted", Toast.LENGTH_LONG).show();

                        recreate();
                        return true;
                    case R.id.reject_request:
                        String selectedFromList1 = (String) (receivedRequestsListView.getItemAtPosition(position));
                        // update the received request list of current user
                        DocumentReference documentReference1 = db.collection("Received Requests").document(MainPageTabs.email);
                        documentReference1.update("Received Requests", FieldValue.arrayRemove(selectedFromList1));

                        // update the pending requests list of the other user
                        DocumentReference documentRef1 = db.collection("Pending Requests").document(selectedFromList1);
                        documentRef1.update("pending_requests", FieldValue.arrayRemove(MainPageTabs.email));

                        // toast message
                        Toast.makeText(context,"Request Rejected", Toast.LENGTH_LONG).show();

                        recreate();
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });


    }

}

