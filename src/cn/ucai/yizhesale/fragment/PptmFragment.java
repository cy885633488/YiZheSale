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
import cn.ucai.yizhesale.adapter.PptmAdapter;
import cn.ucai.yizhesale.adapter.SygAdapter;
import cn.ucai.yizhesale.bean.pptmfragment.PptmGoodTotal;
import cn.ucai.yizhesale.bean.pptmfragment.PptmGoodTotalNum;
import cn.ucai.yizhesale.bean.sygfragment.GoodBean;
import cn.ucai.yizhesale.bean.sygfragment.GoodTotal;
import cn.ucai.yizhesale.data.ApiParams;
import cn.ucai.yizhesale.data.GsonRequest;
import cn.ucai.yizhesale.data.MultipartRequest;
import cn.ucai.yizhesale.utils.Utils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PptmFragment extends Fragment {
    BaseActivity mContext;
    RadioButton mrbZXSX,mrbZRSX,mrbZHFQ,mrbJZNZ;
    RecyclerView mRecyclerView;
    PptmAdapter mAdapter;
    ArrayList<PptmGoodTotal> mPptmGoodList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView tvHint;
    LinearLayoutManager mLinearLayoutManager;
    String path;
    int cpage = 1;
    int pagesize = 20;
    int action = I.ACTION_DOWNLOAD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_pptm,null);
        mContext = (BaseActivity) getActivity();
        mPptmGoodList = new ArrayList<PptmGoodTotal>();
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
                        cpage++;
                        getPath();
                        mContext.executeRequest(new GsonRequest<PptmGoodTotalNum>(path,
                                PptmGoodTotalNum.class,
                                responseDownloadPptmGoodTotalListener(),
                                mContext.errorListener()));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后列表项的下标
                lastItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                //解决RecyclerView和SwipeRefreshLayout共用存在的bug
                mSwipeRefreshLayout.setEnabled(mLinearLayoutManager.
                        findFirstCompletelyVisibleItemPosition()==0);
            }
        });
    }

    private void setDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cpage = 1;
                action = I.ACTION_PULL_DOWN;
                tvHint.setVisibility(View.VISIBLE);
                getPath();
                mContext.executeRequest(new GsonRequest<PptmGoodTotalNum>(path,PptmGoodTotalNum.class,
                        responseDownloadPptmGoodTotalListener(),mContext.errorListener()));
            }
        });
    }

    private void initData() {
        getPath();
        mContext.executeRequest(new GsonRequest<PptmGoodTotalNum>(path,PptmGoodTotalNum.class,
                responseDownloadPptmGoodTotalListener(),mContext.errorListener()));
    }

    private Response.Listener<PptmGoodTotalNum> responseDownloadPptmGoodTotalListener() {
        return new Response.Listener<PptmGoodTotalNum>() {
            @Override
            public void onResponse(PptmGoodTotalNum pptmGoodTotalNum) {
                ArrayList<PptmGoodTotal> list = null;
                if (pptmGoodTotalNum!=null){
                    if (pptmGoodTotalNum.getRows()!=null && pptmGoodTotalNum.getRows().length>0) {
                        list = Utils.array2List(pptmGoodTotalNum.getRows());
                        mAdapter.setMore(true);
                        if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            tvHint.setVisibility(View.GONE);
                            mAdapter.initList(list);
                        } else if (action == I.ACTION_PULL_UP) {
                            mAdapter.addList(list);
                        }
                        if (pptmGoodTotalNum.getRows().length<40){
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
                    .with(I.Pptm.CPAGE,""+cpage)
                    .with(I.Pptm.PAGESIZE,""+pagesize)
                    .with(I.Pptm.BCALSS,""+99)
                    .with(I.Syg.V,""+34)
                    .getRequestUrlPptm(I.REQUEST_PPTM);
            Log.i("main",path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.pptm_srl);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        tvHint = (TextView) layout.findViewById(R.id.tv_hint_pptm);
        mrbZXSX = (RadioButton) layout.findViewById(R.id.rb_zxsx_pptm);
        mrbZRSX = (RadioButton) layout.findViewById(R.id.rb_zrsx_pptm);
        mrbZHFQ = (RadioButton) layout.findViewById(R.id.rb_zhfq_pptm);
        mrbJZNZ = (RadioButton) layout.findViewById(R.id.rb_jznz_pptm);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_pptm);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new PptmAdapter(mContext,mPptmGoodList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
