package cn.ucai.yizhesale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.bean.pptmfragment.PptmGoodBean;
import cn.ucai.yizhesale.bean.pptmfragment.PptmGoodTotal;
import cn.ucai.yizhesale.utils.ImageUtils;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PptmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContent;
    ArrayList<PptmGoodTotal> mGoodList;
    ArrayList<ArrayList<PptmGoodBean>> mGoodBeanList;
    String imgUrl = "http://www.syby8.com";
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public PptmAdapter(Context mContent, ArrayList<PptmGoodTotal> mGoodList,
                       ArrayList<ArrayList<PptmGoodBean>> mGoodBeanList) {
        this.mContent = mContent;
        this.mGoodList = mGoodList;
        this.mGoodBeanList = mGoodBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContent);
        PptmGoodItemViewHolder holder =
                new PptmGoodItemViewHolder(inflater.inflate(R.layout.item_good_pptm,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PptmGoodTotal good = mGoodList.get(position);
        ArrayList<PptmGoodBean> goodBean = mGoodBeanList.get(position);
        PptmGoodItemViewHolder goodHolder = (PptmGoodItemViewHolder) holder;
        ImageUtils.setSygGoodThumb(imgUrl+good.getImgUrlSml(),goodHolder.nivPPLogo);
        goodHolder.tvPPName.setText(good.getName());
        goodHolder.tvPPDiscount.setText(sub(""+good.getDisCount())+"折起");
        goodHolder.tvPPDay.setText("剩"+day(good.getEndDateStr())+"天");
        for (int i=0;i<goodBean.size();i++){
            ImageUtils.setSygGoodThumb(goodBean.get(i).getProductImg(),
                    goodHolder.nivGoodThumb[i]);
            goodHolder.nivGoodThumb[i].setScaleType(ImageView.ScaleType.FIT_XY);
            goodHolder.tvGoodNewPrice[i].setText(sub("￥"+goodBean.get(i).getNewPrice()));
            goodHolder.tvGoodDiscount[i].setText(sub(""+goodBean.get(i).getDisCount())+"折");
        }
    }

    @Override
    public int getItemCount() {
        return mGoodList==null?0:mGoodList.size();
    }

    public void initList(ArrayList<PptmGoodTotal> list) {
        if (list!=null && list.size()>0 && mGoodList!=null){
            mGoodList.clear();
            mGoodList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addList(ArrayList<PptmGoodTotal> list) {
        if (list!=null && list.size()>0){
            mGoodList.addAll(list);
            notifyDataSetChanged();
        }
    }

    class PptmGoodItemViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView nivPPLogo,nivGood1Thumb,nivGood2Thumb,nivGood3Thumb;
        TextView tvPPName,tvPPDiscount,tvPPDay,tvGood1NewPrice,tvGood1Discount;
        TextView tvGood2NewPrice,tvGood2Discount,tvGood3NewPrice,tvGood3Discount;
        NetworkImageView[] nivGoodThumb;
        TextView[] tvGoodNewPrice;
        TextView[] tvGoodDiscount;
        public PptmGoodItemViewHolder(View itemView) {
            super(itemView);
            nivPPLogo = (NetworkImageView) itemView.findViewById(R.id.niv_ppThumb_pptm);
            nivGood1Thumb = (NetworkImageView) itemView.findViewById(R.id.pptm_good1_niv_thumb);
            nivGood2Thumb = (NetworkImageView) itemView.findViewById(R.id.pptm_good2_niv_thumb);
            nivGood3Thumb = (NetworkImageView) itemView.findViewById(R.id.pptm_good3_niv_thumb);
            tvPPName = (TextView) itemView.findViewById(R.id.tv_ppName_pptm);
            tvPPDiscount = (TextView) itemView.findViewById(R.id.tv_discount_pptm);
            tvPPDay = (TextView) itemView.findViewById(R.id.tv_day_pptm);
            tvGood1NewPrice = (TextView) itemView.findViewById(R.id.pptm_good1_tv_newPrice);
            tvGood1Discount = (TextView) itemView.findViewById(R.id.pptm_good1_tv_discount);
            tvGood2NewPrice = (TextView) itemView.findViewById(R.id.pptm_good2_tv_newPrice);
            tvGood2Discount = (TextView) itemView.findViewById(R.id.pptm_good2_tv_discount);
            tvGood3NewPrice = (TextView) itemView.findViewById(R.id.pptm_good3_tv_newPrice);
            tvGood3Discount = (TextView) itemView.findViewById(R.id.pptm_good3_tv_discount);
            nivGoodThumb = new NetworkImageView[3];
            nivGoodThumb[0] = nivGood1Thumb;
            nivGoodThumb[1] = nivGood2Thumb;
            nivGoodThumb[2] = nivGood3Thumb;
            tvGoodNewPrice = new TextView[3];
            tvGoodNewPrice[0] = tvGood1NewPrice;
            tvGoodNewPrice[1] = tvGood2NewPrice;
            tvGoodNewPrice[2] = tvGood3NewPrice;
            tvGoodDiscount = new TextView[3];
            tvGoodDiscount[0] = tvGood1Discount;
            tvGoodDiscount[1] = tvGood2Discount;
            tvGoodDiscount[2] = tvGood3Discount;
        }
    }

    private String sub(String string){
        String str = null;
        if (string.contains(".")){
            if (string.length()==(string.indexOf("."))+1){
                str = string.substring(0,string.indexOf("."));
            }else if (string.length()>(string.indexOf("."))+1){
                str = string.substring(0,(string.indexOf("."))+2);
            }
        }else {
            str = string;
        }
        return str;
    }

    /**
     * 输入时间与当前时间的天数
     * @param date
     * @return
     */
    private int day(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = null;
        int tianshu = 0;
        try {
            d = df.parse(date);
            long l = d.getTime();
            long l2 = System.currentTimeMillis();
            tianshu = (int) ((l-l2)/(1000*24*60*60));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tianshu;
    }
}
