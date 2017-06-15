package com.example.wuken.weather_save;

/**
 * Created by wuken on 2017/6/5.
 */
public interface ATresult<T extends Object>
{
    // T是執行結果的物件型態

    public void taskFinish( T result );
}
