package com.example.zoomsoft.eventInfo;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.R;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
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
    ImageView imageView;
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
    private Button SelectImage;
    private StorageReference Storage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog ProgressDialog;
    //Photo
    //Camera
    //Location
    /* @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.EventFragment);
    }*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog_fragment, null);
        if(DeleteDialogFragment.isDeleted) getActivity().getFragmentManager().popBackStack();
        Button button = view.findViewById(R.id.map_button);

        Storage = FirebaseStorage.getInstance().getReference();
        SelectImage = (Button) view.findViewById(R.id.bt_gallery);
        //ProgressDialog = new ProgressDialog(this); this is not working either
        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,GALLERY_INTENT); // need to check here


            }
        });

        Button cameraButton = view.findViewById(R.id.bt_open);
        //Button
        imageView = view.findViewById(R.id.camera_pic);
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Bitmap captureImage = (Bitmap) result.getData().getExtras().get("data");
                    imageView.setImageBitmap(captureImage);
                }
            }
        });
        habitView = view.findViewById(R.id.name);
        textView = view.findViewById(R.id.description);
        dateView = view.findViewById(R.id.date);
        commentView = view.findViewById(R.id.textView9);
        descriptionView = view.findViewById(R.id.description);
        habitView.setText(HabitInfo.clickedHabit);//clicked habit


        //clicked date needs to be passed
        HabitEventFirebase habitEventFirebase = new HabitEventFirebase();//clicked habit
        habitEventFirebase.getHabitClickedDetails(new HabitEventFirebase.MyCallBack() {
            @Override
            public void getDescription(String s) {
                descriptionView.setText("Description:" + s);
            }
            @Override
            public void getAllDates(List<String> list, List<Boolean> list2) {

            }
            @Override
            public void getHabitDetails(HashMap<String, Object> map) {
                HashMap hashMap = (HashMap) map.get(HabitEventDisplay.clickedDate);
                //get the habit comment
                String comment  = (String) hashMap.get("comment");
                if(comment != null) commentView.setText("Comment:" + comment);
                //get the date
                dateView.setText("Date:" + HabitEventDisplay.clickedDate);
                //get the long and lat
                ArrayList<String> list = (ArrayList<String>) hashMap.get("location");
                //will need to call on viewLocation
                //=============
                Button button1 = view.findViewById(R.id.map_button);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] locationArray = new String[2];
                        locationArray[0] = list.get(0);
                        locationArray[1] = list.get(1);
                        if(locationArray[0].equals("N") && locationArray[1].equals("N")) {
                            //set Toast, no location was chosen
                            Toast.makeText(getContext(),
                                    "No Location has been added, click on edit to add", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent(getContext(), MapsActivity.class);
                            intent.putExtra(MainActivity.EXTRA_MESSAGE, locationArray[0] + " " + locationArray[1]);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            }
        });
        
        FloatingActionButton delete = view.findViewById(R.id.floatingActionButton5);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
                habitEventFirebase.deleteHabitEvent(HabitEventDisplay.clickedDate);
                getParentFragmentManager().beginTransaction().remove(EventFragment.this).commit();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Events Recorded On:" + HabitEventDisplay.clickedDate)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //also crossed
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            ProgressDialog.setMessage("Uploading...");
            ProgressDialog.show();
            Uri uri = data.getData();
            StorageReference filepath = Storage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(EventFragment.this, "upload done", Toast.LENGTH_LONG.show()); //error
                }
            });//can add failure too
        }
    }
}
