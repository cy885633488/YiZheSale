package cn.ucai.yizhesale.bean.pptmfragment;

import android.widget.ScrollView;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PptmGoodBean implements Serializable {
    private int Id;
    private int IsBaoYou;
    private int IsPromotion;
    private String Name;
    private double NewPrice;
    private int PFrom;
    private String ProductImg;
    private double disCount;
    private double oldPrice;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIsBaoYou() {
        return IsBaoYou;
    }

    public void setIsBaoYou(int isBaoYou) {
        IsBaoYou = isBaoYou;
    }

    public int getIsPromotion() {
        return IsPromotion;
    }

    public void setIsPromotion(int isPromotion) {
        IsPromotion = isPromotion;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getNewPrice() {
        return NewPrice;
    }

    public void setNewPrice(double newPrice) {
        NewPrice = newPrice;
    }

    public int getPFrom() {
        return PFrom;
    }

    public void setPFrom(int PFrom) {
        this.PFrom = PFrom;
    }

    public String getProductImg() {
        return ProductImg;
    }

    public void setProductImg(String productImg) {
        ProductImg = productImg;
    }

    public double getDisCount() {
        return disCount;
    }

    public void setDisCount(double disCount) {
        this.disCount = disCount;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Override
    public String toString() {
        return "PptmGoodBean{" +
                "Id=" + Id +
                ", IsBaoYou=" + IsBaoYou +
                ", IsPromotion=" + IsPromotion +
                ", Name='" + Name + '\'' +
                ", NewPrice=" + NewPrice +
                ", PFrom=" + PFrom +
                ", ProductImg='" + ProductImg + '\'' +
                ", disCount=" + disCount +
                ", oldPrice=" + oldPrice +
                '}';
    }
}
