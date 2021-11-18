package com.example.zoomsoft;

public class Habits {

    private String habitTitle;
    private String startDate;
    private String habitReason;
    private String habitWeekDay;
    private Boolean privacy; //Public or private status for habits

    public Habits(String habitTitle) {
        this.habitTitle = habitTitle;
    }

    public Habits(String habitTitle, String startDate, String habitReason) {
        this.habitTitle = habitTitle;
        this.startDate = startDate;
        this.habitReason = habitReason;
    }

    public Habits(String habitTitle, String startDate, String habitReason, String habitWeekDay, Boolean privacy) {
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

    public String getHabitWeekDay() {
        return habitWeekDay;
    }

    public void setHabitWeekDay(String habitWeekDay) {
        this.habitWeekDay = habitWeekDay;
    }

    public boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }
}
