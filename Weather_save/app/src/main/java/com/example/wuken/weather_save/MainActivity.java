package com.example.wuken.weather_save;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.wuken.weather_save.DbConstants.TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.example.wuken.weather_save.DbConstants.WEATHER;
import static com.example.wuken.weather_save.DbConstants.TEMP;
import static com.example.wuken.weather_save.DbConstants.DATE;
import static com.example.wuken.weather_save.R.drawable.clear;
import static com.example.wuken.weather_save.R.drawable.clearnight;
import static com.example.wuken.weather_save.R.drawable.cloudy;
import static com.example.wuken.weather_save.R.drawable.fog;
import static com.example.wuken.weather_save.R.drawable.freezing;
import static com.example.wuken.weather_save.R.drawable.freezingrain;
import static com.example.wuken.weather_save.R.drawable.freezingsnow;
import static com.example.wuken.weather_save.R.drawable.frost;
import static com.example.wuken.weather_save.R.drawable.hot01;
import static com.example.wuken.weather_save.R.drawable.mostlysunny;
import static com.example.wuken.weather_save.R.drawable.partlycloudy;
import static com.example.wuken.weather_save.R.drawable.partlycloundynight;
import static com.example.wuken.weather_save.R.drawable.rain01;
import static com.example.wuken.weather_save.R.drawable.rain02;
import static com.example.wuken.weather_save.R.drawable.sleet;
import static com.example.wuken.weather_save.R.drawable.snow;
import static com.example.wuken.weather_save.R.drawable.snow01;
import static com.example.wuken.weather_save.R.drawable.thunder;
import static com.example.wuken.weather_save.R.drawable.thunderstorms01;
import static com.example.wuken.weather_save.R.drawable.thunderstorms02;
import static com.example.wuken.weather_save.R.drawable.unknown;
import static com.example.wuken.weather_save.R.drawable.windy;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements ATresult<String[]>{
    String mWeather = "weather"; //不要改成null
    String mTemp = "fat";
    String mDate = "boy";
    Button btn_viewdb;
    Button btn_save;
    TextView txt_weatherview;
    TextView txt_temp;
    TextView txt_time;
    ImageView imageview;

    private DBHelper dbhelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_viewdb = (Button) findViewById(R.id.btn_viewdb);
        btn_save = (Button) findViewById(R.id.btn_save);
        txt_weatherview = (TextView) findViewById(R.id.txt_weatherview);
        txt_temp=(TextView)findViewById(R.id.temp_text);
        txt_time=(TextView)findViewById(R.id.time_text);
        imageview=(ImageView)findViewById(R.id.imageView);


        theReceiver myAyncTask = new theReceiver();
        myAyncTask.weather_result=this;
        myAyncTask.execute();

        dbhelper = new DBHelper(this);

        btn_viewdb.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, activity_viewdb.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        btn_save.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                dataUpdate(mWeather,mTemp,mDate);
            }
        });
    }


    @Override
    public void taskFinish(String[] result) {
        mWeather=result[0];
        mTemp=result[1];
        mDate=result[2];
        Main_Set(mWeather,mTemp,mDate);
        //dataUpdate(mWeather,mTemp,mDate);
       // Log.v("weather之內容",mWeather);
       // Log.v("temp之內容",mTemp);
       // Log.v("date之內容",mDate);
    }


    public void dataUpdate(String weather,String temp,String Date){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(WEATHER,weather);
        values.put(TEMP,temp);
        values.put(DATE,Date);
        db.insert(TABLE_NAME, null, values);

    }

    protected void Main_Set(String weather,String temp,String time){
        txt_weatherview.setText(weather);
        txt_temp.setText(temp);
        txt_time.setText(time);

        /*介面更新請使用這
                temp是溫度
                Date是偵測日期
               如果weather無吻合，幫我注意一下大小寫
               天氣列表https://developer.yahoo.com/weather/documentation.html*/
        switch (weather){
            case "Tornado":
                imageview.setImageResource(windy);
                break;
            case "Rain":
                imageview.setImageResource(rain02);
                break;
            case "Tropical Storm":
                imageview.setImageResource(windy);
                break;
            case "Hurricane":
                imageview.setImageResource(windy);
                break;
            case "Severe Thunderstorms":
                imageview.setImageResource(thunderstorms02);
                break;
            case "Thunderstorms":
                imageview.setImageResource(thunderstorms01);
                break;
            case "Mixed Rain And Snow":
                imageview.setImageResource(snow);
                break;
            case "Mixed Rain And Sleet":
                imageview.setImageResource(sleet);
                break;
            case "Mixed Snow And Sleet":
                imageview.setImageResource(snow01);
                break;
            case "Freezing Drizzle":
                imageview.setImageResource(freezingsnow);
                break;
            case "Drizzle":
                imageview.setImageResource(snow01);
                break;
            case "Freezing Rain":
                imageview.setImageResource(freezingrain);
                break;
            case "Showers":
                imageview.setImageResource(rain01);
                break;
            case "Snow Flurries":
                imageview.setImageResource(snow01);
                break;
            case "Light Snow Showers":
                imageview.setImageResource(snow01);
                break;
            case "Blowing Snow":
                imageview.setImageResource(snow01);
                break;
            case "Snow":
                imageview.setImageResource(snow01);
                break;
            case "Hail":
                imageview.setImageResource(frost);
                break;
            case "Sleet":
                imageview.setImageResource(sleet);
                break;
            case "Dust":
                imageview.setImageResource(unknown);
                break;
            case "Foggy":
                imageview.setImageResource(unknown);
                break;
            case "Haze":
                imageview.setImageResource(fog);
                break;
            case "Smoky":
                imageview.setImageResource(fog);
                break;
            case "Blustery":
                imageview.setImageResource(windy);
                break;
            case "Windy":
                imageview.setImageResource(windy);
                break;
            case "Cold":
                imageview.setImageResource(freezing);
                break;
            case "Cloudy":
                imageview.setImageResource(cloudy);
                break;
            case "Mostly Cloudy (night)":
                imageview.setImageResource(partlycloundynight);
                break;
            case "Mostly Cloudy (day)":
                imageview.setImageResource(partlycloudy);
                break;
            case "Partly cloudy (night)":
                imageview.setImageResource(partlycloundynight);
                break;
            case "Partly cloudy (day)":
                imageview.setImageResource(partlycloudy);
                break;
            case "Clear (night)":
                imageview.setImageResource(clearnight);
                break;
            case "Sunny":
                imageview.setImageResource(clear);
                break;
            case "Fair (night)":
                imageview.setImageResource(partlycloundynight);
                break;
            case "Fair (day)":
                imageview.setImageResource(mostlysunny);
                break;
            case "Mixed rain and hail":
                imageview.setImageResource(rain02);
                break;
            case "Hot":
                imageview.setImageResource(hot01);
                break;
            case "Isolated thunderstorms":
                imageview.setImageResource(thunder);
                break;
            case "Scattered thunderstorms":
                imageview.setImageResource(thunder);
                break;
            case "Scattered showers":
                imageview.setImageResource(snow);
                break;
            case "Heavy snow":
                imageview.setImageResource(snow);
                break;
            case "Scattered snow showers":
                imageview.setImageResource(snow);
                break;
            case "Partly cloudy":
                imageview.setImageResource(partlycloudy);
                break;
            case "Thundershowers":
                break;
            case "Snow showers":
                break;
            case "Isolated thundershowers":
                break;
            case "Not available":
                break;
            default:
                Log.v("天氣怪怪的","It's a bug!");
        }

    }

}

