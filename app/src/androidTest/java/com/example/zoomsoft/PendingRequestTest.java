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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Test class for Pending Request Activity. All the UI tests are written here. Robotium test framework is
 used.
 UI tested: change in activity from Main to pending req and add friend and verify the data in database and data structure
 */
@RunWith(AndroidJUnit4.class)
public class PendingRequestTest {
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
     * Tests the Add Friend activity, pending request -> delete request and checks if database
     * is updated accordingly at each instance.
     */
    @Test
    public void sendRequestTest(){
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
        solo.clickOnText("Add Friend");
        solo.enterText((EditText) solo.getView(R.id.add_friend_field), "asadtest@gmail.com");
        solo.clickOnButton("Add Friend");
        // changed activity right
        solo.assertCurrentActivity("Wrong Activity", AddFriends.class);


        // back to profile tab
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

        // check in data base if the pending request of current user (ex: asad@gmail.com) is updated
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("Pending Requests")
                .document(MainPageTabs.email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                   DocumentSnapshot document = task.getResult();
                   List<String> group = (List<String>) document.get("pending_requests");
                   assertTrue(group.contains("asadtest@gmail.com"));            // added email exists in db
               }
           });

        // check in data base if the received request of other user is updated
        db.collection("Received Requests")
                .document("asadtest@gmail.com").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        List<String> group = (List<String>) document.get("Received Requests");
                        assertTrue(group.contains(MainPageTabs.email));            // added email exists in db
                    }
                });

        // profile tab
        solo.clickOnText("Pending Requests");
        solo.assertCurrentActivity("Wrong Activity", PendingRequests.class);

        // if email is exists in there
        assertTrue(solo.searchText("asadtest@gmail.com"));
        solo.clickOnText("asadtest@gmail.com");     // delete the pending request
        assertFalse(solo.searchText("asadtest@gmail.com"));

        // check in data base if the pending request of current user (ex: asad@gmail.com) is updated in db
        db.collection("Pending Requests")
                .document(MainPageTabs.email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        List<String> group = (List<String>) document.get("pending_requests");
                        assertFalse(group.contains("asadtest@gmail.com"));            // email does not exists in db
                    }
                });

        // check in data base if the received request of other user is updated
        db.collection("Received Requests")
                .document("asadtest@gmail.com").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        List<String> group = (List<String>) document.get("Received Requests");
                        assertFalse(group.contains(MainPageTabs.email));            //  email doesn't exists in db
                    }
                });


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


