package org.qcode.demo.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.qcode.demo.utils.UIUtil;
import org.qcode.qskinloader.SkinManager;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.skintestdemo.R;

import java.util.ArrayList;

/**
 * qqliu
 * 2016/9/11.
 */
public class DataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TEXT_ID = 0x5f000031;
    private final Context mContext;

    private ArrayList<String> mList = new ArrayList<String>();

    public DataRecyclerViewAdapter(Context context) {
        mContext = context;

        for (int i = 0; i < 1000; i++) {
            mList.add("测试" + i);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = newItem();
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView.findViewById(TEXT_ID);
        textView.setText(mList.get(position));
        SkinManager.getInstance().applySkin(holder.itemView, true);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public View newItem() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundResource(R.color.color_background);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams paramImg =
                new LinearLayout.LayoutParams(
                        UIUtil.dip2px(mContext, 50), UIUtil.dip2px(mContext, 50));
        imageView.setImageResource(R.mipmap.ic_launcher);
        linearLayout.addView(imageView, paramImg);

        TextView textView = new TextView(mContext);
        textView.setId(TEXT_ID);
        LinearLayout.LayoutParams paramText =
                new LinearLayout.LayoutParams(
                        UIUtil.dip2px(mContext, 100), UIUtil.dip2px(mContext, 50));
        textView.setTextColor(mContext.getResources().getColor(R.color.color_text));
        linearLayout.addView(textView, paramText);

        SkinManager
                .with(linearLayout)
                .setViewAttrs(SkinAttrName.BACKGROUND, R.color.color_background);

        SkinManager
                .with(textView)
                .setViewAttrs(SkinAttrName.TEXT_COLOR, R.color.color_text);

        return linearLayout;
    }
}
