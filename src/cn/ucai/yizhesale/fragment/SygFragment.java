package cn.ucai.yizhesale.fragment;

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
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.yizhesale.I;
import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.activity.BaseActivity;
import cn.ucai.yizhesale.activity.YiZHESaleMainActivity;
import cn.ucai.yizhesale.adapter.SygAdapter;
import cn.ucai.yizhesale.bean.sygfragment.GoodBean;
import cn.ucai.yizhesale.bean.sygfragment.GoodTotal;
import cn.ucai.yizhesale.data.ApiParams;
import cn.ucai.yizhesale.data.MultipartRequest;
import cn.ucai.yizhesale.utils.Utils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class SygFragment extends Fragment {
    BaseActivity mContext;
    RadioButton mrbBoutique,mrbOne,mrbTwo,mrbThree;
    RecyclerView mRecyclerView;
    SygAdapter mAdapter;
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
        View layout = inflater.inflate(R.layout.fragment_syg,null);
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
        mContext.executeRequest(new MultipartRequest<GoodTotal>(path,GoodTotal.class,null,
                responseDownloadGoodTotalListener(),mContext.errorListener(),null,null));
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
                    .with(I.Syg.SC,""+0)
                    .with(I.Syg.SORTS,"")
                    .with(I.Syg.CHANNEL,""+9)
                    .with(I.Syg.CKEY,"")
                    .with(I.Syg.DAYNEWS,"")
                    .with(I.Syg.LPRICE,""+0)
                    .with(I.Syg.HPRICE,""+0)
                    .with(I.Syg.TBCLASS,""+0)
                    .with(I.Syg.ACTID,""+0)
                    .with(I.Syg.BRANDID,""+0)
                    .with(I.Syg.PREDATE,"")
                    .with(I.Syg.INDEX,""+0)
                    .getRequestUrl(I.REQUEST_SHIYUANGOU);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.syg_srl);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        tvHint = (TextView) layout.findViewById(R.id.tv_hint_syg);
        mrbBoutique = (RadioButton) layout.findViewById(R.id.boutique_syg);
        mrbOne = (RadioButton) layout.findViewById(R.id.one_syg);
        mrbTwo = (RadioButton) layout.findViewById(R.id.two_syg);
        mrbThree = (RadioButton) layout.findViewById(R.id.three_syg);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_syg);
        mGridLayoutManager = new GridLayoutManager(mContext,I.COLUM_NUM);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new SygAdapter(mContext,mGoodList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
