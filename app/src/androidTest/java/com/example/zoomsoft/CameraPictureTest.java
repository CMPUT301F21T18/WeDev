package com.example.zoomsoft;

import static org.junit.Assert.assertFalse;

import android.Manifest;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.eventInfo.HabitInfo;
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
     * Clicks on Activity in the event page,
     * Tests the MainPage UI goes to Login page and checks the activity and adds the data
     * to the fields and verify change in activity
     */
    @Test
    public void checkCamera(){

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

        solo.clickOnText("Bowling");
        solo.assertCurrentActivity("Wrong Activity", HabitInfo.class);
        solo.clickOnText("EVENT");
        solo.clickOnText("2022-1-28");

        // open camera
        Button openCamera = (Button) solo.getView(R.id.bt_open);
        solo.clickOnView(openCamera);

        // if the current activity is diff than prev. camera opened
        assertFalse(solo.getCurrentActivity().isFinishing());
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
