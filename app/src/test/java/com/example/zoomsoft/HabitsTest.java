package com.example.zoomsoft;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

public class HabitsTest {
    private Habits mockHabits() {
        return new Habits("Sleeping", "05112021", "I Like Sleep", "Friday", Boolean.TRUE);
    }
    private Habits mockEmptyHabits() {
        return new Habits(" ", " ", " ", " ", Boolean.FALSE);
    }

    @Test
    public void testGetHabitTitle(){
        Habits habit = mockHabits();
        assertEquals("Sleeping",habit.getHabitTitle());
    }

    @Test
    public void testSetHabitTitle(){
        Habits habit = mockEmptyHabits();
        assertEquals(" ",habit.getHabitTitle());
        habit.setHabitTitle("Playing Games");
        assertEquals("Playing Games",habit.getHabitTitle());
    }

    @Test
    public void testGetStartDate(){
        Habits habit = mockHabits();
        assertEquals("05112021",habit.getStartDate());
    }

    @Test
    public void testSetStartDate(){
        Habits habit = mockEmptyHabits();
        assertEquals(" ",habit.getStartDate());
        habit.setStartDate("01012020");
        assertEquals("01012020",habit.getStartDate());
    }

    @Test
    public void testGetHabitReason(){
        Habits habit = mockHabits();
        assertEquals("I Like Sleep",habit.getHabitReason());
    }

    @Test
    public void testSetHabitReason(){
        Habits habit = mockEmptyHabits();
        assertEquals(" ",habit.getHabitReason());
        habit.setHabitReason("Why not");
        assertEquals("Why not",habit.getHabitReason());
    }

    @Test
    public void testGetHabitWeekDay(){
        Habits habit = mockHabits();
        assertEquals("Friday",habit.getHabitWeekDay());
    }

    @Test
    public void testSetHabitWeekDay(){
        Habits habit = mockEmptyHabits();
        assertEquals(" ",habit.getHabitWeekDay());
        habit.setHabitWeekDay("Sunday");
        assertEquals("Sunday",habit.getHabitWeekDay());
    }

    @Test
    public void testGetPrivacy(){
        Habits habit = mockHabits();
        assertTrue(habit.getPrivacy());
    }

    @Test
    public void testSetPrivacy(){
        Habits habit = mockEmptyHabits();
        assertFalse(habit.getPrivacy());
        habit.setPrivacy(Boolean.TRUE);
        assertTrue(habit.getPrivacy());

    }


}
