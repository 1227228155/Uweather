package cn.ucai.uweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class  Update{

        @SerializedName("loc")
        public String upodateTime;
    }

}
