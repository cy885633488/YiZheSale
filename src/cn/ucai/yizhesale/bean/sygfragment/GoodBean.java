package cn.ucai.yizhesale.bean.sygfragment;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/27.
 */
public class GoodBean implements Serializable {
    private Object comment;
    private Object commentNickName;
    private double Discount;
    private int Id;
    private int IsBaoYou;
    private int IsPromotion;
    private String Name;
    private double NewPrice;
    private double OldPrice;
    private int PFrom;
    private int picHeight;
    private int picWidth;
    private String ProductImg;
    private String ProductImg1;
    private String ProductImgWX;
    private String ProductUrl;
    private int SaleTotal;
    private String SpreadUrl;

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public double getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(double oldPrice) {
        OldPrice = oldPrice;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Object getCommentNickName() {
        return commentNickName;
    }

    public void setCommentNickName(Object commentNickName) {
        this.commentNickName = commentNickName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getNewPrice() {
        return NewPrice;
    }

    public void setNewPrice(double newPrice) {
        NewPrice = newPrice;
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

    public int getPFrom() {
        return PFrom;
    }

    public void setPFrom(int PFrom) {
        this.PFrom = PFrom;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public String getProductImg() {
        return ProductImg;
    }

    public void setProductImg(String productImg) {
        ProductImg = productImg;
    }

    public String getProductImg1() {
        return ProductImg1;
    }

    public void setProductImg1(String productImg1) {
        ProductImg1 = productImg1;
    }

    public String getProductImgWX() {
        return ProductImgWX;
    }

    public void setProductImgWX(String productImgWX) {
        ProductImgWX = productImgWX;
    }

    public String getProductUrl() {
        return ProductUrl;
    }

    public void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }

    public int getSaleTotal() {
        return SaleTotal;
    }

    public void setSaleTotal(int saleTotal) {
        SaleTotal = saleTotal;
    }

    public String getSpreadUrl() {
        return SpreadUrl;
    }

    public void setSpreadUrl(String spreadUrl) {
        SpreadUrl = spreadUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodBean goodBean = (GoodBean) o;

        return Id == goodBean.Id;

    }

    @Override
    public int hashCode() {
        return (int) (Id ^ (Id >>> 32));
    }

    @Override
    public String toString() {
        return "GoodBean{" +
                "comment='" + comment + '\'' +
                ", commentNickName='" + commentNickName + '\'' +
                ", Discount=" + Discount +
                ", Id=" + Id +
                ", IsBaoYou=" + IsBaoYou +
                ", IsPromotion=" + IsPromotion +
                ", Name='" + Name + '\'' +
                ", NewPrice=" + NewPrice +
                ", OldPrice=" + OldPrice +
                ", PFrom=" + PFrom +
                ", picHeight=" + picHeight +
                ", picWidght=" + picWidth +
                ", ProductImg='" + ProductImg + '\'' +
                ", ProductImg1='" + ProductImg1 + '\'' +
                ", ProductImgWX='" + ProductImgWX + '\'' +
                ", ProductUrl='" + ProductUrl + '\'' +
                ", SaleTotal=" + SaleTotal +
                ", SpreadUrl='" + SpreadUrl + '\'' +
                '}';
    }
}
