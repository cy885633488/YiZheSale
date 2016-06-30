package cn.ucai.yizhesale.bean.sygfragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/27.
 */
public class GoodTotal implements Serializable {
    private GoodBean[] rows;
    private int total;
    private int totalnew;

    public GoodBean[] getRows() {
        return rows;
    }

    public void setRows(GoodBean[] rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalnew() {
        return totalnew;
    }

    public void setTotalnew(int totalnew) {
        this.totalnew = totalnew;
    }

    @Override
    public String toString() {
        return "GoodTotal{" +
                "rows=" + Arrays.toString(rows) +
                ", total=" + total +
                ", totalnew=" + totalnew +
                '}';
    }
}
