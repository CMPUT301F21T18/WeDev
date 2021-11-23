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
import com.google.firebase.firestore.CollectionReference;
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
        pendingRequestsDataList = new ArrayList<>();
        pendingAdapter = new PendingArrayAdapter(this, pendingRequestsDataList);
        pendingRequestsListView.setAdapter(pendingAdapter);


        setUpListViewListener();

    }

    private void setUpListViewListener() {
        pendingRequestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context,"Request removed", Toast.LENGTH_LONG).show();

                db = FirebaseFirestore.getInstance();
                String selectedFromList = (String) (pendingRequestsListView.getItemAtPosition(i));
                Log.d("123LOP", selectedFromList);

                final CollectionReference collectionReference = db.collection("Pending Requests");
                DocumentReference documentReference = collectionReference.document(MainPageTabs.email);
                Log.d("123LP", MainPageTabs.email);

                Map<String,Object> updates = new HashMap<>();
                updates.put(selectedFromList, FieldValue.delete());
                Log.d("123LOP", selectedFromList);
                documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Log.w("TAG", "Deleted from firebase");
                        else
                            Log.w("TAG", "Error deleting document");
                    }
                });

                pendingRequestsDataList.remove(i);
                pendingAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}

