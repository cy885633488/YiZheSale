package cn.ucai.yizhesale.bean.syfragment.item1;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Xiao implements Serializable {
    private String address;
    private String cName;
    private int cStatus;
    private String imgUrl;
    private int UrlClass;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getcStatus() {
        return cStatus;
    }

    public void setcStatus(int cStatus) {
        this.cStatus = cStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getUrlClass() {
        return UrlClass;
    }

    public void setUrlClass(int urlClass) {
        UrlClass = urlClass;
    }

    @Override
    public String toString() {
        return "sy_item1_xiao{" +
                "address='" + address + '\'' +
                ", cName='" + cName + '\'' +
                ", cStatus=" + cStatus +
                ", imgUrl='" + imgUrl + '\'' +
                ", UrlClass=" + UrlClass +
                '}';
    }
}
