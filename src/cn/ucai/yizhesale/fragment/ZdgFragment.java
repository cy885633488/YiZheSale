package cn.ucai.yizhesale.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.yizhesale.I;
import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.activity.BaseActivity;
import cn.ucai.yizhesale.adapter.ZdgAdapter;
import cn.ucai.yizhesale.bean.sygfragment.GoodBean;
import cn.ucai.yizhesale.bean.sygfragment.GoodTotal;
import cn.ucai.yizhesale.data.ApiParams;
import cn.ucai.yizhesale.data.GsonRequest;
import cn.ucai.yizhesale.data.MultipartRequest;
import cn.ucai.yizhesale.utils.Utils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ZdgFragment extends Fragment {
    BaseActivity mContext;
    RadioButton mrbBoutique,mrbNvZhuang,mrbNanZhuang,mrbJujia,mrbMuYin;
    RadioButton mrbXieBao,mrbPeiShi,mrbMeiShi,mrbShuMa,mrbMeiZhuang,mrbWenti;
    RecyclerView mRecyclerView;
    ZdgAdapter mAdapter;
    ArrayList<GoodBean> mGoodList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView tvHint;
    GridLayoutManager mGridLayoutManager;
    String path;
    int Pages = 1;
    int action = I.ACTION_DOWNLOAD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_zdg,null);
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
                if (newState==RecyclerView.SCROLL_STATE_IDLE &&
                        lastItemPosition==mAdapter.getItemCount()-1){
                    if (mAdapter.isMore()){
                        action = I.ACTION_PULL_UP;
                        Pages++;
                        getPath();
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
                Pages = 1;
                action = I.ACTION_PULL_DOWN;
                tvHint.setVisibility(View.VISIBLE);
                getPath();
                mContext.executeRequest(new MultipartRequest<GoodTotal>(path,GoodTotal.class,null,
                        responseDownloadGoodTotalListener(),mContext.errorListener(),null,null));
            }
        });
    }

    private void initData() {
        getPath();
        mContext.executeRequest(new GsonRequest<GoodTotal>(path,GoodTotal.class,
                responseDownloadGoodTotalListener(),mContext.errorListener()));
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

    private String getPath(){
        try {
            path = new ApiParams()
                    .with(I.Syg.V,""+34)
                    .with(I.Syg.PAGES,""+Pages)
                    .with(I.Syg.BC,""+0)
                    .with(I.Syg.BRANDID,""+0)
                    .getRequestUrl(I.REQUEST_ZHIDEGUANG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.zdg_srl);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        tvHint = (TextView) layout.findViewById(R.id.tv_hint_zdg);
        mrbBoutique = (RadioButton) layout.findViewById(R.id.boutique_zdg);
        setRBWidthHeight(mrbBoutique);
        mrbNvZhuang = (RadioButton) layout.findViewById(R.id.nvzhuang_zdg);
        setRBWidthHeight(mrbNvZhuang);
        mrbNanZhuang = (RadioButton) layout.findViewById(R.id.nanzhuang_zdg);
        setRBWidthHeight(mrbNanZhuang);
        mrbJujia = (RadioButton) layout.findViewById(R.id.jujia_zdg);
        setRBWidthHeight(mrbJujia);
        mrbMuYin = (RadioButton) layout.findViewById(R.id.muyin_zdg);
        setRBWidthHeight(mrbMuYin);
        mrbXieBao = (RadioButton) layout.findViewById(R.id.xiebao_zdg);
        setRBWidthHeight(mrbXieBao);
        mrbPeiShi = (RadioButton) layout.findViewById(R.id.peishi_zdg);
        setRBWidthHeight(mrbPeiShi);
        mrbMeiShi = (RadioButton) layout.findViewById(R.id.meishi_zdg);
        setRBWidthHeight(mrbMeiShi);
        mrbShuMa = (RadioButton) layout.findViewById(R.id.shuma_zdg);
        setRBWidthHeight(mrbShuMa);
        mrbMeiZhuang = (RadioButton) layout.findViewById(R.id.meizhuang_zdg);
        setRBWidthHeight(mrbMeiZhuang);
        mrbWenti = (RadioButton) layout.findViewById(R.id.wenti_zdg);
        setRBWidthHeight(mrbWenti);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_zdg);
        mGridLayoutManager = new GridLayoutManager(mContext,I.COLUM_NUM);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new ZdgAdapter(mContext,mGoodList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setRBWidthHeight(RadioButton rb){
        DisplayMetrics dm = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
        rb.setLayoutParams(new RadioGroup.LayoutParams(dm.widthPixels/5,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
