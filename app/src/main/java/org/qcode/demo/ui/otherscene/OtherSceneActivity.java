package org.qcode.demo.ui.otherscene;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.qcode.demo.BaseActivity;
import org.qcode.demo.ui.customattr.DefBackgroundAttrHandler;
import org.qcode.demo.utils.UIUtil;
import org.qcode.qskinloader.SkinManager;
import org.qcode.qskinloader.entity.DynamicAttr;
import org.qcode.skintestdemo.R;

import static org.qcode.demo.ui.customattr.DefBackgroundAttrHandler.DEF_BACKGROUND;

/**
 * qqliu
 * 2016/10/13.
 */

public class OtherSceneActivity extends BaseActivity {
    private PopupWindow mPopWindow;
    private Dialog mDialog;
    private FloatView mFloatView;
    private boolean mIsShowingFloatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SkinManager.getInstance().registerSkinAttrHandler(
                SpannableSkinAttr.HIGHLIGHT_SPANNABLE, new SpannableSkinAttrHandler());

        setContentView(R.layout.activity_other_scene);

        TextView textView = (TextView) findViewById(R.id.textviewSpannableSkin);

        DynamicAttr dynamicAttr = new SpannableSkinAttr(
                textView.getText().toString(), R.color.color_red);
        SkinManager.with(textView).addViewAttrs(dynamicAttr);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPopWindow:
                View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow, null);
                //对所有不在Activity的Content下的View（PopWindow/Dialog/Float View等）都可以应用
                //此方法将View添加到SkinManager内维护
                //注意，虽然框架内对View采用了弱引用，但是建议此方法配合remove或clear方法一起使用，释放对View的静态引用
                SkinManager.getWindowViewManager()
                        .addWindowView(contentView)
                        .applySkinForViews(true);

                if (null == mPopWindow) {
                    mPopWindow = new PopupWindow(
                            contentView,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                }

                if (!mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(view);
                } else {
                    mPopWindow.dismiss();
                }

                break;


            case R.id.btnDialog:
                if (null == mDialog) {
                    mDialog = new CustomDialog(this);
                }

                if (!mDialog.isShowing()) {
                    mDialog.show();
                } else {
                    mDialog.dismiss();
                }
                break;


            case R.id.btnFloatWindow:
                if (null == mFloatView) {
                    mFloatView = new FloatView(this);
                    mFloatView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mIsShowingFloatView = false;
                            hideFloatView();
                        }
                    });
                }

                mIsShowingFloatView = !mIsShowingFloatView;
                if (mIsShowingFloatView) {
                    showFloatView();
                } else {
                    hideFloatView();
                }
                break;

            case R.id.btnPopWindowClick:
                findViewById(R.id.btnPopWindow).performClick();
                Toast.makeText(this, "popWindow点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showFloatView() {
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        int width = UIUtil.dip2px(this, 50);
        layoutParams.width = width;
        layoutParams.height = width;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.format = PixelFormat.TRANSLUCENT; // 设置图片格式，效果为背景透明
        windowManager.addView(mFloatView, layoutParams);
        mFloatView.show();
    }

    private void hideFloatView() {
        WindowManager windowManager = getWindowManager();
        windowManager.removeView(mFloatView);
        mFloatView.dismiss();
    }

    @Override
    protected int getWindowBackgroundResource() {
        return R.color.color_transprent;
    }

    @Override
    public boolean isSwitchSkinImmediately() {
        //悬浮窗会导致Activity失去Focus,但是悬浮窗又是半透明的,所以此处建议立刻切换皮肤
        return true;
    }
}
