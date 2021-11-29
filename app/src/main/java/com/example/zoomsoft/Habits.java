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

    /**
     * Holds all parameters for Habits
     * @param habitTitle
     * @param startDate
     * @param habitReason
     * @param habitWeekDay
     */
    public Habits(String habitTitle, String startDate, String habitReason, ArrayList<Integer> habitWeekDay) {
        this.habitTitle = habitTitle;
        this.startDate = startDate;
        this.habitReason = habitReason;
        this.habitWeekDay = habitWeekDay;
    }

    /**
     * Holds all parameters for Habits
     * @param habitTitle
     * @param startDate
     * @param habitReason
     * @param habitWeekDay
     * @param privacy
     */
    public Habits(String habitTitle, String startDate, String habitReason, ArrayList<Integer> habitWeekDay, String privacy) {
        this.habitTitle = habitTitle;
        this.startDate = startDate;
        this.habitReason = habitReason;
        this.habitWeekDay = habitWeekDay;
        this.privacy = privacy;
    }

    public String getHabitTitle() {
        return habitTitle;
    }

    public void setHabitTitle(String habitTitle) {
        this.habitTitle = habitTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getHabitReason() {
        return habitReason;
    }

    public void setHabitReason(String habitReason) {
        this.habitReason = habitReason;
    }

    public ArrayList<Integer> getHabitWeekDay() {
        return habitWeekDay;
    }

    public void setHabitWeekDay(ArrayList<Integer> habitWeekDay) {
        this.habitWeekDay = habitWeekDay;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
