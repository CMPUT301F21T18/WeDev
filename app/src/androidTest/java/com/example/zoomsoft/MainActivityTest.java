package com.example.zoomsoft;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used.
 UI tested: change in activity from Main to login and registration
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

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
     * Tests the MainPage UI goes to Login page and checks the activity
     */
    @Test
    public void checkChangeActivityToLogin(){
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // Go to next activity showActivity
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", login.class);
    }

    /**
     * Tests the MainPage UI goes to Registration page and checks the activity
     */
    @Test
    public void checkChangeActivityToRegistration(){
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // Go to next activity showActivity
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", register.class);
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


