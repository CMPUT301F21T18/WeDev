package com.example.zoomsoft.eventInfo;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;

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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EventFragment extends DialogFragment {
    private double longitude;
    private double latitude;
    private String comment;
    private String date;
    private String photoPath;
    private String firePath = "images/events/" + MainPageTabs.email + HabitInfo.clickedHabit;
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
    private Button SelectImage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog ProgressDialog;
    //Photo
    private StorageReference storage;
    private StorageReference refer;
    //Camera
    //Location
    /* @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.EventFragment);
    }*/

    @Override
    public void onStart() {
        super.onStart();
        receiveImage(storage);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog_fragment, null);
        Button button = view.findViewById(R.id.map_button);
        storage = FirebaseStorage.getInstance().getReference();


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
                            if (getContext() != null){
                                Intent intent = new Intent(getContext(), MapsActivity.class);
                                intent.putExtra(MainActivity.EXTRA_MESSAGE, locationArray[0] + " " + locationArray[1]);
                                intent.putExtra("isSearching", "false");
                                startActivity(intent);
                            }
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
                Toast.makeText(getContext(), "Event Deleted", Toast.LENGTH_LONG).show();
                Objects.requireNonNull(getDialog()).dismiss();
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
                        if(map == null) return;
                        HashMap hashMap = (HashMap) map.get(HabitEventDisplay.clickedDate);
                        if(hashMap == null) return; //if we deleted
                        Log.d("NKINGSKE", hashMap.toString());
                        //get the habit comment
                        String comment  = (String) hashMap.get("comment");
                        commentView.setText("Comment:" + comment);
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
                .setTitle("Events Recorded")
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
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            File file = new File(photoPath);
            //imageView.setImageURI(Uri.fromFile(file));
//                Picasso.get()
//                        .load(Uri.fromFile(file))
//                        .into(imageView);
            Uri contUri = Uri.fromFile(file);
            uploadFirebase(file.getName(), contUri);
        }
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
//            ProgressDialog.setMessage("Uploading...");
//            ProgressDialog.show();
            Uri galUri = data.getData();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "." + getFileExtension(galUri);
            uploadFirebase(imageFileName, galUri);
//            StorageReference filepath = storage.child("Photos").child(uri.getLastPathSegment());
//            File galleryFile = new File(photoPath);
//            uploadFirebase(galleryFile.getName(), uri);
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    //Toast.makeText(EventFragment.this, "upload done", Toast.LENGTH_LONG.show()); //error
//                }
//            });
        }//can add failure too
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cont = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cont.getType(uri));
    }

    public void uploadFirebase(String name, Uri uri) {
        StorageReference photo = storage.child(firePath);
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

    public void receiveImage(StorageReference storage) {
        refer = storage.child(firePath);
        refer.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //when no picture is made
            }
        });
    }
}
//=======
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //also crossed
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
//            ProgressDialog.setMessage("Uploading...");
//            ProgressDialog.show();
//            Uri uri = data.getData();
//            StorageReference filepath = Storage.child("Photos").child(uri.getLastPathSegment());
//            /*filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(EventFragment.this, "upload done", Toast.LENGTH_LONG.show()); //error
//                }*/
//            };//can add failure too
//        }
//    }
//
//>>>>>>> Sebastian
