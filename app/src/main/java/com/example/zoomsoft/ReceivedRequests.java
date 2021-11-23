package com.example.zoomsoft;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ReceivedRequests<dataBase> extends AppCompatActivity{
    ListView recievedRequestsListView;
    ArrayList<String> recievedRequestsDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received_requests);


        ReceivedRequestsFirebase receivedRequestsFirebase = new ReceivedRequestsFirebase();
        receivedRequestsFirebase.getReceivedRequests(receivedRequests -> {
            ReceivedRequestsArrayAdapter receivedRequestsArrayAdapter = new ReceivedRequestsArrayAdapter(getApplicationContext(),receivedRequests);
            ListView receivedRequestsList = findViewById(R.id.recieved_requests);
            receivedRequestsList.setAdapter(receivedRequestsArrayAdapter);
        });
    }
}

