package org.qcode.demo.ui.gridview;

import android.os.Bundle;
import android.widget.GridView;

import org.qcode.demo.BaseActivity;
import org.qcode.demo.ui.viewpageandlistview.DataListAdapter;
import org.qcode.skintestdemo.R;

public class GridViewActivity extends BaseActivity{

    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        mGridView = (GridView)findViewById(R.id.grid_view);
        mGridView.setAdapter(new DataListAdapter(this, ""));
    }
}
