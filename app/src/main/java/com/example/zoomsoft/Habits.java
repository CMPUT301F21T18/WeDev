package com.example.zoomsoft;

import java.util.ArrayList;

/**
 * Stores all the habit information
 */
public class Habits {

    private String habitTitle;
    private String startDate;
    private String habitReason;
    private ArrayList<Integer> habitWeekDay;
    private String privacy; //Public or private status for habits

    public Habits(String habitTitle) {
        this.habitTitle = habitTitle;
    }

    public Habits(String habitTitle, String startDate, String habitReason, ArrayList<Integer> habitWeekDay) {
        this.habitTitle = habitTitle;
        this.startDate = startDate;
        this.habitReason = habitReason;
        this.habitWeekDay = habitWeekDay;
    }

    public Habits(String habitTitle, String startDate, String habitReason, ArrayList<Integer> habitWeekDay, String privacy) {
        this.habitTitle = habitTitle;
        this.startDate = startDate;
        this.habitReason = habitReason;
        this.habitWeekDay = habitWeekDay;
        this.privacy = privacy;
    }
    /**
     * Gets the habit title
     * @return the habit title
     */
    public String getHabitTitle() {
        return habitTitle;
    }
    /**
     * Sets the habit title
     * @param habitTitle name of the new habit
     */
    public void setHabitTitle(String habitTitle) {
        this.habitTitle = habitTitle;
    }
    /**
     * Gets the start date
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * Sets the start date
     * @param startDate name of the new start date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * Gets the habit reason
     * @return the habit reason
     */
    public String getHabitReason() {
        return habitReason;
    }
    /**
     * Sets the habit reason
     * @param habitReason name of the habit reason
     */
    public void setHabitReason(String habitReason) {
        this.habitReason = habitReason;
    }
    /**
     * Gets the days of the week that a habit occurs
     * @return the days of the week that a habit occurs
     */
    public ArrayList<Integer> getHabitWeekDay() {
        return habitWeekDay;
    }
    /**
     * Sets the the days of the week that a habit occurs
     * @param habitWeekDay the days of the week that a habit occurs
     */
    public void setHabitWeekDay(ArrayList<Integer> habitWeekDay) {
        this.habitWeekDay = habitWeekDay;
    }
    /**
     * Gets the privacy/public status
     * @return the privacy/public status
     */
    public String getPrivacy() {
        return privacy;
    }
    /**
     * Sets the privacy/public status
     * @param privacy privacy/public status
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
