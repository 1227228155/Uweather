package cn.ucai.uweather.gson;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class AQI {
    public AQICity city;
    public class  AQICity{
        public String aqi;
        public String pm25;
        public String qlty;
    }
}
