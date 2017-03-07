package cn.ucai.uweather.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import butterknife.ButterKnife;
import cn.ucai.uweather.R;

public class MainActivity extends FragmentActivity {
    ChooseAreaFragment chooseAreaFragment;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gotoActivity();
    }

    private void gotoActivity() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("weather_id",null)!=null){
            Log.e(TAG, preferences.getString("weather_id", null));
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            this.finish();
        }else {
             Amap();

        }
    }



    private void Amap() {
        chooseAreaFragment = new ChooseAreaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, chooseAreaFragment).commit();

    }
}
