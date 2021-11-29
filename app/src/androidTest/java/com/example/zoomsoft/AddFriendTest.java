package com.example.zoomsoft;

import android.Manifest;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.loginandregister.Login;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for Add Friend Activity. All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class AddFriendTest {
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
     * Tests the add Friend to see if the user exists in the databse
     * and sends friend request
     */
    @Test
    public void addFriendTest(){
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
        solo.clickOnText("Profile");

        // add friend
        solo.clickOnText("Add Friend");
        solo.assertCurrentActivity("Wrong Activity", AddFriends.class);

        solo.enterText((EditText) solo.getView(R.id.add_friend_field), "you@gmail.com");
        solo.clickOnButton("Add Friend");     // email shouldn't exist


        // back to profile tab
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

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




