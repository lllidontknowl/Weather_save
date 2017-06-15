package com.example.wuken.weather_save;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wuken on 2017/6/5.
 */
public class theReceiver extends AsyncTask<String,Void,String[]> {


    public ATresult<String[]> weather_result = null;

    @Override
    protected void onPostExecute(String[] strings) {
        this.weather_result.taskFinish(strings);
        super.onPostExecute(strings);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        JSONObject json_tmp = null;
        String weather[]=new String[3];
        try{
            url = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20%20woeid%3D2306180%20and%20u%3D%27c%27&format=json");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.connect();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                Log.e("連線結果","exceptions"+"OK");
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String data;
                StringBuffer mbuffer = new StringBuffer();
                while ((data = br.readLine()) != null) {
                    mbuffer.append(data);
                    Log.v("data內容",data);
                }
                is.close();
                br.close();
                JSONObject weather_result = new JSONObject(mbuffer.toString());
                String string_tmp = new JSONObject((weather_result).getString("query")).getString("results");
                json_tmp = new JSONObject(string_tmp);
                string_tmp = new JSONObject((json_tmp).getString("channel")).getString("item");
                json_tmp = new JSONObject(string_tmp);
                weather[0]= (new JSONObject((json_tmp).getString("condition")).getString("text"));
                weather[1] = (new JSONObject((json_tmp).getString("condition")).getString("temp"));
                weather[2] = (new JSONObject((json_tmp).getString("condition")).getString("date"));
                return weather;

            }else{
                Log.e("連線結果","exceptions"+"NOT OK");
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("連線結果","exceptions"+e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
