package com.example.zoomsoft;

import static org.junit.Assert.assertFalse;

import android.Manifest;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.eventInfo.HabitInfo;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Test class for Habit Activity -> ADD/Edit/Delete . All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class HabitTest {

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

        // Goes to habit tab before starting each tests
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
    }


    /**
     * Tests the add habit and checks if data
     *  is updated accordingly at each instance.
     */
    @Test
    public void addHabitTest() {

        solo.clickOnText("List of Habits");
        FloatingActionButton fab = (FloatingActionButton) solo.getCurrentActivity().findViewById(R.id.add_habit_button);
        solo.clickOnView(fab);

        solo.enterText((EditText) solo.getView(R.id.habit_title_edit_text), "Hiking");

        solo.clickOnView(solo.getView(R.id.switch_1));

        ImageButton button = (ImageButton) solo.getCurrentActivity().findViewById(R.id.edit_habit_check);

        solo.enterText((EditText) solo.getView(R.id.habit_reason_edit_text), "I like it");
        solo.clickOnView(button);

        solo.goBack();
        // Habit exists in the list
        solo.searchText("Hiking");

    }

    /**
     * Tests the delete habit and checks if data
     *  is updated accordingly at each instance.
     */
    @Test
    public void deleteHabitTest(){
        // add the habit to be deleted later
        solo.clickOnText("List of Habits");

        solo.clickOnText("Hiking");
        solo.assertCurrentActivity("Wrong Activity", HabitInfo.class);

        FloatingActionButton fabDelete = (FloatingActionButton) solo.getView(R.id.fab_delete);
        solo.clickOnView(fabDelete);

        // Hiking habit shouldn't exist
        solo.sleep(2000);
        assertFalse(solo.searchText("Hiking"));
    }

    /**
     * Tests the edit habit and checks if data
     *  is updated accordingly at each instance.
     */
    @Test
    public void editHabitTest(){
        // add the habit to be deleted later
        solo.clickOnText("List of Habits");
        solo.clickOnText("Sing");

        solo.searchText("Sing");
        FloatingActionButton fabEdit = (FloatingActionButton) solo.getView(R.id.fab_edit);
        solo.clickOnView(fabEdit);

        // edit the habit
        DatePicker datePicker1 = (DatePicker) solo.getCurrentActivity().findViewById(R.id.datePicker);
        solo.setDatePicker(datePicker1, 2015, 2, 16);

        solo.enterText((EditText) solo.getView(R.id.habit_reason_edit_text), "Test reason");
        solo.enterText((EditText) solo.getView(R.id.habit_description_edit_text), "Test description");
        View switcherSunday = (View) solo.getCurrentActivity().findViewById(R.id.switch_1);
        solo.clickOnView(switcherSunday);

        View switcherFriday = (View) solo.getCurrentActivity().findViewById(R.id.switch_6);
        solo.clickOnView(switcherFriday);

        View switcherSaturday = (View) solo.getCurrentActivity().findViewById(R.id.switch_7);
        solo.clickOnView(switcherSaturday);

        // done
        View editDoneButton = (View) solo.getView(R.id.edit_habit_check);
        solo.clickOnView(editDoneButton);

        // check if updated correctly
        solo.searchText("Test reason");
        solo.searchText("Test description");
        solo.searchText("2015-02-16");


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


