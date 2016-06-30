package cn.ucai.yizhesale.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.yizhesale.I;
import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.activity.BaseActivity;
import cn.ucai.yizhesale.adapter.SyAdapter;
import cn.ucai.yizhesale.bean.syfragment.item1.Da;
import cn.ucai.yizhesale.bean.syfragment.item1.Xiao;
import cn.ucai.yizhesale.bean.syfragment.item2.SyItem2;
import cn.ucai.yizhesale.bean.sygfragment.GoodBean;
import cn.ucai.yizhesale.bean.sygfragment.GoodTotal;
import cn.ucai.yizhesale.data.ApiParams;
import cn.ucai.yizhesale.data.MultipartRequest;
import cn.ucai.yizhesale.utils.ImageUtils;
import cn.ucai.yizhesale.utils.Utils;
import cn.ucai.yizhesale.view.FlowIndicator;
import cn.ucai.yizhesale.view.SlideAutoLoopView;

/**
 * Created by Administrator on 2016/6/27.
 */
public class SyFragment extends Fragment {
    BaseActivity mContext;
    RecyclerView mRecyclerView;
    SyAdapter mAdapter;
    ArrayList<GoodBean> mGoodList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView tvHint;
    GridLayoutManager mGridLayoutManager;
    String path;
    int pages = 1;
    int action = I.ACTION_DOWNLOAD;
    SlideAutoLoopView mSlideAutoLoopView;
    FlowIndicator mFlowIndicator;
    TextView mtvNZ,mtvJJ,mtvXB,mtvGD;
    NetworkImageView nivJRGX,nivSJZB,nivMSH,nivMZGH,nivZHFQ;
    NetworkImageView[] mNetworkImageViews;
    String predate = "2016-06-28+14%3A04%3A13";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_sy,null);
        mContext = (BaseActivity) getActivity();
        mGoodList = new ArrayList<GoodBean>();
        initData();
        initView(layout);
        setListener();
        return layout;
    }

    private void setListener() {
        setDownListener();
        setUpListener();
    }

    private void setUpListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItemPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("main","lastItemPosition="+lastItemPosition);
                Log.i("main","Count = "+mAdapter.getItemCount());
                if (newState==RecyclerView.SCROLL_STATE_IDLE &&
                        lastItemPosition==mAdapter.getItemCount()-1){
                    if (mAdapter.isMore()){
                        action = I.ACTION_PULL_UP;
                        pages++;
                        predate = "";
                        getPath3();
                        mContext.executeRequest(new MultipartRequest<GoodTotal>(path,GoodTotal.class,null,
                                responseDownloadGoodTotalListener(),mContext.errorListener(),null,null));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后列表项的下标
                lastItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
                //解决RecyclerView和SwipeRefreshLayout共用存在的bug
                mSwipeRefreshLayout.setEnabled(mGridLayoutManager.
                        findFirstCompletelyVisibleItemPosition()==0);
            }
        });
    }

    private void setDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                action = I.ACTION_PULL_DOWN;
                tvHint.setVisibility(View.VISIBLE);
                getPath3();
                mContext.executeRequest(new MultipartRequest<GoodTotal>(path,GoodTotal.class,null,
                        responseDownloadGoodTotalListener(),mContext.errorListener(),null,null));
            }
        });
    }

    private void initData() {
        getPath1();
        getPath2();
        getPath3();
    }

    private void getPath2() {
        try {
            path = new ApiParams()
                    .getRequestUrlSyItem2("34");
            mContext.executeRequest(new MultipartRequest<SyItem2[]>(path,SyItem2[].class,null,
                    responseDownloadSYItem2Listener(),mContext.errorListener(),null,null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<SyItem2[]> responseDownloadSYItem2Listener() {
        return new Response.Listener<SyItem2[]>() {
            @Override
            public void onResponse(SyItem2[] syItem2s) {
                if (syItem2s!=null && syItem2s.length>0){
                    for (int i=0;i<syItem2s.length;i++){
                        ImageUtils.setSygGoodThumb(syItem2s[i].getImgUrl()
                                ,mNetworkImageViews[syItem2s[i].getAId()-1]);
                    }
                }
            }
        };
    }

    private void getPath1() {
        try {
            path = new ApiParams()
                    .getRequestUrlSyItem1("34");
            mContext.executeRequest(new MultipartRequest<Da>(path,Da.class,null,
                    responseDownloadSYimgListener(),mContext.errorListener(),null,null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<Da> responseDownloadSYimgListener() {
        return new Response.Listener<Da>() {
            @Override
            public void onResponse(Da da) {
                if (da!=null && da.getAdList().length>0){
                    String[] imgUrlArr = new String[da.getAdList().length];
                    for (int i=0;i<da.getAdList().length;i++){
                        imgUrlArr[i] = (da.getAdList())[i].getImgUrl();
                    }
                    mSlideAutoLoopView.startPlayLoop(mFlowIndicator,imgUrlArr,imgUrlArr.length);
                }
            }
        };
    }

    private Response.Listener<GoodTotal> responseDownloadGoodTotalListener() {
        return new Response.Listener<GoodTotal>() {
            @Override
            public void onResponse(GoodTotal goodTotal) {
                ArrayList<GoodBean> list = null;
                if (goodTotal!=null){
                    if (goodTotal.getRows()!=null && goodTotal.getRows().length>0) {
                        list = Utils.array2List(goodTotal.getRows());
                        mAdapter.setMore(true);
                        if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            tvHint.setVisibility(View.GONE);
                            mAdapter.initList(list);
                        } else if (action == I.ACTION_PULL_UP) {
                            mAdapter.addList(list);
                        }
                        if (goodTotal.getRows().length<40){
                            mAdapter.setMore(false);
                        }
                    }
                }
            }
        };
    }

    private String getPath3(){
        try {
            path = new ApiParams()
                    .with(I.Syg.V,""+34)
                    .with(I.Syg.PAGES,""+pages)
                    .with(I.Syg.BC,""+0)
                    .with(I.Syg.SC,""+0)
                    .with(I.Syg.SORTS,"")
                    .with(I.Syg.CHANNEL,""+0)
                    .with(I.Syg.CKEY,"")
                    .with(I.Syg.DAYNEWS,"")
                    .with(I.Syg.LPRICE,""+0)
                    .with(I.Syg.HPRICE,""+0)
                    .with(I.Syg.TBCLASS,""+0)
                    .with(I.Syg.ACTID,""+0)
                    .with(I.Syg.BRANDID,""+0)
                    .with(I.Syg.PREDATE,predate)
                    .with(I.Syg.INDEX,""+1)
                    .getRequestUrl(I.REQUEST_SHIYUANGOU);
            mContext.executeRequest(new MultipartRequest<GoodTotal>(path,GoodTotal.class,null,
                    responseDownloadGoodTotalListener(),mContext.errorListener(),null,null));
            Log.i("main",path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private void initView(View layout) {
        mSlideAutoLoopView = (SlideAutoLoopView) layout.findViewById(R.id.salv);
        mFlowIndicator = (FlowIndicator) layout.findViewById(R.id.indicator);
        mtvNZ = (TextView) layout.findViewById(R.id.sy_nz);
        mtvJJ = (TextView) layout.findViewById(R.id.sy_jj);
        mtvXB = (TextView) layout.findViewById(R.id.sy_xb);
        mtvGD = (TextView) layout.findViewById(R.id.sy_gd);
        nivJRGX = (NetworkImageView) layout.findViewById(R.id.nivJRGX);
        nivSJZB = (NetworkImageView) layout.findViewById(R.id.nivSJZB);
        nivMSH = (NetworkImageView) layout.findViewById(R.id.nivMSH);
        nivMZGH = (NetworkImageView) layout.findViewById(R.id.nivMZGH);
        nivZHFQ = (NetworkImageView) layout.findViewById(R.id.nivZHFQ);
        mNetworkImageViews = new NetworkImageView[5];
        mNetworkImageViews[0] = nivJRGX;
        mNetworkImageViews[1] = nivSJZB;
        mNetworkImageViews[2] = nivMSH;
        mNetworkImageViews[3] = nivMZGH;
        mNetworkImageViews[4] = nivZHFQ;
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.sy_srl);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        tvHint = (TextView) layout.findViewById(R.id.tv_hint_sy);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_sy);
        mGridLayoutManager = new GridLayoutManager(mContext,I.COLUM_NUM);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new SyAdapter(mContext,mGoodList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
