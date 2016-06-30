package cn.ucai.yizhesale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.bean.pptmfragment.PptmGoodTotal;
import cn.ucai.yizhesale.utils.ImageUtils;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PptmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContent;
    ArrayList<PptmGoodTotal> mGoodList;
    String imgUrl = "http://www.syby8.com";
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public PptmAdapter(Context mContent, ArrayList<PptmGoodTotal> mGoodList) {
        this.mContent = mContent;
        this.mGoodList = mGoodList;
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
        PptmGoodItemViewHolder goodHolder = (PptmGoodItemViewHolder) holder;
        ImageUtils.setSygGoodThumb(imgUrl+good.getImgUrlSml(),goodHolder.nivPPLogo);
        goodHolder.tvPPName.setText(good.getName());
        goodHolder.tvPPDiscount.setText(sub(""+good.getDisCount())+"折起");
        try {
            goodHolder.tvPPDay.setText("剩"+test8(good.getEndDateStr())+"天");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0;i<good.getProductInfo().length;i++){
            ImageUtils.setSygGoodThumb(good.getProductInfo()[i].getProductImg(),
                    goodHolder.nivGood1Thumb);
            goodHolder.tvGood1NewPrice.setText(sub(""+good.getProductInfo()[i].getNewPrice()));
            goodHolder.tvGood1Discount.setText(sub(""+good.getProductInfo()[i].getDisCount()));
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
        }
    }

    public String sub(String string){
        String str = null;
        if (string.contains(".")){
            if (string.length()==(string.indexOf("."))+1){
                str = string;
            }else if (string.length()>(string.indexOf("."))+1){
                str = string.substring(0,(string.indexOf("."))+2);
            }
        }else {
            str = string;
        }
        return str;
    }

    private String test8(String date) throws Exception{
        final DateFormat dateformat = new SimpleDateFormat(date, Locale.CHINA);
        long	h8			= 1000 * 60 * 60 *8;
        long	h16			= h8*2;
        long	curDay	= System.currentTimeMillis();//此处+ut8是因为可以减少在分区的时候做一次减法
        long h24 = h8*3;
        curDay = curDay - curDay % h24 + (curDay % h24>=h16?h24:0);

        long t1 = 1368892799999L;
        long m = t1%h24;
        long i = (curDay-(t1-m+(m>=h16?h24:0)))/h24;

        t1 = 1368892799999L-h24+1;
        i = (curDay-(t1-m+(m>=h16?h24:0)))/h24;

        t1 = 1368892800000L;
        i = (curDay-(t1-m+(m>=h16?h24:0)))/h24;

        for(int a=0;a<1000;a++){
            i=new Long((dateformat.parse(dateformat.format(new Date(curDay))).getTime()-
                    dateformat.parse(dateformat.format(new Date(t1))).getTime())/(1000 * 60 * 60 * 24)).intValue();
        }

        for(int a=0;a<1000;a++){
            m = t1%h24;
            i = (curDay-(t1-m+(m>=h16?h24:0)))/h24;
        }
        return ""+i;
    }
}
