package com.dyzs.customcloundtags.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.dyzs.customcloundtags.R;
import com.dyzs.customcloundtags.adapter.TagsAdapter;
import com.dyzs.customcloundtags.entity.TagsItemInfo;

import java.util.ArrayList;

/**
 * Created by maidou on 2016/7/4.
 * 简单的瀑布流
 *
 * @deprecated give it up
 */
public class TagOperatorActivity extends Activity implements View.OnClickListener{
    public TagOperatorActivity() {}
    private static final String TAG = TagOperatorActivity.class.getSimpleName();
    private static final String[] TEMP_DATAS = {"生活", "工作", "睡前小故事", "玩游戏",
            "喝水了", "睡前小故事", "喝水了", "去鸟巢", "喝水了", "去鸟巢",
            "睡前小故事", "去鸟巢", "喝水了", "睡前小故事睡前小故事睡前小故事睡前小故事", "喝水了", "睡前小故事",
            "生活", "睡前小故事", "睡前小故事", "玩游戏"
    };

    private Context mContext;


    private RecyclerView mRvAddedTagsList;
    private TagsAdapter mAddedAdapter;
    private ArrayList<TagsItemInfo> mRvDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_tag_operator);
        mContext = this;
        preparedListData();

        initView();


    }

    private void preparedListData() {
        mRvDataList = new ArrayList<>();
        TagsItemInfo info;
        for (int i = 0; i < TEMP_DATAS.length; i ++) {
            info = new TagsItemInfo();
            info.isSelected = false;
            info.isInEditing = false;
            info.tagName = TEMP_DATAS[i];
            mRvDataList.add(info);
        }
    }

    private void initView() {
        mRvAddedTagsList = (RecyclerView) findViewById(R.id.rv_added_tags_list);
        mRvAddedTagsList.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL);
        mRvAddedTagsList.setLayoutManager(layoutManager);
        mAddedAdapter = new TagsAdapter(mContext, mRvDataList);
        mAddedAdapter.setHandleFilterListener(new TagsAdapter.HandleFilterListener() {
            @Override
            public void handleFilter(int position) {
                for (int i = 0 ; i < mRvDataList.size(); i ++) {
                    mRvDataList.get(i).isSelected = false;
                }
                mRvDataList.get(position).isSelected = true;
            }
        });
        mRvAddedTagsList.setAdapter(mAddedAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
