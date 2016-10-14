package org.qcode.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.qcode.demo.utils.UIUtil;
import org.qcode.qskinloader.IActivitySkinEventHandler;
import org.qcode.qskinloader.ISkinActivity;
import org.qcode.qskinloader.SkinManager;
import org.qcode.skintestdemo.R;

/**
 * 所有Activity的父类；需要实现ISkinActivity接口
 */
public abstract class BaseActivity extends Activity implements ISkinActivity {
    private IActivitySkinEventHandler mSkinEventHandler;
    private boolean mFirstTimeApplySkin = true;
    private FrameLayout mContentContainer;
    private SkinChangeSwitchView mSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);

        mSkinEventHandler = SkinManager.newActivitySkinEventHandler()
                .setSwitchSkinImmediately(isSwitchSkinImmediately())
                .setSupportSkinChange(isSupportSkinChange())
                .setWindowBackgroundResource(getWindowBackgroundResource());
        mSkinEventHandler.onCreate(this);

        initView(this);
    }

    private void initView(Context context) {
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        super.setContentView(root);

        LinearLayout.LayoutParams paramTitle = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSwitchView = new SkinChangeSwitchView(context);
        int padding = UIUtil.dip2px(context, 15);
        mSwitchView.setPadding(padding, padding, padding, padding);
        root.addView(mSwitchView, paramTitle);

        LinearLayout.LayoutParams paramContent = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        paramContent.weight = 1;
        mContentContainer = new FrameLayout(context);
        root.addView(mContentContainer, paramContent);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mContentContainer);
    }

    @Override
    public void setContentView(View view) {
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentContainer.addView(view, param);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSkinEventHandler.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //皮肤相关，此通知放在此处，尽量让子类的view都添加到view树内
        if (mFirstTimeApplySkin) {
            mSkinEventHandler.onViewCreated();
            mFirstTimeApplySkin = false;
        }

        mSwitchView.refreshSwitch();

        mSkinEventHandler.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //皮肤相关
        mSkinEventHandler.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSkinEventHandler.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSkinEventHandler.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //皮肤相关
        mSkinEventHandler.onDestroy();
    }

    @Override
    public boolean isSupportSkinChange() {
        //告知当前界面是否支持换肤：true支持换肤，false不支持
        return true;
    }

    @Override
    public boolean isSwitchSkinImmediately() {
        //告知当切换皮肤时，是否立刻刷新当前界面；true立刻刷新，false表示在界面onResume时刷新；
        //减轻换肤时性能压力
        return false;
    }

    @Override
    public void handleSkinChange() {
        //当前界面在换肤时收到的回调，可以在此回调内做一些其他事情；
        //比如：通知WebView内的页面切换到夜间模式等
    }

    /**
     * 告知当前界面Window的background资源，换肤时会寻找对应的资源替换
     */
    protected int getWindowBackgroundResource() {
        return R.color.activity_bg_color;
    }
}
