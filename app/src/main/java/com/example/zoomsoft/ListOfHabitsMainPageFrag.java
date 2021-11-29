package com.example.zoomsoft;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Displays a list of all the users habits
 */

public class ListOfHabitsMainPageFrag extends Fragment {

    private String TAG = "SAMPLE";
    ArrayList<String> habitName = new ArrayList<>();
    FloatingActionButton addHabitButton;
    private RecyclerAdaptor.RecyclerViewClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_habits_main_page_fragment, container, false);

        RecyclerView habitList = view.findViewById(R.id.habit_list);

        MainPageFirebase mainPageFirebase = new MainPageFirebase();
        mainPageFirebase.getListOfHabits(new MainPageFirebase.MainPageInterface() {
            @Override
            public void getHabitInterface(ArrayList<String> habitArrayList) {
                if(getActivity() != null) {
                    habitName = habitArrayList;
                    setOnClickListener();
                    RecyclerAdaptor recyclerAdaptor = new RecyclerAdaptor(habitArrayList, listener);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    habitList.setLayoutManager(layoutManager);
                    habitList.setItemAnimator(new DefaultItemAnimator());
                    habitList.setAdapter(recyclerAdaptor);
                }
            }

            @Override
            public void getAllHabitsForToday(ArrayList<String> habitsToday) {

            }
        });

//        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String clickedHabit = (String) habitList.getItemAtPosition(i); //Getting name of habit
//                Intent intent = new Intent(getActivity(), HabitInfo.class); //Adding habit name to intent
//                intent.putExtra(MainActivity.EXTRA_MESSAGE + "1", clickedHabit);
//                startActivity(intent);
//            }
//        });

        addHabitButton = view.findViewById(R.id.add_habit_button);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddHabit.class);
                startActivity(intent);
            }
        });

        mainPageFirebase.rootRef.collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mainPageFirebase.getListOfHabits(new MainPageFirebase.MainPageInterface() {
                    @Override
                    public void getHabitInterface(ArrayList<String> habitArrayList) {
                        if(getActivity() != null) {
                            habitName = habitArrayList;
                            setOnClickListener();
                            RecyclerAdaptor recyclerAdaptor = new RecyclerAdaptor(habitArrayList, listener);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                            habitList.setLayoutManager(layoutManager);
                            habitList.setItemAnimator(new DefaultItemAnimator());
                            habitList.setAdapter(recyclerAdaptor);
                        }
                    }
                    @Override
                    public void getAllHabitsForToday(ArrayList<String> habitsToday) {

                    }
                });
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(habitList);

        return view;
    }

    private void setOnClickListener() {
        listener = new RecyclerAdaptor.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                RecyclerView habitList = view.findViewById(R.id.habit_list);
                String clickedHabit = (String) habitName.get(position); //Getting name of habit
                Intent intent = new Intent(getActivity(), HabitInfo.class); //Adding habit name to intent
                intent.putExtra(MainActivity.EXTRA_MESSAGE + "1", clickedHabit);
                startActivity(intent);
            }
        };
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
            ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(habitName, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}