package com.example.edisonthk.homemagicmirror.Weather;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Created by edisonthk on 2017/02/09.
 */

public class WeatherHttpRequest extends AsyncTask<String, Void, Void> {

    private final String TAG = "WeatherHTTPRequest";
    private final String REQUEST_CHARACTER_SET = "UTF-8";
    private WeatherWebsite mWebsite;
    private WeatherWebsiteListener mSiteLoadedListener;
    private Activity mActivity;

    @Override
    protected Void doInBackground(String[] params) {

        Log.d(TAG, "begin to doInBackground");
        String html = "";



        try {

//            InputStream is = mActivity.getAssets().open("tenki_jp");

//            mWebsite.setHtml(html);

            URL url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Language", "ja,en-US;q=0.8,en;q=0.6,zh-CN;q=0.4,zh;q=0.2");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            InputStream in = urlConnection.getInputStream();

            html = this.readStream(in);
            if(html.length() == 0) {
                Log.e(TAG, "Fail to retrieve info from internet");
            }

            in.close();

            mWebsite.setHtml(html);

        } catch(Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return null;
    }

    private String readStream(InputStream is) throws Exception{

        InputStreamReader inputStreamReader = new InputStreamReader(is, REQUEST_CHARACTER_SET);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String s;
        StringBuilder sb = new StringBuilder();
        while((s = br.readLine()) != null)
            sb.append(s + "\n");

        return sb.toString();
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void setWebsite(WeatherWebsite wb) {
        this.mWebsite = wb;
    }

    public void setSiteLoadedListener(WeatherWebsiteListener listener) {
        this.mSiteLoadedListener = listener;
    }

    protected void onPostExecute(Void result) {
        Log.i(TAG, "done");
        this.mSiteLoadedListener.onUpdate(this.mWebsite);
    }
}
