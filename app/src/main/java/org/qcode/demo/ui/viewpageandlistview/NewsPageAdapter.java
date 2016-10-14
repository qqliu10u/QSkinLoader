package org.qcode.demo.ui.viewpageandlistview;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import org.qcode.qskinloader.base.utils.Logging;

import java.util.ArrayList;

public class NewsPageAdapter extends RecyclablePageAdapter<ListView> {
    private static final String TAG = "NewsPageAdapter";

    private Context mContext;

    private ArrayList<String> mList = new ArrayList<String>();

    public NewsPageAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 15; i++) {
            mList.add("测试" + i);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        if (view instanceof ListView && null != ((ListView) view).getAdapter()) {
            return obj.equals(((DataListAdapter) ((ListView) view).getAdapter()).getIdentifier());
        }
        return false;
    }

    @Override
    public int getItemPosition(Object object) {
        super.getItemPosition(object);

        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).equals(object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected Object getItemObject(int position) {
        return mList.get(position);
    }

    @Override
    protected ListView createItemView(int itemViewType) {
        ListView listView = new ListView(mContext);
        return listView;
    }

    @Override
    protected void onBindView(ListView itemView, int position, int itemViewType) {
        Logging.d(TAG, "onBindView()| position= " + position);

        String str = mList.get(position);
        DataListAdapter adapter = new DataListAdapter(mContext, str);
        itemView.setAdapter(adapter);
    }

    @Override
    protected void destroyItemView(ListView view) {

    }
}
