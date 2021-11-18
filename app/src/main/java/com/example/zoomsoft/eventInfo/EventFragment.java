package com.example.zoomsoft.eventInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.R;

import java.util.Calendar;
public class EventFragment extends DialogFragment {
    private double longitude;
    private double latitude;
    private String comment;
    private String date;
    //private OnFragmentInteractionListener listener;
    //my interface that includes the inter
//    public interface OnFragmentInteractionListener {
//        void onOkPressed();
//        void onDeletePressed();
//    }

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



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog_fragment, null);
        Button button = view.findViewById(R.id.map_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
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
