package cn.ucai.uweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class Weather {
    public String status;
    public Basic basic;
    public Suggestion suggestion;
    public Now now;
    public AQI aqi;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
