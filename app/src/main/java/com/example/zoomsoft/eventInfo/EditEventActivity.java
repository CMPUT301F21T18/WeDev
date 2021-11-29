package com.example.zoomsoft.eventInfo;

import static com.example.zoomsoft.MainPageTabs.email;
import static com.example.zoomsoft.eventInfo.EventFragment.CAMERA_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
import java.util.Map;

/**
 * An activity class that Edits any of the parameters of a habit event.
 */
public class EditEventActivity extends AppCompatActivity {
    EditText comment;
    ImageButton good;
    ImageButton cancel;
    Button addLocation;
    private StorageReference storage;
    private String photoPath;
    Button cameraButton;
    boolean isPictureTaken;
    private File file;
    private Uri imageUri;
    private DatePicker datePicker;
    /**
     * Gives functionality to the checkmark button, the delete button, the camera button and the location button. Also
     * implements the date picker.
     * @param savedInstanceState The last saved instance state of the Fragment, or null if this is a freshly created Fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        storage = FirebaseStorage.getInstance().getReference();
        cameraButton = findViewById(R.id.add_picture);
        isPictureTaken = false;
        comment = findViewById(R.id.habit_title_edit_text);
        datePicker = findViewById(R.id.datePicker);
        addLocation = findViewById(R.id.AddLocation);
        good = findViewById(R.id.edit_habit_check);
        cancel = findViewById(R.id.edit_habit_stop);

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                String dateString = year + "-" + month + "-" + day;
                String commentString = comment.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Events");
                DocumentReference documentReference = collectionReference.document(email);
                if(!commentString.isEmpty()) {
                    Map<String,Object> updates = new HashMap<>();
                    updates.put(HabitInfo.clickedHabit + "." + HabitEventDisplay.clickedDate + "." + "comment", commentString);
                    documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.w("TAG", "Deleted from firebase");
                            else
                                Log.w("TAG", "Error deleting document");
                        }
                    });
                }

                HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
                habitEventFirebase.getHabitClickedDetails(new HabitEventFirebase.MyCallBack() {
                    @Override
                    public void getDescription(String s) {

                    }

                    @Override
                    public void getAllDates(List<String> list, List<Boolean> dateList) {

                    }

                    @Override
                    public void getHabitDetails(HashMap<String, Object> map) {
                        Map<String,Object> updates = new HashMap<>();
                        map.put(dateString, map.get(HabitEventDisplay.clickedDate));
                        updates.put(HabitInfo.clickedHabit , map);
                        documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    String prevDate = HabitEventDisplay.clickedDate;
                                    HabitEventDisplay.clickedDate = dateString;
                                    if(!dateString.equals(prevDate)) habitEventFirebase.deleteHabitEvent(HabitEventDisplay.clickedDate);
                                    Log.w("TAG", "Date replaced");
                                }
                                else
                                    Log.w("TAG", "Error replacing date");
                            }
                        });
                    }
                });


                if (isPictureTaken) {
                    uploadFirebase(file.getName(), imageUri);
                }

                finish();
//                getFragmentManager().beginTransaction().detach(EventFragment.this).attach(EventFragment.this).commit();
                Toast.makeText(EditEventActivity.this,
                            "Fields updated accordingly", Toast.LENGTH_LONG).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditEventActivity.this, ViewLocationMap.class);
                startActivity(intent);
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
    }
    /**
     * Creates a new image file
     * @return a file object called image
     * @throws IOException Throws:
     * IllegalArgumentException – If the prefix argument contains fewer than three characters
     * java.io.IOException – If a file could not be created
     * SecurityException – If a security manager exists and its SecurityManager.checkWrite(String) method does not allow a file to be created
     */
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
    /**
     * Activates when a picture is taken.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                file = new File(photoPath);
                //imageView.setImageURI(Uri.fromFile(file));
//                Picasso.get()
//                        .load(Uri.fromFile(file))
//                        .into(imageView);
                imageUri = Uri.fromFile(file);
                isPictureTaken = true;
            }
        }
    }
    /**
     * Uploads an image file to firebase.
     * @param uri The uri of the file on the system.
     */
    public void uploadFirebase(String name, Uri uri) {
        StorageReference photo = storage.child("images/events/" + MainPageTabs.email + HabitInfo.clickedHabit + HabitEventDisplay.clickedDate);
        photo.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Picasso.get().load(uri).fit().centerCrop().into(imageView);
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