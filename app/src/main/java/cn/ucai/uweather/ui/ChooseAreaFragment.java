package cn.ucai.uweather.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.uweather.I;
import cn.ucai.uweather.R;
import cn.ucai.uweather.db.City;
import cn.ucai.uweather.db.County;
import cn.ucai.uweather.db.Province;
import cn.ucai.uweather.utils.HttpUtil;
import cn.ucai.uweather.utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseAreaFragment extends Fragment {
    private static final String TAG = "ChooseAreaFragment";
    private final String KEY ="&key=90d2c73f5c75480cb6d21e784965864a";
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    @BindView(R.id.back_button)
    ImageView backButton;

    private ProgressDialog progressDialog;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> dataList = new ArrayList<>();

    //省列表
    private List<Province> provinceList;
    //市列表
    private List<City> cityList;
    //县列表
    private List<County> countyList;
    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.list_view)
    ListView listView;

    public ChooseAreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        ButterKnife.bind(this, view);
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCity();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounty();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询所有的省份，优先从数据库查找，如果没有查询到再到服务器上查询
     */
    private void queryProvinces() {
        Log.e(TAG, "queryProvinces-----------------------------------");
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province:provinceList
                 ) {
                dataList.add(province.getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel =LEVEL_PROVINCE;
        }else {
            String url = I.REQUEST;
            Log.e(TAG, "url---------" + url);
            queryFromServer(url,"province");
        }
    }

    /**
     * 查询省内所有的城市，优先从数据库中查找，如果没有查询到再去服务器查询
     */
    private void queryCity() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid=?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city :
                    cityList) {
                dataList.add(city.getCityName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else {
            int provinceCode = selectedProvince.getPrivonceCode();
            String url =I.REQUEST+provinceCode;
            queryFromServer(url,"city");
        }
    }
    /**
     * 查询城市内所有的县城，优先从数据库中查找，如果没有查询到再去服务器查询
     */
    private void queryCounty() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size()>0){
            dataList.clear();
            for (County county :
                    countyList) {
                dataList.add(county.getCountyName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            int provinceCode  = selectedProvince.getPrivonceCode();
            int cityCode = selectedCity.getCityCode();
            String url = I.REQUEST+provinceCode+"/"+cityCode;
            queryFromServer(url,"county");
        }
    }



    //根据传入的地址和类型从服务器查找数据
    private void queryFromServer(String url, final String level) {
        showProgressDialog();
        HttpUtil.sendOKhttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                Log.e(TAG, "responseText------------" + responseText);
                    boolean result =false;
                if ("province".equals(level)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if ("city".equals(level)){
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                }else if ("county".equals(level)){
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }
                Log.e(TAG, "result----------" + String.valueOf(result));
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(level)){
                                queryProvinces();
                            }else if ("city".equals(level)){
                                queryCity();
                            }else if ("county".equals(level)){
                                queryCounty();
                            }
                        }
                    });
                }
            }

        });
    }

    //显示进度对话框
    private void showProgressDialog() {
        if (progressDialog==null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    //关闭进度对话框
    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @OnClick(R.id.back_button)
    public void onClick() {
        Log.e(TAG, "onClick------------"+currentLevel);
        if (currentLevel==LEVEL_COUNTY){
            queryCity();
        }else  if (currentLevel==LEVEL_CITY){
            queryProvinces();
        }
    }

}
