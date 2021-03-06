package com.dyzs.labels;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dyzs.labels.activity.LabelActivity;
import com.dyzs.labels.utils.ColorUtil;
import com.dyzs.labels.utils.DrawableUtil;
import com.dyzs.labels.utils.ToastUtil;
import com.dyzs.labels.view.LabelsLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private List<String> mList;
    private Context mContext;
    private LabelsLayout CCTLayout;
    private int textHorPadding, textVerPadding;
    private float radius;

    private LinearLayout ll_content;
    private ScrollView sv;


    private TextView tv_jump_to_choose_label, tv_jump_to_choose_new_tag;
//    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        setContentView(R.layout.activity_main);

        initList();

        initView();

        addLayoutDataIntoCCTC();

    }


    private void initView() {
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        sv = (ScrollView) findViewById(R.id.sv);

        CCTLayout = new LabelsLayout(mContext);
        int padding = (int) mContext.getResources().getDimension(R.dimen.cloudlayout_padding);
        CCTLayout.setPadding(padding, padding, padding, padding);
        int spacing = (int) mContext.getResources().getDimension(R.dimen.cloudlayout_spacing);
        CCTLayout.setHorizontalSpacing(spacing);
        CCTLayout.setVerticalSpacing(spacing);
//        CCTLayout.setOnLongClickListener(new CCTLLongClickListener());
        sv.addView(CCTLayout);

        tv_jump_to_choose_label = (TextView) findViewById(R.id.tv_jump_to_choose_tag);
        tv_jump_to_choose_label.setOnClickListener(this);

        tv_jump_to_choose_new_tag = (TextView) findViewById(R.id.tv_jump_to_choose_new_tag);
        tv_jump_to_choose_new_tag.setOnClickListener(this);
    }


    private void addDataIntoCCTC () {
        textHorPadding = (int) mContext.getResources().getDimension(R.dimen.hot_textview_hor_padding);
        textVerPadding = (int) mContext.getResources().getDimension(R.dimen.hot_textview_ver_padding);
        radius = (int) mContext.getResources().getDimension(R.dimen.hot_textview_radius);
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                final TextView textView = new TextView(mContext);
                textView.setText(mList.get(i));
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(16);
                textView.setPadding(textHorPadding, textVerPadding, textHorPadding, textVerPadding);

                Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius);
                Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius);

                textView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, normal));
                CCTLayout.addView(textView);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.makeText(mContext, textView.getText().toString());
                    }
                });
            }
        }

    }

    private void initList() {
        mList = new ArrayList<>();
        mList.add("中华人名共和国");
        mList.add("大韩民国");
        mList.add("日本");
        mList.add("朝鲜");
        mList.add("台湾(20)");
        mList.add("香港特别行政区");
        mList.add("澳门特别行政区");
        mList.add("越南");
        mList.add("老挝");
        mList.add("柬埔寨");
        mList.add("泰国");
        mList.add("缅甸");
        mList.add("马来西亚(10)");
        mList.add("新加坡");
        mList.add("印度尼西亚");
        mList.add("文莱");
        mList.add("菲律宾");
    }

    private void addLayoutDataIntoCCTC() {
        textHorPadding = (int) mContext.getResources().getDimension(R.dimen.hot_textview_hor_padding);
        textVerPadding = (int) mContext.getResources().getDimension(R.dimen.hot_textview_ver_padding);
        radius = (int) mContext.getResources().getDimension(R.dimen.hot_textview_radius);
        int stroke = 2;
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                final RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
                itemView.setPadding(textHorPadding, textVerPadding, textHorPadding, textVerPadding);

                Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius, stroke);
                Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius, stroke);

                itemView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, normal));
                CCTLayout.addView(itemView);

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
                        final ImageView delChild = (ImageView) itemView.findViewById(R.id.iv_del);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_jump_to_choose_new_tag) {
            Intent jump = new Intent(MainActivity.this, LabelActivity.class);
            startActivity(jump);
        }
    }
}