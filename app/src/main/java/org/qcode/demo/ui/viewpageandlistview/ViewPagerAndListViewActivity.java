package org.qcode.demo.ui.viewpageandlistview;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.qcode.demo.BaseActivity;
import org.qcode.skintestdemo.R;

public class ViewPagerAndListViewActivity extends BaseActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_listview);
        mViewPager = (ViewPager) findViewById(R.id.home_news_viewpager);
        mViewPager.setAdapter(new NewsPageAdapter(this));
    }
}
