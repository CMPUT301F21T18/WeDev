package com.example.zoomsoft;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.Manifest;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.loginandregister.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Test class for received Request Activity. All the UI tests are written here. Robotium test framework is
 used.
 UI tested: change in activity from Main to received req and verify the data in database and data structure
 */
@RunWith(AndroidJUnit4.class)
public class ReceivedRequestTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    // grant permission
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }



    /**
     * Tests the Received activity, received request -> accept/reject request and checks if database
     * is updated accordingly at each instance and check View friends
     */
    @Test
    public void receivedRequestTest(){
        FirebaseFirestore db;
        //Asserts that the current activity is the MainActivity. Otherwise, show
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // Go to next activity login
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        // enter the data and test
        solo.enterText((EditText) solo.getView(R.id.email), "asad@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "123456");
        solo.clickOnButton("Login");

        // check if activity switched properly
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

        // profile tab
        solo.clickOnText("Profile");

        // add friend
        solo.clickOnText("Received Requests");
        solo.assertCurrentActivity("Wrong Activity", ReceivedRequests.class);

        assertFalse(solo.searchText("asadtest@gmail.com"));     // email shouldn't exist

        // back to profile tab
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

        // add the testing email to db
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Received Requests").document(MainPageTabs.email);
        documentReference.update("Received Requests", FieldValue.arrayUnion("asadtest@gmail.com"));
        // second instance email that we will reject later
        documentReference.update("Received Requests", FieldValue.arrayUnion("asadtest1@gmail.com"));

        solo.clickOnText("Received Requests");
        solo.assertCurrentActivity("Wrong Activity", ReceivedRequests.class);

        assertTrue(solo.searchText("asadtest@gmail.com"));     // email should exist
        assertTrue(solo.searchText("asadtest1@gmail.com"));     // email should exist


        // select second email n reject it
        solo.clickOnText("asadtest1@gmail.com");
        solo.clickOnText("Decline Request");

        assertFalse(solo.searchText("asadtest1@gmail.com"));     // email shouldn't exist in received request page

        // check in data base if the received request of current user (ex: asad@gmail.com) is updated
        db.collection("Received Requests")
                .document(MainPageTabs.email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        List<String> group = (List<String>) document.get("Received Requests");
                        assertFalse(group.contains("asadtest1@gmail.com"));            // email shoudln't exists in db
                    }
                });

        // select first email n accept it
        solo.clickOnText("asadtest@gmail.com");
        solo.clickOnText("Accept Request");

        assertFalse(solo.searchText("asadtest@gmail.com"));  // email shouldn't exist in received request page

        // check if friend list of current user is updated and has asadtest@gmail.com
        db.collection("Friends")
                .document(MainPageTabs.email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        List<String> group = (List<String>) document.get("friends");
                        assertTrue(group.contains("asadtest@gmail.com"));     // email should exists in db
                    }
                });

        // back to profile tab
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

        solo.clickOnText("View Friends");
        solo.assertCurrentActivity("Wrong Activity", ViewFriend.class);

        assertTrue(solo.searchText("asadtest@gmail.com"));  // email should exist in view friend page
        assertFalse(solo.searchText("asadtest1@gmail.com"));  // email shouldn't exist in view friend page



    }

    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}


