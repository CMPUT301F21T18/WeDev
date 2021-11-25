package com.example.zoomsoft.eventInfo;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitInfoDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitInfoDisplay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String email = MainPageTabs.email;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HabitInfoDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitInfoDisplay newInstance(String param1, String param2) {
        HabitInfoDisplay fragment = new HabitInfoDisplay();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Switch mondaySwitch;
    Switch tuesdaySwitch;
    Switch wednesdaySwitch;
    Switch thursdaySwitch;
    Switch fridaySwitch;
    Switch saturdaySwitch;
    Switch sundaySwitch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView textView = view.findViewById(R.id.habit);
        TextView textView2 = view.findViewById(R.id.reason);
        sundaySwitch = view.findViewById(R.id.sunday_switch);
        mondaySwitch = view.findViewById(R.id.monday_switch);
        tuesdaySwitch = view.findViewById(R.id.tuesday_switch);
        wednesdaySwitch = view.findViewById(R.id.wednesday_switch);
        thursdaySwitch = view.findViewById(R.id.thursday_switch);
        fridaySwitch = view.findViewById(R.id.friday_switch);
        saturdaySwitch = view.findViewById(R.id.saturday_switch);
        HabitInfoFirebase habitInfoFirebase = new HabitInfoFirebase(); //replace with clicked habit

        habitInfoFirebase.getDaysSelected(new HabitInfoFirebase.MyCallBack() {
            @Override
            public void getDays(ArrayList<Long> days) {
//                Log.d(days.toString(), );
                for(int i = 0; i < 7; i++) {
                    switch (i) {
                        case 0:
                            if(days.get(i) == 1) {
                                sundaySwitch.toggle();
                                sundaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            sundaySwitch.setEnabled(false);
                            break;
                        case 1:
                            if(days.get(i) == 1) {
                                mondaySwitch.toggle();
                                mondaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            mondaySwitch.setEnabled(false);
                            break;
                        case 2:
                            if(days.get(i) == 1) {
                                tuesdaySwitch.toggle();
                                tuesdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            tuesdaySwitch.setEnabled(false);
                            break;
                        case 3:
                            if(days.get(i) == 1) {
                                wednesdaySwitch.toggle();
                                wednesdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            wednesdaySwitch.setEnabled(false);
                            break;
                        case 4:
                            if(days.get(i) == 1) {
                                thursdaySwitch.toggle();
                                thursdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            thursdaySwitch.setEnabled(false);
                            break;
                        case 5:
                            if(days.get(i) == 1) {
                                fridaySwitch.toggle();
                                fridaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            fridaySwitch.setEnabled(false);
                            break;
                        case 6:
                            if(days.get(i) == 1) {
                                saturdaySwitch.toggle();
                                saturdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                            }
                            saturdaySwitch.setEnabled(false);
                            break;
                    }
                }
            }

            @Override
            public void getReason(String reason) {
                //do nothing yet
            }

            @Override
            public void getStartDate(String startDate) {

            }

            @Override
            public void getStatus(String status) {
                //do nothing
            }
        });

        habitInfoFirebase.getHabitReason(new HabitInfoFirebase.MyCallBack() {
            @Override
            public void getDays(ArrayList<Long> days) {
                //do nothing
            }

            @Override
            public void getReason(String reason) {
                textView2.setText("Reason:" + reason);
            }

            @Override
            public void getStartDate(String startDate) {

            }
            @Override
            public void getStatus(String status) {
                //
            }
        });

        HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
        habitEventFirebase.getAllDates(new HabitEventFirebase.MyCallBack() {
            @Override
            public void getDescription(String s) {

            }

            @Override
            public void getAllDates(List<String> list, List<Boolean> dateList) {
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.simpleProgressBar);
                int totalEvents = list.size();
                int totalDone = dateList.size();
                progressBar.setMax(totalEvents);
                progressBar.setProgress(totalDone);
                TextView textView = view.findViewById(R.id.progress);
                textView.setText("Progress: " + "You have completed " + totalDone + " event(s) out of " + totalEvents);
            }

            @Override
            public void getHabitDetails(HashMap<String, Object> map) {

            }
        });

        habitInfoFirebase.getHabitStartDate(new HabitInfoFirebase.MyCallBack() {
            @Override
            public void getDays(ArrayList<Long> days) {

            }

            @Override
            public void getReason(String reason) {

            }

            @Override
            public void getStartDate(String date) {
                TextView textView1 = view.findViewById(R.id.start_date);
                textView1.setText("Start Date:" + date);
            }

            @Override
            public void getStatus(String status) {

            }
        });

        habitInfoFirebase.getHabitStatus(new HabitInfoFirebase.MyCallBack() {
            @Override
            public void getDays(ArrayList<Long> days) {

            }

            @Override
            public void getReason(String reason) {

            }

            @Override
            public void getStartDate(String date) {

            }

            @Override
            public void getStatus(String status) {
                TextView statusView = view.findViewById(R.id.status);
                statusView.setText("Status: " + status);
            }
        });

        habitEventFirebase.db.collection("Events").document(email).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                habitInfoFirebase.getDaysSelected(new HabitInfoFirebase.MyCallBack() {
                    @Override
                    public void getDays(ArrayList<Long> days) {
//                Log.d(days.toString(), );
                        for(int i = 0; i < 7; i++) {
                            switch (i) {
                                case 0:
                                    if(days.get(i) == 1) {
                                        sundaySwitch.toggle();
                                        sundaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    sundaySwitch.setEnabled(false);
                                    break;
                                case 1:
                                    if(days.get(i) == 1) {
                                        mondaySwitch.toggle();
                                        mondaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    mondaySwitch.setEnabled(false);
                                    break;
                                case 2:
                                    if(days.get(i) == 1) {
                                        tuesdaySwitch.toggle();
                                        tuesdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    tuesdaySwitch.setEnabled(false);
                                    break;
                                case 3:
                                    if(days.get(i) == 1) {
                                        wednesdaySwitch.toggle();
                                        wednesdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    wednesdaySwitch.setEnabled(false);
                                    break;
                                case 4:
                                    if(days.get(i) == 1) {
                                        thursdaySwitch.toggle();
                                        thursdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    thursdaySwitch.setEnabled(false);
                                    break;
                                case 5:
                                    if(days.get(i) == 1) {
                                        fridaySwitch.toggle();
                                        fridaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    fridaySwitch.setEnabled(false);
                                    break;
                                case 6:
                                    if(days.get(i) == 1) {
                                        saturdaySwitch.toggle();
                                        saturdaySwitch.setBackgroundColor(Color.parseColor("#31C52E"));
                                    }
                                    saturdaySwitch.setEnabled(false);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void getReason(String reason) {
                        //do nothing yet
                    }

                    @Override
                    public void getStartDate(String startDate) {

                    }

                    @Override
                    public void getStatus(String status) {
                        //do nothing
                    }
                });

                habitInfoFirebase.getHabitReason(new HabitInfoFirebase.MyCallBack() {
                    @Override
                    public void getDays(ArrayList<Long> days) {
                        //do nothing
                    }

                    @Override
                    public void getReason(String reason) {
                        textView2.setText("Reason:" + reason);
                    }

                    @Override
                    public void getStartDate(String startDate) {

                    }
                    @Override
                    public void getStatus(String status) {
                        //
                    }
                });

                HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
                habitEventFirebase.getAllDates(new HabitEventFirebase.MyCallBack() {
                    @Override
                    public void getDescription(String s) {

                    }

                    @Override
                    public void getAllDates(List<String> list, List<Boolean> dateList) {
                        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.simpleProgressBar);
                        int totalEvents = list.size();
                        int totalDone = dateList.size();
                        progressBar.setMax(totalEvents);
                        progressBar.setProgress(totalDone);
                        TextView textView = view.findViewById(R.id.progress);
                        textView.setText("Progress: " + "You have completed " + totalDone + " event(s) out of " + totalEvents);
                    }

                    @Override
                    public void getHabitDetails(HashMap<String, Object> map) {

                    }
                });

                habitInfoFirebase.getHabitStartDate(new HabitInfoFirebase.MyCallBack() {
                    @Override
                    public void getDays(ArrayList<Long> days) {

                    }

                    @Override
                    public void getReason(String reason) {

                    }

                    @Override
                    public void getStartDate(String date) {
                        TextView textView1 = view.findViewById(R.id.start_date);
                        textView1.setText("Start Date:" + date);
                    }

                    @Override
                    public void getStatus(String status) {

                    }
                });

                habitInfoFirebase.getHabitStatus(new HabitInfoFirebase.MyCallBack() {
                    @Override
                    public void getDays(ArrayList<Long> days) {

                    }

                    @Override
                    public void getReason(String reason) {

                    }

                    @Override
                    public void getStartDate(String date) {

                    }

                    @Override
                    public void getStatus(String status) {
                        TextView statusView = view.findViewById(R.id.status);
                        statusView.setText("Status: " + status);
                    }
                });
            }
        });

        textView.setText("Habit:" + HabitInfo.clickedHabit); //pass in the clicked habit
        return view;
    }
}