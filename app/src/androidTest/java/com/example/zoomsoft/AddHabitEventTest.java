package com.example.zoomsoft;

import android.Manifest;
import android.app.Activity;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.eventInfo.AddEventActivity;
import com.example.zoomsoft.eventInfo.HabitInfo;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for Add Habit Event Activity . All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class AddHabitEventTest {

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
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Tests the Add habit event activity, and checks if database
     * is updated accordingly at each instance
     */
    @Test
    public void addHabitEventTest() {
        FirebaseFirestore db;
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
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
        solo.clickOnText("List of Habits");

        // habit
        solo.clickOnText("Bowling");
        solo.assertCurrentActivity("Wrong Activity", HabitInfo.class);

        // profile tab
        solo.clickOnText("EVENT");

        // click on add floating button
        FloatingActionButton addFab = (FloatingActionButton) solo.getCurrentActivity().findViewById(R.id.add_event_button);
        solo.clickOnView(addFab);

        // changed activity
        solo.assertCurrentActivity("Wrong Activity", AddEventActivity.class);

        // set the date
        DatePicker datePicker = (DatePicker) solo.getCurrentActivity().findViewById(R.id.add_event_datePicker);
        solo.setDatePicker(datePicker, 2012, 2, 16);

        solo.enterText((EditText) solo.getView(R.id.event_comment_edit_text), "Finished the Event");




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


