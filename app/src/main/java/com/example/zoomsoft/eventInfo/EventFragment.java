package com.example.zoomsoft.eventInfo;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EventFragment extends DialogFragment {
    private double longitude;
    private double latitude;
    private String comment;
    private String date;
    private String photoPath;
    public static final int CAMERA_REQUEST_CODE = 102;

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
    //Photo
    private StorageReference storage;
    //Camera
    //Location

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog_fragment, null);
        Button button = view.findViewById(R.id.map_button);
        Button cameraButton = view.findViewById(R.id.bt_open);
        imageView = view.findViewById(R.id.camera_pic);
        storage = FirebaseStorage.getInstance().getReference();

        habitView = view.findViewById(R.id.name);
        textView = view.findViewById(R.id.description);
        dateView = view.findViewById(R.id.date);
        commentView = view.findViewById(R.id.textView9);
        descriptionView = view.findViewById(R.id.description);
        habitView.setText("Habit:" + HabitInfo.clickedHabit);//clicked habit


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
                            intent.putExtra("isSearching", "false");
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(view.getContext(),
                                "com.example.zoomsoft.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                    }
                }
            }
        });

        FloatingActionButton delete = view.findViewById(R.id.floatingActionButton5);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
                habitEventFirebase.deleteHabitEvent(HabitEventDisplay.clickedDate);
                getActivity().getFragmentManager().popBackStack();
            }
        });
        FloatingActionButton edit = view.findViewById(R.id.floatingActionButton2);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEventActivity editEventActivity = new EditEventActivity();
                Intent intent = new Intent(getContext(), EditEventActivity.class);
                startActivity(intent);
            }
        });


        //snapshot
        habitEventFirebase.db.collection("Events").document(MainPageTabs.email).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
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
                                    intent.putExtra("isSearching", "false");
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Events Recorded On:" + HabitEventDisplay.clickedDate)
                .create();
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                File file = new File(photoPath);
                //imageView.setImageURI(Uri.fromFile(file));
//                Picasso.get()
//                        .load(Uri.fromFile(file))
//                        .into(imageView);
                Uri contUri = Uri.fromFile(file);
                uploadFirebase(file.getName(), contUri);
            }
        }
    }
    private void uploadFirebase(String name, Uri uri) {
        StorageReference photo = storage.child("images/events/" + name);
        photo.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(imageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Error messages
            }
        });
    }
}
