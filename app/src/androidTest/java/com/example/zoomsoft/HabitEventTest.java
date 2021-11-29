package com.example.zoomsoft;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.eventInfo.AddEventActivity;
import com.example.zoomsoft.eventInfo.EditEventActivity;
import com.example.zoomsoft.eventInfo.HabitInfo;
import com.example.zoomsoft.eventInfo.ViewLocationMap;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for Add/Edit Habit Event Activity . All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class HabitEventTest {

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

        // Goes to event tab before starting each tests
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
    }


    /**
     * Tests the Add/ habit event activity, and checks if date
     * is updated accordingly at each instance
     */
    @Test
    public void addHabitEventTest() {
        // Add habit event test

        // click on add floating button
        FloatingActionButton addFab = (FloatingActionButton) solo.getCurrentActivity().findViewById(R.id.add_event_button);
        solo.clickOnView(addFab);

        // changed activity
        solo.assertCurrentActivity("Wrong Activity", AddEventActivity.class);

        // set the date
        DatePicker datePicker = (DatePicker) solo.getCurrentActivity().findViewById(R.id.add_event_datePicker);
        solo.setDatePicker(datePicker, 2012, 2, 16);

        solo.enterText((EditText) solo.getView(R.id.event_comment_edit_text), "Finished the Event");
        View switcher = (View) solo.getCurrentActivity().findViewById(R.id.done_switch);
        solo.clickOnView(switcher);

        // finish adding
        View doneButton = (View) solo.getCurrentActivity().findViewById(R.id.add_event_confirm_button);
        solo.clickOnView(doneButton);


        // verify if the data exists on screen
        solo.assertCurrentActivity("Wrong Activity", AddEventActivity.class);
        solo.searchText("2012-2-16");
        solo.searchText("Done");

    }



    /**
     * Tests the Edit/ habit event activity, and checks if date
     * is updated accordingly at each instance
     */
    @Test
    public void editHabitEventTest() {


        // edit habit event test
        solo.clickOnText("2012-3-16");
        solo.searchText("Bowling");
        solo.searchText("Finished the Event");
        solo.assertCurrentActivity("Wrong Activity", HabitInfo.class);

        // edit the event
        FloatingActionButton editFab = (FloatingActionButton) solo.getView(R.id.floatingActionButton2);
        solo.clickOnView(editFab);

        // set the date
        solo.assertCurrentActivity("Wrong Activity", EditEventActivity.class);
        DatePicker datePicker1 = (DatePicker) solo.getCurrentActivity().findViewById(R.id.datePicker);
        solo.setDatePicker(datePicker1, 2015, 2, 16);

        // updating location
        solo.enterText((EditText) solo.getView(R.id.habit_title_edit_text), "Finished the Event repeat");
        Button updateLoc = (Button) solo.getView(R.id.AddLocation);
        solo.clickOnView(updateLoc);
        Button selectLoc = (Button) solo.getView(R.id.search_location);
        solo.clickOnView(selectLoc);
        solo.assertCurrentActivity("Wrong Activity", ViewLocationMap.class);
        solo.enterText(0, "canada");
        solo.sleep(2000);
        SearchView searchView = (SearchView) solo.getView(R.id.sv_location);
        solo.clickOnView(searchView);
        solo.sendKey(Solo.ENTER);


        Button okLoc = (Button) solo.getView(R.id.okButton);
        solo.clickOnView(okLoc);

        solo.goBack();
        Button confirm = (Button) solo.getView(R.id.confirm_button);
        solo.clickOnView(confirm);
        solo.assertCurrentActivity("Wrong Activity", EditEventActivity.class);

        View editDoneButton = (View) solo.getCurrentActivity().findViewById(R.id.edit_habit_check);
        solo.clickOnView(editDoneButton);


        // fields are updated n done
        solo.searchText("2015-02-16");
        solo.searchText("Finished the Event repeat");

    }

    /**
     * Tests the delete event button, and checks if date
     * is updated accordingly at each instance
     */
    @Test
    public void deleteHabitEvenTest() {
        // edit habit event test
        solo.clickOnText("2021-7-28");
        solo.searchText("Bowling");

        // edit the event
        FloatingActionButton deleteFab = (FloatingActionButton) solo.getView(R.id.floatingActionButton5);
        solo.clickOnView(deleteFab);

        // shouldn't exists
        solo.searchText("2021-7-28");

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


