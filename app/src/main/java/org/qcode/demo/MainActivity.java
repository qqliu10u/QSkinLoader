package org.qcode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.qcode.demo.ui.customattr.CustomAttrViewActivity;
import org.qcode.demo.ui.dynamicaddview.DynamicAddViewActivity;
import org.qcode.demo.ui.gridview.GridViewActivity;
import org.qcode.demo.ui.otherscene.OtherSceneActivity;
import org.qcode.demo.ui.recyclerview.RecyclerViewActivity;
import org.qcode.demo.ui.viewpageandlistview.ViewPagerAndListViewActivity;
import org.qcode.skintestdemo.R;

/**
 * qqliu
 * 2016/10/10.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnCustomView:
                intent.setClass(this, CustomAttrViewActivity.class);
                break;
            case R.id.btnDynamicAddView:
                intent.setClass(this, DynamicAddViewActivity.class);
                break;
            case R.id.btnRecyclerView:
                intent.setClass(this, RecyclerViewActivity.class);
                break;
            case R.id.btnViewPagerAndListView:
                intent.setClass(this, ViewPagerAndListViewActivity.class);
                break;
            case R.id.btnGridView:
                intent.setClass(this, GridViewActivity.class);
                break;
            case R.id.btnOtherScene:
                intent.setClass(this, OtherSceneActivity.class);
                break;
        }
        startActivity(intent);
    }
}
