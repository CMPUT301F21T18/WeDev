package com.example.zoomsoft;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PendingRequests<dataBase> extends AppCompatActivity{
    ListView pendingRequestsListView;
    ArrayList<String> pendingRequestsDataList = new ArrayList<>();
    PendingArrayAdapter pendingAdapter;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_requests);

        PendingRequestsFirebase pendingRequestsFirebase = new PendingRequestsFirebase();
        pendingRequestsFirebase.getPendingRequests(pendingRequests -> {
            PendingArrayAdapter pendingArrayAdapter = new PendingArrayAdapter(getApplicationContext(),pendingRequests);
            ListView receivedRequestsList = findViewById(R.id.pending_requests);
            receivedRequestsList.setAdapter(pendingArrayAdapter);
        });

        pendingRequestsListView = findViewById(R.id.pending_requests);
        pendingAdapter = new PendingArrayAdapter(this, pendingRequestsDataList);
        pendingRequestsListView.setAdapter(pendingAdapter);
        setUpListViewListener();

    }

    private void setUpListViewListener() {
        pendingRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Context context = getApplicationContext();
                db = db.getInstance();
                Toast.makeText(context,"Request removed", Toast.LENGTH_LONG).show();

                String selectedFromList = (String) (pendingRequestsListView.getItemAtPosition(i));

                // update the current user pending requests
                DocumentReference documentReference = db.collection("Pending Requests").document(MainPageTabs.email);
                Map<String,Object> updates = new HashMap<>();
                documentReference.update("pending_requests", FieldValue.arrayRemove(selectedFromList));

                documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Log.w("TAG", "Deleted from firebase");
                        else
                            Log.w("TAG", "Error deleting document");
                    }
                });

                // update the next user received requests
                DocumentReference documentRef = db.collection("Received Requests").document(selectedFromList);
                Map<String,Object> updates1 = new HashMap<>();
                documentRef.update("Received Requests", FieldValue.arrayRemove(MainPageTabs.email));

                documentRef.update(updates1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Log.w("TAG", "Deleted from firebase");
                        else
                            Log.w("TAG", "Error deleting document");
                    }
                });

                //pendingRequestsDataList.remove(i);
                recreate();
                pendingAdapter.notifyDataSetChanged();
            }
        });
    }
}

