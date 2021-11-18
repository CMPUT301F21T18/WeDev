/*Custom adapter to display habits in a list

 */
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HabitCustomList extends ArrayAdapter<Habits> {

    private ArrayList<Habits> habits;
    Context context;

    public HabitCustomList(Context context, ArrayList<Habits> habits){
        super(context,0, habits);
        this.habits = habits;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_of_habits_content, parent,false);
        }
        Habits habit = habits.get(position); //Gets exact Class entry as reference for its data
        //Creating references to TextViews
        TextView habitName = view.findViewById(R.id.content_habit_name);

        //Adds the data from the entries onto the TextViews
        habitName.setText(habit.getHabitTitle());

        //Get access to list of habit events and calculate progress for progress bar
//        String email = "a@gmail.com";
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
//        final CollectionReference collectionReference2 = rootRef.collection("Events");
//        collectionReference2
//                .document(email)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        //habitDataList.clear();
//                        DocumentSnapshot document = task.getResult();
//                        //change according to getting done or not done status of events
//                        List<String> arrayListHabit = (List<String>) document.get("HabitsList");
//                        int completedEvents = 0;
//                        for (String str : arrayListHabit) {
//                            //habitDataList.add(new Habits(str));
//                            if (str == "done"){
//                                completedEvents++;
//                            }
//                        }
//                        progressBar.setProgress((completedEvents/arrayListHabit.size())*100);
//                        //habitAdaptor.notifyDataSetChanged();
//                    }
//                });
        return view;
    }

}
