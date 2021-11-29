package com.example.zoomsoft;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RecyclerAdaptor deals with progressbar as well as the editevent details
 */
public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.MyViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email = MainPageTabs.email;
    Source source = Source.SERVER;
    int totalDone;
    int count;

    private ArrayList<String> habitNames;
    private RecyclerViewClickListener listener;

    /**
     * initializes habitNames and listener
     * @param habitNames
     * @param listener
     */
    public  RecyclerAdaptor(ArrayList<String> habitNames, RecyclerViewClickListener listener){
        this.habitNames = habitNames;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView habit;
        private ProgressBar progressBar;
        /**
         *
         * @param view
         */
        public MyViewHolder(final View view){
            super(view);
            habit = view.findViewById(R.id.content_habit_name);
            progressBar = view.findViewById(R.id.progress_bar);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_habits_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptor.MyViewHolder holder, int position) {
        String name = habitNames.get(position);
        holder.habit.setText(name);

        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        List<String> list = new ArrayList<>();
                        Map<String, Object> map = documentSnapshot.getData();
                        HashMap hashMap = (HashMap) map.get(name);
                        if(hashMap == null) return;
                        //dateList.clear();
                        List<Boolean> dateList = new ArrayList<>();
                        for(String str : (Set<String>) hashMap.keySet()) {
                            if (str.equals("description") || str.equals("reason") || str.equals("days") || str.equals("startDate") || str.equals("status")) continue;
                            list.add(str); //str is a date that the event occurred
                            HashMap hashMap1 = (HashMap) hashMap.get(str);
                            for(String date : (Set<String>) hashMap1.keySet()){
                                if(date.equals("done")) {
                                    dateList.add((Boolean) hashMap1.get("done"));
                                    break;
                                }
                            }
                        }
                        count = 0;
                        for (int i=0; i< dateList.size(); i++){
                            if (dateList.get(i) == true){
                                count++;
                            }
                        }
                        totalDone = dateList.size();
                        holder.progressBar.setMax(totalDone);
                        holder.progressBar.setProgress(count);
                    }
                }
                else {
                    int x = 6;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return habitNames.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }
}
