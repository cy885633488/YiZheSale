package cn.ucai.yizhesale.bean.syfragment.item1;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Da implements Serializable {
    private Xiao[] adList;
    private Object searchHotKey;

    public Xiao[] getAdList() {
        return adList;
    }

    public void setAdList(Xiao[] adList) {
        this.adList = adList;
    }

    public Object getSearchHotKey() {
        return searchHotKey;
    }

    public void setSearchHotKey(Object searchHotKey) {
        this.searchHotKey = searchHotKey;
    }

    @Override
    public String toString() {
        return "SY_item1_da{" +
                "adList=" + Arrays.toString(adList) +
                ", searchHotKey=" + searchHotKey +
                '}';
    }
}
