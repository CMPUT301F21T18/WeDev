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

import com.example.zoomsoft.eventInfo.HabitEventFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HabitCustomList extends ArrayAdapter<String> {

    private ArrayList<String> habits;
    ProgressBar progressBar;
    Context context;

    public HabitCustomList(Context context, ArrayList<String> habits){
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
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        String habit = habits.get(position); //Gets exact Class entry as reference for its data
        //Creating references to TextViews
        TextView habitName = view.findViewById(R.id.content_habit_name);
        //Adds the data from the entries onto the TextViews
        habitName.setText(habit);
        progressBar.setProgress(80);

//        HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
//        habitEventFirebase.getAllDates(new HabitEventFirebase.MyCallBack() {
//            @Override
//            public void getDescription(String s) {
//
//            }
//
//            @Override
//            public void getAllDates(List<String> list, List<Boolean> dateList) {
//
//                int totalEvents = list.size();
//                int totalDone = dateList.size();
//                if (totalEvents == 0){
//                    progressBar.setProgress(0);
//                }
//                else{
//                    progressBar.setProgress((totalDone/totalEvents)/100);
//                }
//            }
//
//            @Override
//            public void getHabitDetails(HashMap<String, Object> map) {
//
//            }
//        });

        return view;
    }
}
