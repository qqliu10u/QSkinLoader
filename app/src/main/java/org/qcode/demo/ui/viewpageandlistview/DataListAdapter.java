package org.qcode.demo.ui.viewpageandlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.qcode.qskinloader.SkinManager;
import org.qcode.skintestdemo.R;

import java.util.ArrayList;

/**
 * qqliu
 * 2016/9/9.
 */
public class DataListAdapter extends BaseAdapter {
    private static final String TAG = "MyListAdapter";
    private final String mOutterIdentifier;
    private Context mContext;

    private ArrayList<String> mList = new ArrayList<String>();

    public DataListAdapter(Context context, String outterIdentifier) {
        mContext = context;
        mOutterIdentifier = outterIdentifier;
        for (int i = 0; i < 1000; i++) {
            mList.add("测试" + i);
        }
    }

    public String getIdentifier() {
        return mOutterIdentifier;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_view, null);
        }
        //在Adapter getView处应用处理,保证动态添加的View刷新了皮肤
        SkinManager.getInstance().applySkin(convertView, true);

        TextView txtView = (TextView) convertView.findViewById(R.id.list_item_text_view);
        txtView.setText(mOutterIdentifier + " " + mList.get(position));

        return convertView;
    }
}
