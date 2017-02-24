package cn.ucai.uweather.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import butterknife.ButterKnife;
import cn.ucai.uweather.R;

public class MainActivity extends FragmentActivity {
    ChooseAreaFragment chooseAreaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("bing_pic",null)!=null){
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            this.finish();
        }else {
            addFragment();
        }
    }

    private void addFragment() {
        chooseAreaFragment = new ChooseAreaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, chooseAreaFragment).commit();
    }
}
