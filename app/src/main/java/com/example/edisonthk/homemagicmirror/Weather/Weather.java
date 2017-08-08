package com.example.edisonthk.homemagicmirror.Weather;

/**
 * Created by edisonthk on 2017/02/09.
 */

public class Weather {

    public static final int SUNNY = 1;
    public static final int CLOUDY = 2;
    public static final int RAIN = 3;
    public static final int SNOW = 4;

    public String city;
    public String weather;
    public String minTemp;
    public String maxTemp;
    public String propPreciptation;

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setPropPrecipitation(String propre) {
        this.propPreciptation = propre;
    }

    public void setMaxTemperature(String maxTemp) {
        this.maxTemp= maxTemp;
    }

    public void setMinTemperature(String minTemp) {
        this.minTemp= minTemp;
    }

    public int getWeather() {
        switch (this.weather) {
            case "晴":
                return SUNNY;
            case "曇":
                return CLOUDY;
            case "曇のち晴":
                return CLOUDY;
            case "曇一時雪":
                return SNOW;
            case "雪":
                return SNOW;
        }

        return 0;
    }
}
