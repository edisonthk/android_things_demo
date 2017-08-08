package com.example.edisonthk.homemagicmirror;

import com.example.edisonthk.homemagicmirror.Train.TrainSchedule;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void trainSchedule_isCorrect() throws Exception {
        TrainSchedule schedule = new TrainSchedule(null);

        Calendar begin = Calendar.getInstance();
        begin.set(2017,2,13,23,30);

        String[] times = schedule.nextTrainTime(begin);

        for(String t: times) {
            System.out.println(t);
        }

    }
}