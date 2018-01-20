package com.dyzs.labels.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dyzs.labels.R;
import com.dyzs.labels.adapter.LabelsAdapter;
import com.dyzs.labels.entity.LabelsItemInfo;
import com.dyzs.labels.utils.ColorUtil;
import com.dyzs.labels.utils.DrawableUtil;
import com.dyzs.labels.utils.ToastUtil;
import com.dyzs.labels.view.LabelsLayout;

import java.util.ArrayList;

/**
 * Created by maidou on 2016/7/4.
 */
public class LabelActivity extends Activity implements View.OnClickListener{
    public LabelActivity() {}
    private static final String TAG = LabelActivity.class.getSimpleName();
    private static final String[] TEMP_DATAS = {"生活", "工作", "睡前小故事", "玩游戏",
            "喝水了", "睡前小故事", "喝水了", "去鸟巢", "喝水了", "去鸟巢",
            "睡前小故事", "去鸟巢", "喝水了", "故事睡前小故事睡前小故事", "喝水了", "睡前小故事",
            "生活", "睡前小故事", "睡前小故事", "玩游戏"
    };

    private Context mContext;

    private RecyclerView mRvAddedLabelsList;
    private LabelsAdapter mAddedAdapter;
    private ArrayList<LabelsItemInfo> mDataList;

    private ScrollView sv_labels_container;
    private LabelsLayout labelsLayout;
    private int textHorPadding, textVerPadding;
    private float radius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_label);
        mContext = this;
        preparedListData();

        initView();

        addLayoutDataIntoCCTC();

    }

    private void addLayoutDataIntoCCTC() {
        textHorPadding = (int) mContext.getResources().getDimension(R.dimen.hot_textview_hor_padding);
        textVerPadding = (int) mContext.getResources().getDimension(R.dimen.hot_textview_ver_padding);
        radius = (int) mContext.getResources().getDimension(R.dimen.hot_textview_radius);
        int stroke = 2;

        TextView tag;
        ImageView del;
        LabelsItemInfo info;
        if (mDataList != null) {
            for (int i = 0; i < mDataList.size(); i++) {
                info = mDataList.get(i);
                final RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_tags, null);
                itemView.setPadding(textHorPadding, textVerPadding, textHorPadding, textVerPadding);

                tag = (TextView) itemView.findViewById(R.id.tv_item_content);
                del = (ImageView) itemView.findViewById(R.id.iv_item_del);

                tag.setText(info.tagName);

                Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius, stroke, DrawableUtil.STROKE_DEEPLY);
                Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius, stroke, DrawableUtil.STROKE_DEEPLY);


                itemView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, normal));
                labelsLayout.addView(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.makeText(mContext, "i:");
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ToastUtil.makeText(mContext, "item view long click");
                        final ImageView delChild = (ImageView) itemView.findViewById(R.id.iv_item_del);
                        delChild.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.makeText(mContext, "del click");
                            }
                        });
                        delChild.setVisibility(View.VISIBLE);
                        return true;
                    }
                });
            }
        }
    }

    private void preparedListData() {
        mDataList = new ArrayList<>();
        LabelsItemInfo info;
        for (int i = 0; i < TEMP_DATAS.length; i ++) {
            info = new LabelsItemInfo();
            info.isSelected = false;
            info.isInEditing = false;
            info.tagName = TEMP_DATAS[i];
            mDataList.add(info);
        }
    }

    private void initView() {
        mRvAddedLabelsList = (RecyclerView) findViewById(R.id.rv_added_labels_list);
        mRvAddedLabelsList.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRvAddedLabelsList.setLayoutManager(layoutManager);
        mAddedAdapter = new LabelsAdapter(mContext, mDataList);
        mAddedAdapter.setHandleFilterListener(new LabelsAdapter.HandleFilterListener() {
            @Override
            public void handleFilter(int position) {
                for (int i = 0 ; i < mDataList.size(); i ++) {
                    mDataList.get(i).isSelected = false;
                }
                mDataList.get(position).isSelected = true;
            }
        });
        mRvAddedLabelsList.setAdapter(mAddedAdapter);

        sv_labels_container = (ScrollView) findViewById(R.id.sv_labels_container);
        labelsLayout = new LabelsLayout(mContext);
        int padding = (int) mContext.getResources().getDimension(R.dimen.cloudlayout_padding);
        labelsLayout.setPadding(padding, padding, padding, padding);

        int spacing = (int) mContext.getResources().getDimension(R.dimen.cloudlayout_spacing);
        labelsLayout.setHorizontalSpacing(spacing);
        labelsLayout.setVerticalSpacing(spacing);

        sv_labels_container.addView(labelsLayout);
    }

    @Override
    public void onClick(View v) {

    }
}
