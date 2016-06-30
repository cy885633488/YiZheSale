package cn.ucai.yizhesale.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.fragment.PcFragment;
import cn.ucai.yizhesale.fragment.PptmFragment;
import cn.ucai.yizhesale.fragment.SyFragment;
import cn.ucai.yizhesale.fragment.SygFragment;
import cn.ucai.yizhesale.fragment.ZdgFragment;

/**
 * Created by Administrator on 2016/6/24.
 */
public class YiZHESaleMainActivity extends BaseActivity {
    Context mContext;
    Fragment[] fragments;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    private RadioButton[] mRadios;
    SygFragment mSygFragment;
    ZdgFragment mZdgFragment;
    SyFragment mSyFragment;
    PptmFragment mPptmFragment;
    PcFragment mPcFragment;
    RadioButton mrbSyg,mrbShouYe,mrbPptm,mrbZdg,mrbMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_yizhesale);
        fragments = new Fragment[5];
        initView();
        initFragment();
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fl, mSyFragment)
                .add(R.id.main_fl, mPptmFragment).hide(mPptmFragment)
                .add(R.id.main_fl, mSygFragment).hide(mSygFragment)
                .add(R.id.main_fl, mZdgFragment).hide(mZdgFragment)
                .add(R.id.main_fl, mPcFragment).hide(mPcFragment)
                .show(mSyFragment)
                .commit();
    }

    private void initFragment() {
        mSygFragment = new SygFragment();
        mZdgFragment = new ZdgFragment();
        mSyFragment = new SyFragment();
        mPptmFragment = new PptmFragment();
        mPcFragment = new PcFragment();
        fragments[0] = mSyFragment;
        fragments[1] = mPptmFragment;
        fragments[2] = mSygFragment;
        fragments[3] = mZdgFragment;
        fragments[4] = mPcFragment;
    }

    private void initView() {
        mRadios = new RadioButton[5];
        mrbSyg = (RadioButton) findViewById(R.id.rb_syg);
        mrbShouYe = (RadioButton) findViewById(R.id.rb_shouye);
        mrbPptm = (RadioButton) findViewById(R.id.rb_pptm);
        mrbZdg = (RadioButton) findViewById(R.id.rb_zdg);
        mrbMe = (RadioButton) findViewById(R.id.rb_me);
        mRadios[0] = mrbShouYe;
        mRadios[1] = mrbPptm;
        mRadios[2] = mrbSyg;
        mRadios[3] = mrbZdg;
        mRadios[4] = mrbMe;
    }

    /**
     * button点击事件
     *
     * @param view
     */
    public void onRadioClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_shouye:
                index = 0;
                break;
            case R.id.rb_pptm:
                index = 1;
                break;
            case R.id.rb_syg:
                index = 2;
                break;
            case R.id.rb_zdg:
                index = 3;
                break;
            case R.id.rb_me:
                index = 4;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        currentTabIndex = index;
    }

}
