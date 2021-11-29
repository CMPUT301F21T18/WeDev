package com.example.zoomsoft;

import android.Manifest;
import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.loginandregister.Login;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for CameraPicture. All the UI tests are written here. Robotium test framework is
 used.
 UI tested: Click on Camera button in edit activity, and takes a picture, waits until it is added, checks firebase?
 */
@RunWith(AndroidJUnit4.class)
public class CameraPictureTest {

    private Solo solo;

    //launches app
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
     * Clicks on Activity in the event page,
     * Tests the MainPage UI goes to Login page and checks the activity and adds the data
     * to the fields and verify change in activity
     */
    @Test
    public void checkCamera(){

        //same code as activehabitinfotest at first

        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // Go to next activity login
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        // enter the data and test
        solo.enterText((EditText) solo.getView(R.id.email), "a@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "1");
        solo.clickOnButton("Login");

        //not sure if this is needed
        // check if activity switched properly
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

        solo.clickLongOnText(("go outside"));

        //not sure how to get to events tab from here, especially if the event is null
        solo.clickOnButton("Camera");
        //not sure what to do after the camera is clicked, i looked up that robotium isnt able to run tests outside the app and i wonder if the camera is considered the phone and not the app itself

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
