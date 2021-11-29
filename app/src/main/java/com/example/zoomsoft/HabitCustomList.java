//For creating and accessing circular progress bar
//https://www.youtube.com/watch?v=YsHHXg1vbcc&t=791s&ab_channel=CodinginFlow
package com.example.zoomsoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zoomsoft.eventInfo.HabitEventFirebase;
import com.example.zoomsoft.eventInfo.HabitInfoDisplay;
import com.example.zoomsoft.eventInfo.HabitInfoFirebase;
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
 * Class that controls the progress bars for consistency in following habits.
 */
public class HabitCustomList extends ArrayAdapter<String> {

    private ArrayList<String> habits;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email = MainPageTabs.email;
    Source source = Source.SERVER;
    int totalDone;
    int count;

    public HabitCustomList(Context context, ArrayList<String> habits){
        super(context,0, habits);
        this.habits = habits;
        this.context = context;
    }

    /**
     * Gets the view that displays the habit custom list adapter's data at specified position.
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_of_habits_content, parent,false);
        }
        ProgressBar progressBar;
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        String habit = habits.get(position); //Gets exact Class entry as reference for its data
        //Creating references to TextViews
        TextView habitName = view.findViewById(R.id.content_habit_name);
        //Adds the data from the entries onto the TextViews
        habitName.setText(habit);

        //List<Boolean> dateList = new ArrayList<>();
        //List<String> list = new ArrayList<>();

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
                        HashMap hashMap = (HashMap) map.get(habit);
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
                        progressBar.setMax(totalDone);
                        progressBar.setProgress(count);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });

        return view;
    }
}
