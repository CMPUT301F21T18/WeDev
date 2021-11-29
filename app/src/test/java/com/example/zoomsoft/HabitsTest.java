package com.example.zoomsoft;
import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class HabitsTest {
    private Habits mockHabits() {
        ArrayList<Integer> habitWeekday = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            habitWeekday.add(1);
        }
        return new Habits("Sleeping", "05112021", "I Like Sleep", habitWeekday, "private");
    }

    private Habits mockEmptyHabits() {
        return new Habits(" ", " ", " ", null, " ");
    }

    @Test
    public void testGetHabitTitle() {
        Habits habit = mockHabits();
        assertEquals("Sleeping", habit.getHabitTitle());
    }

    @Test
    public void testSetHabitTitle() {
        Habits habit = mockEmptyHabits();
        assertEquals(" ", habit.getHabitTitle());
        habit.setHabitTitle("Playing Games");
        assertEquals("Playing Games", habit.getHabitTitle());
    }

    @Test
    public void testGetStartDate() {
        Habits habit = mockHabits();
        assertEquals("05112021", habit.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        Habits habit = mockEmptyHabits();
        assertEquals(" ", habit.getStartDate());
        habit.setStartDate("01012020");
        assertEquals("01012020", habit.getStartDate());
    }

    @Test
    public void testGetHabitReason() {
        Habits habit = mockHabits();
        assertEquals("I Like Sleep", habit.getHabitReason());
    }

    @Test
    public void testSetHabitReason() {
        Habits habit = mockEmptyHabits();
        assertEquals(" ", habit.getHabitReason());
        habit.setHabitReason("Why not");
        assertEquals("Why not", habit.getHabitReason());
    }

    @Test
    public void testGetHabitWeekDay() {
        Habits habit = mockHabits();
        ArrayList<Integer> habitWeekday = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            habitWeekday.add(1);
        }
        assertEquals(habitWeekday, habit.getHabitWeekDay());
    }

    @Test
    public void testSetHabitWeekDay() {
        Habits habit = mockEmptyHabits();
        assertEquals(null, habit.getHabitWeekDay());
        ArrayList<Integer> habitWeekday1 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            habitWeekday1.add(0);
        }
        habit.setHabitWeekDay(habitWeekday1);
        assertEquals(habitWeekday1, habit.getHabitWeekDay());
    }


    @Test
    public void testGetPrivacy() {
        Habits habit = mockHabits();
        assertEquals("private", habit.getPrivacy());

    }

    @Test
    public void testSetPrivacy() {
        Habits habit = mockEmptyHabits();
        assertEquals(" ", habit.getPrivacy());

        habit.setPrivacy("public");
        assertEquals("public", habit.getPrivacy());

    }
}


