package com.example.edisonthk.homemagicmirror;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edisonthk.homemagicmirror.Train.TrainSchedule;
import com.example.edisonthk.homemagicmirror.Weather.Weather;
import com.example.edisonthk.homemagicmirror.Weather.WeatherWebsite;
import com.example.edisonthk.homemagicmirror.Weather.WeatherWebsiteListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TrainSchedule mSchedule;
    private WeatherWebsite mWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final TextView mRainingView = (TextView)findViewById(R.id.raining);
        final TextView mTempView = (TextView)findViewById(R.id.temperature);
        final ImageView mWeather = (ImageView) findViewById(R.id.weather);
        final TextView mTrainView = (TextView)findViewById(R.id.train_info);
        mSchedule = new TrainSchedule(mTrainView);

//        final TextView mSystemView = (TextView) findViewById(R.id.system_info);
//        mSystemView.setText(Utils.getLocalIpAddress());

        mWebsite = new WeatherWebsite();
        mWebsite.setAcitivty(this);
        mWebsite.setOnUpdateWeatherListener(new WeatherWebsiteListener() {
            @Override
            public void onUpdate(WeatherWebsite website) {

                Weather weather = website.getTokyoWeather();

                switch (weather.getWeather()) {
                    case Weather.SUNNY:
                        mWeather.setBackgroundResource(R.drawable.ic_weather_sunny);
                        break;
                    case Weather.CLOUDY:
                        mWeather.setBackgroundResource(R.drawable.ic_weather_cloudy);
                        break;
                    case Weather.RAIN:
                        mWeather.setBackgroundResource(R.drawable.ic_weather_rain);
                        break;
                }

                mRainingView.setText(weather.propPreciptation);
                mTempView.setText(weather.maxTemp + "°C / " + weather.minTemp + "°C");

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("A","start");
        mSchedule.start();
        mWebsite.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        mSchedule.stop();
        mWebsite.stop();
    }

}
