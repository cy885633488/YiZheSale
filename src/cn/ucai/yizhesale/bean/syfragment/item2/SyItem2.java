package cn.ucai.yizhesale.bean.syfragment.item2;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/28.
 */
public class SyItem2 implements Serializable {
    private int AId;
    private String imgUrl;
    private String Name;
    private String UrlAddress;
    private String UrlClass;

    public int getAId() {
        return AId;
    }

    public void setAId(int AId) {
        this.AId = AId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrlAddress() {
        return UrlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        UrlAddress = urlAddress;
    }

    public String getUrlClass() {
        return UrlClass;
    }

    public void setUrlClass(String urlClass) {
        UrlClass = urlClass;
    }

    @Override
    public String toString() {
        return "SyItem3{" +
                "AId=" + AId +
                ", imgUrl='" + imgUrl + '\'' +
                ", Name='" + Name + '\'' +
                ", UrlAddress='" + UrlAddress + '\'' +
                ", UrlClass='" + UrlClass + '\'' +
                '}';
    }
}
