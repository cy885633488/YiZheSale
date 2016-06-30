package cn.ucai.yizhesale.bean.pptmfragment;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PptmGoodTotalNum implements Serializable {
    private PptmGoodTotal[] rows;
    private int total;

    public PptmGoodTotal[] getRows() {
        return rows;
    }

    public void setRows(PptmGoodTotal[] rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PptmGoodTotalNum{" +
                "rows=" + Arrays.toString(rows) +
                ", total=" + total +
                '}';
    }
}
