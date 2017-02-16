package cn.ucai.uweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class Province extends DataSupport {
    private int id;
    private String privinceName;
    private int privinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrivinceName() {
        return privinceName;
    }

    public void setPrivinceName(String privinceName) {
        this.privinceName = privinceName;
    }

    public int getPrivinceCode() {
        return privinceCode;
    }

    public void setPrivinceCode(int privinceCode) {
        this.privinceCode = privinceCode;
    }
}
