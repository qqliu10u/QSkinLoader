package org.qcode.demo.ui.dynamicaddview;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.qcode.demo.BaseActivity;
import org.qcode.demo.ui.viewpageandlistview.DataListAdapter;
import org.qcode.demo.utils.UIUtil;
import org.qcode.skintestdemo.R;

public class DynamicAddViewActivity extends BaseActivity {

    public static final int SHOW_COUNT = 40;
    private LinearLayout mContainer;
    private DataListAdapter mAdapter;
    private int mCount;
    private boolean mIsDestroying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_add_view);
        mContainer = (LinearLayout) findViewById(R.id.dynamic_container);

        mAdapter = new DataListAdapter(this, "");

        mContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mIsDestroying) {
                    return;
                }
                if(mCount >= 25) {
                    UIUtil.showToast(DynamicAddViewActivity.this, "添加完毕");
                    return;
                }

                for (int i = SHOW_COUNT * mCount; i < SHOW_COUNT * (mCount + 1); i++) {
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(getApplicationContext(), 50)
                    );
                    mContainer.addView(mAdapter.getView(i, null, null), param);
                }

                mCount++;
                mContainer.postDelayed(this, 200);
            }
        }, 200);
    }

    @Override
    protected void onDestroy() {
        mIsDestroying = true;
        super.onDestroy();
    }
}
