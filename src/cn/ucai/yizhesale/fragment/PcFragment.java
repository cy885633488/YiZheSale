package cn.ucai.yizhesale.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;

import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.activity.LoginActivity;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PcFragment extends Fragment {
    Button mLogin;
    Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_pensonal_center,null);
        mContext = getActivity();
        initView(layout);
        setListener();
        return layout;
    }

    private void initView(View layout) {
        mLogin = (Button) layout.findViewById(R.id.btn_login_pc);
    }

    private void setListener() {
        setBtnLoginListener();
    }

    private void setBtnLoginListener() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }
}
