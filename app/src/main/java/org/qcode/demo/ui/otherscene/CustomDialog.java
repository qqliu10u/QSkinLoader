package org.qcode.demo.ui.otherscene;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

import org.qcode.qskinloader.SkinManager;
import org.qcode.skintestdemo.R;

/**
 * qqliu
 * 2016/10/14.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    private WrapperDismissListener mDismissListener;

    public CustomDialog(Context context) {
        super(context);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_dialog_custom);

        findViewById(R.id.btn_dialog_confirm).setOnClickListener(this);
        findViewById(R.id.btn_dialog_cancel).setOnClickListener(this);

//        //方案二：---------------------------------
//        mDismissListener = new WrapperDismissListener();
//        super.setOnDismissListener(mDismissListener);
//        //如果换肤过程中对话框需要展示，则需要将对话框的View
//        //添加到框架的WindowViewManager中存储;
//        //建议addWindowView与removeWindowView成对使用
//        SkinManager
//                .getWindowViewManager()
//                .addWindowView(findViewById(android.R.id.content))
//                .applySkinForViews(true);
    }

    @Override
    public void show() {
        super.show();

        //方案一：---------------------------------
        //如果换肤的过程中对话框不会展示，则在Dialog初始化时
        //按照当前皮肤设置应用一次皮肤即可
        SkinManager.getInstance().applySkin(
                findViewById(android.R.id.content), true);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        mDismissListener.setDismissListener(listener);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_confirm:
            case R.id.btn_dialog_cancel:
                dismiss();
                break;
        }
    }

    //代理外部的OnDismissListener
    private static class WrapperDismissListener implements OnDismissListener {

        private OnDismissListener mOuterListener;

        void setDismissListener(OnDismissListener listener) {
            mOuterListener = listener;
        }

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if(null != mOuterListener) {
                mOuterListener.onDismiss(dialogInterface);
            }

            if(!(dialogInterface instanceof CustomDialog)) {
                return;
            }

            CustomDialog dialog = (CustomDialog) dialogInterface;

            //与addWindowView成对使用
            SkinManager.getWindowViewManager()
                    .removeWindowView(dialog.findViewById(android.R.id.content));
        }
    }

}
