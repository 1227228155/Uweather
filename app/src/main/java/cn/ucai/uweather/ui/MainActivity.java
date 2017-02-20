package cn.ucai.uweather.ui;

import android.os.Bundle;
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
        addFragment();
    }

    private void addFragment() {
        chooseAreaFragment = new ChooseAreaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, chooseAreaFragment).commit();
    }
}
