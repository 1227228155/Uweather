package cn.ucai.uweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class Province extends DataSupport {
    private int id;
    private String privinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return privinceName;
    }

    public void setProvinceName(String privinceName) {
        this.privinceName = privinceName;
    }

    public int getPrivonceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int privinceCode) {
        this.provinceCode = privinceCode;
    }

}
