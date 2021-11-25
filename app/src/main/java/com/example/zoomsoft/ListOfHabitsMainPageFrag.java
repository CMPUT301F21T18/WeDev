/*Displays the User's total list of habits in the first tab
  Has an add button to add new habits into the list (In progress)
  Allows user to click on a habit and view its information (In progress)
  Shows user's progress per habit (In progress)
*/
package com.example.zoomsoft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOfHabitsMainPageFrag extends Fragment {


    private String TAG = "SAMPLE";
    FloatingActionButton addHabitButton;
    private EditText habitTitle;
    private EditText habitReason;
    private DatePicker startDate;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);

        ListView habitList = view.findViewById(R.id.habit_list);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

//        Get list of habits and display it
//        String email = "a@gmail.com";
//        final CollectionReference collectionReference = rootRef.collection("Habits");
//        collectionReference
//                .document(email)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        habitDataList.clear();
//                        DocumentSnapshot document = task.getResult();
//                        //would recommend populating it to the Habits later on:
//                        List<String> arrayListHabit = (List<String>) document.get("HabitsList");
//                        for (String str : arrayListHabit) {
//                            habitDataList.add(new Habits(str));
//                        }
//                        habitAdaptor.notifyDataSetChanged();
//                    }
//                });
        MainPageFirebase mainPageFirebase = new MainPageFirebase();
        mainPageFirebase.getListOfHabits(new MainPageFirebase.MainPageInterface() {
            @Override
            public void getHabitInterface(ArrayList<String> habitArrayList) {
                if(habitArrayList.contains("exists")) habitArrayList.remove("exists");
                ArrayAdapter habitAdaptor = new HabitCustomList(getContext(), habitArrayList);
                habitList.setAdapter(habitAdaptor);
            }
        });

        //OnClickListener for item clicked in listview
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedHabit = (String) habitList.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), HabitInfo.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE + "1", clickedHabit);
                startActivity(intent);
            }
        });

        //OnClickListener for the floating action button to add new entries
        addHabitButton = view.findViewById(R.id.add_habit_button);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddHabit.class);
                startActivity(intent);
            }
        });
        return view;
    }
}