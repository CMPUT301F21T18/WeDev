package com.example.zoomsoft;


import android.Manifest;
import android.app.Activity;

import android.widget.EditText;
import android.widget.ImageButton;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

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
 * Test class for Add Habit Activity . All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class AddHabitTest {

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
//
    @Test
    public void addHabitTest() {
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

        solo.clickOnText("List of Habits");
        FloatingActionButton fab = (FloatingActionButton) solo.getCurrentActivity().findViewById(R.id.add_habit_button);

        solo.clickOnView(fab);

        solo.enterText((EditText) solo.getView(R.id.habit_title_edit_text), "Hiking");

        solo.clickOnView(solo.getView(R.id.switch_1));

        ImageButton button = (ImageButton) solo.getCurrentActivity().findViewById(R.id.edit_habit_check);

        solo.enterText((EditText) solo.getView(R.id.habit_reason_edit_text), "I like it");
        solo.clickOnView(button);

        solo.goBack();
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


