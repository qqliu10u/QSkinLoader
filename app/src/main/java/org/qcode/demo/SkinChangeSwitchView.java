package org.qcode.demo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.qcode.demo.skin.SkinChangeHelper;
import org.qcode.qskinloader.SkinManager;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.skintestdemo.R;


public class SkinChangeSwitchView extends LinearLayout {

    private static final String TAG = "NightModeSettingView";

    private ImageView mImgBtnSwitch;
    private TextView mTextViewTitle;

    public SkinChangeSwitchView(Context context) {
        super(context);
        initView(context);
    }

    protected void initView(final Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        SkinManager.with(this)
                .setViewAttrs(SkinAttrName.BACKGROUND, R.color.color_background)
                .applySkin(true);

        mTextViewTitle = new TextView(context);
        SkinManager.with(mTextViewTitle)
                .setViewAttrs(SkinAttrName.TEXT_COLOR, R.color.color_text)
                .applySkin(false);
        mTextViewTitle.setTextSize(16);
        LinearLayout.LayoutParams paramTitlePart = new LinearLayout.LayoutParams(
                0, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramTitlePart.weight = 1;
        addView(mTextViewTitle, paramTitlePart);
        mTextViewTitle.setText("夜间模式");

        mImgBtnSwitch = new ImageView(context);
        LinearLayout.LayoutParams paramEntryFlag = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addView(mImgBtnSwitch, paramEntryFlag);

        refreshSwitch();

        mImgBtnSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SkinChangeHelper.getInstance().switchSkinMode(
                        new SkinChangeHelper.OnSkinChangeListener() {
                            @Override
                            public void onSuccess() {
                                refreshSwitch();
                            }

                            @Override
                            public void onError() {
                                refreshSwitch();
                            }
                        });

                refreshSwitch();
            }
        });
    }

    public void refreshSwitch() {
        boolean isDefaultMode = SkinChangeHelper.getInstance().isDefaultMode();
        boolean isSwitching = SkinChangeHelper.getInstance().isSwitching();
        int drawableId;
        if (isDefaultMode) {
            //mImgBtnSwitch.setImageResource(R.drawable.news_switch_setting_off_nor);
            drawableId = R.mipmap.news_switch_setting_off_nor;
        } else {
            //mImgBtnSwitch.setImageResource(R.drawable.news_switch_setting_on_nor);
            drawableId = R.mipmap.news_switch_setting_on_nor;
        }
        SkinManager.with(mImgBtnSwitch)
                .setViewAttrs(SkinAttrName.SRC, drawableId)
                .applySkin(false);
        mImgBtnSwitch.setEnabled(!isSwitching);
    }
}
