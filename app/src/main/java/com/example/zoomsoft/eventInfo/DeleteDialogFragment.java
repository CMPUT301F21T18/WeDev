package com.example.zoomsoft.eventInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zoomsoft.R;

public class DeleteDialogFragment extends DialogFragment {

    public DeleteDialogFragment() {
        super();
    }

    public static boolean isDeleted = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.delete_dialog_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Are you sure you want to delete this event?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
                        habitEventFirebase.deleteHabitEvent(HabitEventDisplay.clickedDate);
                        Log.d("TAG", "DELETED");
                    }
                })
                .create();
    }
}
