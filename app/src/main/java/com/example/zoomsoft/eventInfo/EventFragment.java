package com.example.zoomsoft.eventInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EventFragment extends DialogFragment {
    private double longitude;
    private double latitude;
    private String comment;
    private String date;

    public EventFragment(String longitude, String latitude, String date, String comment) {
        this.latitude = Double.parseDouble(longitude);
        this.longitude = Double.parseDouble(latitude);
        this.comment = comment;
        this.date = date;
    }

    public EventFragment() {super();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        if(context instanceof OnFragmentInteractionListener) {
//            listener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + "must implement onFragmentInteractionListener");
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
    //Habit
    //Description
    private TextView descriptionView;
    private TextView habitView;
    private TextView textView;
    //Date
    private TextView dateView;
    //Comment
    private TextView commentView;
    //Photo
    //Camera
    //Location

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog_fragment, null);
        Button button = view.findViewById(R.id.map_button);
        HabitInfoFirebase habitInfoFirebase = new HabitInfoFirebase("Walk a dog"); //clicked habit

        habitView = view.findViewById(R.id.name);
        textView = view.findViewById(R.id.description);
        dateView = view.findViewById(R.id.date);
        commentView = view.findViewById(R.id.textView9);
        descriptionView = view.findViewById(R.id.description);
        habitView.setText("Walk a dog");//clicked habit

        //clicked date needs to be passed
        HabitEventFirebase habitEventFirebase = new HabitEventFirebase("Walk a dog");//clicked habit
        habitEventFirebase.getHabitClickedDetails(new HabitEventFirebase.MyCallBack() {
            @Override
            public void updateComment(String s) {

            }

            @Override
            public void getAllDates(List<String> list) {

            }

            @Override
            public void getHabitDetails(HashMap<String, Object> map) {
                HashMap hashMap = (HashMap) map.get("09:02:21");
                //get the habit comment
                String comment  = (String) hashMap.get("comment");
                if(comment != null) commentView.setText("Comment:" + comment);
                //get the date
                dateView.setText("Date:" + "09:02:21");
                //get the long and lat
                ArrayList<Double> list = (ArrayList<Double>) hashMap.get("Location");
                //will need to call on viewLocation

                //get the description
                habitEventFirebase.getHabitDescription(new HabitEventFirebase.MyCallBack() {
                    @Override
                    public void updateComment(String s) {
                        descriptionView.setText("Description:" + s);
                    }

                    @Override
                    public void getAllDates(List<String> list) {

                    }

                    @Override
                    public void getHabitDetails(HashMap<String, Object> map) {

                    }
                });
                //=============
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapSearch.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, 53.5232 + " " + 13.5263);
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Dialog Fragment")
                .setNegativeButton("Cancel", null)
                .create();
    }
}
