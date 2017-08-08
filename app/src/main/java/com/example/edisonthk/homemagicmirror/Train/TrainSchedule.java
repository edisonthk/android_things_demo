package com.example.edisonthk.homemagicmirror.Train;

import android.os.Handler;
import android.transition.TransitionSet;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by edisonthk on 2017/02/12.
 */

public class TrainSchedule {

    private static final String TAG = "TrainSchedule";
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String TIME_FORMAT = "HH:mm";
    private final TextView mMessageView;
    private Handler mHandler;
    private Runnable mRunnable;

    public TrainSchedule(TextView view) {

        mMessageView = view;
    }

    public void start() {
        mHandler = new Handler();
        mRunnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Calendar now = Calendar.getInstance();

                    // 家から駅まで徒歩7分
                    now.add(Calendar.MINUTE, 7);
                    String[] nextTrain = nextTrainTime(now);

                    String message = "Yomiuriland\nNext Train\n";
                    for (String time: nextTrain) {
                        message += time + "\n";
                    }
                    mMessageView.setText(message);
                } catch (ParseException e) {
                    e.printStackTrace();

                    mMessageView.setText("Error! On Train time");
                }
                mHandler.postDelayed(this, 1000 * 60);
            }
        };

        mHandler.post(mRunnable);
    }

    public void stop() {
        if(mHandler!= null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    public String[] nextTrainTime(Calendar begin) throws ParseException{

        Calendar nextday = (Calendar)begin.clone();
        nextday.add(Calendar.DATE, 1);


        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT + TIME_FORMAT);

        String[] schedule = new String[]{};
        if(isWeekend(begin)) {
            schedule = TrainScheduleInWeekends.timetable;
        } else {
            schedule = TrainScheduleInWeekdays.timetable;
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        List<String> nextTime = new ArrayList<String>();


        for (String time: schedule) {
            
            String beginDay = dateFormat.format(begin.getTime());
            Date date = sdf.parse(beginDay +" " + time);

            Calendar scheduleCal = Calendar.getInstance();
            scheduleCal.setTime(date);
            
            if(scheduleCal.after(begin)) {
                nextTime.add(time);
            }

            if(nextTime.size() >= 2) {
                break;
            }
        }

        if(nextTime.size() == 0) {
            for (String time: schedule) {
                String nextdayStr = dateFormat.format(nextday.getTime());
                Date date = sdf.parse(nextdayStr  +" " + time);

                Calendar scheduleCal = Calendar.getInstance();
                scheduleCal.setTime(date);

                if(scheduleCal.after(begin)) {
                    nextTime.add(time);
                }

                if(nextTime.size() >= 2) {
                    break;
                }
            }
        }

        return nextTime.toArray(new String[nextTime.size()]);
    }

    // 祝日が含まれていない（祝日は用実装）
    public boolean isWeekend(Calendar c) {
        int day = c.get(Calendar.DAY_OF_WEEK);

        // saturday 7
        // sunday 1
        return day == 7 || day == 1;
    }
}
