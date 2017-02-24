package cn.ucai.uweather;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class I {
    //请求省市级的数据接口
    public static final String REQUEST = "http://guolin.tech/api/china/";
    //和风天气的key
    public static final String KEY ="&key=90d2c73f5c75480cb6d21e784965864a";
    //请求天气信息的数据接口
    public static final String WEATHER = "http://guolin.tech/api/weather?cityid=";
    //获取必应每日一图的接口
    public static final String BING_PIC = "http://guolin.tech/api/bing_pic";
    //后台定时更新数据的时间间隔
    public static final  int UPDATE_TIME = 4*60*60*1000;//4个小时的毫秒数
}
