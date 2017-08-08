package com.example.edisonthk.homemagicmirror.Weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by edisonthk on 2017/02/09.
 */

public class WeatherWebsite {

    public final String URL = "https://tenki.jp/lite/";

    private final String TAG = "WeatherWebsite";

    private Handler mHandler;
    private Runnable mRunnable;
    private WeatherWebsiteListener mListener;
    private Activity mActivity;

    private Document mDoc;

    public WeatherWebsite() {

    }

    public void setAcitivty(Activity a) {
        this.mActivity = a;
    }

    public void start() {

        final WeatherWebsite mThis = this;

        mHandler = new Handler();
        mRunnable = new Runnable() {

            @Override
            public void run() {

                WeatherHttpRequest request = new WeatherHttpRequest();
                request.setWebsite(mThis);
                request.setActivity(mThis.mActivity);
                request.setSiteLoadedListener(mListener);
                request.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL);

                Log.i(TAG, String.format(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)));

                mHandler.postDelayed(this, 1000 * 60 * 6);
            }
        };

        mHandler.post(mRunnable);
    }

    public void stop() {
        if(mHandler!= null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    public void setHtml(String html) {

        this.mDoc = Jsoup.parse(html);
    }

    public void setOnUpdateWeatherListener(WeatherWebsiteListener listener) {
        this.mListener = listener;
    }

    public Weather getTokyoWeather() {
        // refer to site
        Element ele = this.mDoc.getElementById("forecast-map-entry-13101");

//        String cityName     = this.getTextFromClass(ele, "map_point_name");
        String weather      = this.getTextFromClass(ele, "forecast-image");
        String maxTemp      = this.getTextFromClass(ele, "max-temp");
        String minTemp      = this.getTextFromClass(ele, "min-temp");
        String precip       = this.getTextFromClass(ele, "prob-precip");

        Weather w = new Weather();
        w.setCity("");
        w.setWeather(weather);
        w.setMaxTemperature(maxTemp);
        w.setMinTemperature(minTemp);
        w.setPropPrecipitation(precip);

        return w;
    }

    private String getTextFromClass(Element ele,String className) {
        Elements eles = ele.getElementsByClass(className);
        if(eles.size() <= 0) {
            Log.e(TAG, className + " no element found");
            return null;
        }

        return eles.first().text();
    }
}
