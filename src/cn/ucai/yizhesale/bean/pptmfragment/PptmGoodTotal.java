package cn.ucai.yizhesale.bean.pptmfragment;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PptmGoodTotal implements Serializable {
    private int brandId;
    private double DisCount;
    private String EndDate;
    private String EndDateStr;
    private String ImgUrlSml;
    private String Name;
    private String ProductInfo;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public double getDisCount() {
        return DisCount;
    }

    public void setDisCount(double disCount) {
        DisCount = disCount;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndDateStr() {
        return EndDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        EndDateStr = endDateStr;
    }

    public String getImgUrlSml() {
        return ImgUrlSml;
    }

    public void setImgUrlSml(String imgUrlSml) {
        ImgUrlSml = imgUrlSml;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProductInfo() {
        return ProductInfo;
    }

    public void setProductInfo(String productInfo) {
        ProductInfo = productInfo;
    }

    @Override
    public String toString() {
        return "PptmGoodTotal{" +
                "brandId=" + brandId +
                ", DisCount=" + DisCount +
                ", EndDate='" + EndDate + '\'' +
                ", EndDateStr='" + EndDateStr + '\'' +
                ", ImgUrlSml='" + ImgUrlSml + '\'' +
                ", Name='" + Name + '\'' +
                ", ProductInfo='" + ProductInfo + '\'' +
                '}';
    }
}
