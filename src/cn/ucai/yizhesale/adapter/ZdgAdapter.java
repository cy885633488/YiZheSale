package cn.ucai.yizhesale.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.bean.sygfragment.GoodBean;
import cn.ucai.yizhesale.utils.ImageUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ZdgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<GoodBean> mGoodList;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public ZdgAdapter(Context mContext, ArrayList<GoodBean> mGoodList) {
        this.mContext = mContext;
        this.mGoodList = mGoodList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ZdgGoodItemViewHolder holder = new ZdgGoodItemViewHolder(inflater.inflate(R.layout.item_good_zdg,
                parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZdgGoodItemViewHolder goodHolder = (ZdgGoodItemViewHolder) holder;
        GoodBean good = mGoodList.get(position);
        goodHolder.tvGoodName.setText(good.getName());
        goodHolder.tvNewPrice.setText(sub(("￥"+good.getNewPrice())));
        goodHolder.tvOldPrice.setText(sub(("￥"+good.getOldPrice())));
        goodHolder.tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        goodHolder.tvSaleTotal.setText("已售"+good.getSaleTotal()+"件");
        if (good.getIsBaoYou()==1){
            goodHolder.tvIsBaoYou.setText("包邮");
        }else {
            goodHolder.tvIsBaoYou.setText("不包邮");
        }
        goodHolder.tvDiscount.setText(sub((""+good.getDiscount()))+"折");
        ImageUtils.setSygGoodThumb(good.getProductImg(),goodHolder.nivGoodThumb);
    }

    @Override
    public int getItemCount() {
        return mGoodList==null?0:mGoodList.size();
    }

    public void initList(ArrayList<GoodBean> list) {
        if (mGoodList!=null && list!=null){
            mGoodList.clear();
            mGoodList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addList(ArrayList<GoodBean> list) {
        if (list!=null && list.size()>0){
            mGoodList.addAll(list);
            notifyDataSetChanged();
        }
    }

    class ZdgGoodItemViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView nivGoodThumb;
        TextView tvGoodName;
        TextView tvNewPrice;
        TextView tvOldPrice;
        TextView tvDiscount;
        TextView tvSaleTotal;
        TextView tvIsBaoYou;
        public ZdgGoodItemViewHolder(View itemView) {
            super(itemView);
            nivGoodThumb = (NetworkImageView) itemView.findViewById(R.id.nivGoodThumb_zdg);
            tvGoodName = (TextView) itemView.findViewById(R.id.tvGoodName_zdg);
            tvNewPrice = (TextView) itemView.findViewById(R.id.newPrice_zdg);
            tvOldPrice = (TextView) itemView.findViewById(R.id.oldPrice_zdg);
            tvDiscount = (TextView) itemView.findViewById(R.id.disCount_zdg);
            tvSaleTotal = (TextView) itemView.findViewById(R.id.saleTotal_zdg);
            tvIsBaoYou = (TextView) itemView.findViewById(R.id.isBaoYou_zdg);
        }
    }

    private String sub(String string){
        String str = null;
        if (string.contains(".")){
            if (string.length()==(string.indexOf("."))+2){
                str = string+"0";
            }else if (string.length()>(string.indexOf("."))+2){
                str = string.substring(0,(string.indexOf("."))+3);
            }
        }else {
            str = string+".00";
        }
        return str;
    }
}
