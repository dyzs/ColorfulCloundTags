package com.dyzs.labels.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyzs.labels.R;
import com.dyzs.labels.entity.LabelsItemInfo;

import java.util.ArrayList;

/**
 * Created by maidou on 2016/3/28.
 */
public class LabelsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<LabelsItemInfo> mList;

    private int mHeight, mWidth;
    public LabelsAdapter(Context context, ArrayList<LabelsItemInfo> list) {
        this.context = context;
        this.mList = list;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tags, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder h = (MyHolder) holder;
        String name = mList.get(position).tagName;
        h.text.setText(name);
//        if (filterSampleIconBitmap != null && !filterSampleIconBitmap.isRecycled()) {
//            filterSampleIconBitmap.isRecycled();
//        }
//        filterSampleIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pic_icon_filter_sample);
//        h.icon.setCornerRadius(10f);
//        h.icon.setSelectedBitmap(mSelectedBitmap);
//        if (position == 0) {
//            h.icon.setSmallBitmap(filterSampleIconBitmap);
//        }
//        else {
//            h.icon.setSmallBitmap(PhotoProcessing.filterPhoto(filterSampleIconBitmap, position));
//        }
//        if (mList.get(position).isSelected) {
//
//        } else {
//
//        }
//        h.icon.setOnClickListener(new FilterClickListener(position));


        mHeight = h.itemView.getMeasuredHeight();
        mWidth = h.itemView.getMeasuredWidth();
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) h.itemView.getLayoutParams();
//        lp.width = mWidth;
//        h.itemView.setLayoutParams(lp);
    }
    private class FilterClickListener implements View.OnClickListener{
        private int clickPosition;
        public FilterClickListener(int position) {
            this.clickPosition = position;
        }
        @Override
        public void onClick(View v) {
            if (mHandleFilterListener != null) {
                mHandleFilterListener.handleFilter(clickPosition);
            }
        }
    }
    public static class MyHolder extends RecyclerView.ViewHolder {
        public ImageView del;
        public TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            this.del = (ImageView) itemView.findViewById(R.id.iv_item_del);
            this.text = (TextView) itemView.findViewById(R.id.tv_item_content);
        }
    }

    /**
     * 处理点击事件的监听接口
     */
    private HandleFilterListener mHandleFilterListener;
    public void setHandleFilterListener(HandleFilterListener mHandleFilterListener) {this.mHandleFilterListener = mHandleFilterListener;}
    public interface HandleFilterListener {
        void handleFilter(int position);
    }
}