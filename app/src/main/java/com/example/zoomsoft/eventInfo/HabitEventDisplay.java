package com.example.zoomsoft.eventInfo;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitEventDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitEventDisplay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String email = MainPageTabs.email;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HabitEventDisplay() {
        // Required empty public constructor
    }

    public static String clickedDate;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitEventDisplay newInstance(String param1, String param2) {
        HabitEventDisplay fragment = new HabitEventDisplay();
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
    public static Boolean isDone;
    TextView habitNameTextView;
    TextView descriptionTextView;
    ListView listView;
    ArrayAdapter<String> dateAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        habitNameTextView = view.findViewById(R.id.name);
        descriptionTextView = view.findViewById(R.id.description);
        listView = view.findViewById(R.id.listView);
        //Update the habit
        HabitEventFirebase habitEventFirebase = new HabitEventFirebase(); //will replace with the clickedHabit
        habitEventFirebase.getHabitDescription(new HabitEventFirebase.MyCallBack() {
            @Override
            public void getDescription(String s) {
                String description = s;
                habitNameTextView.setText("Habit:" + HabitInfo.clickedHabit);
                descriptionTextView.setText("Description:"+ description);
            }
            @Override
            public void getAllDates(List<String> list) {
                //
            }
            @Override
            public void getHabitDetails(HashMap<String, Object> map) {

            }
        });

        habitEventFirebase.getAllDates(new HabitEventFirebase.MyCallBack() {
            String description;
            ArrayList<String> dateList;
            HashMap<String, Object> map;
            @Override
            public void getDescription(String s) {
                //do nothing
            }

            @Override
            public void getAllDates(List<String> list) {
                dateList = new ArrayList<>(list);
                dateAdapter = new DateCustomListAdapter(getActivity(), dateList);
                listView.setAdapter(dateAdapter);
            }

            @Override
            public void getHabitDetails(HashMap<String,Object> map) {

            }
        });

        habitEventFirebase.getHabitClickedDetails(new HabitEventFirebase.MyCallBack() {
            List<String> dateList;
            @Override
            public void getDescription(String s) {

            }

            @Override
            public void getAllDates(List<String> list) {
                this.dateList = list;
            }

            @Override
            public void getHabitDetails(HashMap<String, Object> map) {
                isDone = (Boolean) map.get("done");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        EventFragment eventFragment = new EventFragment();
                        clickedDate = (String) listView.getItemAtPosition(i);
                        eventFragment.show(getActivity().getSupportFragmentManager(),"Fragment");
                    }
                });
            }
        });
        return view;
    }
}