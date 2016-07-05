package com.dyzs.customcloundtags.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * create by maidou
 */
public class CustomTagsLayoutV2 extends ViewGroup {
    private static final int DEFAULT_SPACING = 10;
    private int horizontalSpacing = DEFAULT_SPACING;            // lineView 之间的水平间距
    private int verticalSpacing = DEFAULT_SPACING;              // TextView 之间的垂直间距

    private ArrayList<Line> lineList = new ArrayList<CustomTagsLayoutV2.Line>();
    public CustomTagsLayoutV2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomTagsLayoutV2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomTagsLayoutV2(Context context) {
        super(context);
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        if (horizontalSpacing > 0) {
            this.horizontalSpacing = horizontalSpacing;
        }
    }

    public void setVerticalSpacing(int verticalSpacing) {
        if (verticalSpacing > 0) {
            this.verticalSpacing = verticalSpacing;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        lineList.clear();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
        Line line = new Line();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(0, 0);
            if (line.getViewList().size() == 0) {
                line.addLineView(childView);
            } else if (line.getWidth() + horizontalSpacing + childView.getMeasuredWidth() > noPaddingWidth) {
                lineList.add(line);
                line = new Line();

                line.addLineView(childView);
            } else {
                line.addLineView(childView);
            }
            if (i == (getChildCount() - 1)) {
                lineList.add(line);
            }
        }

        int height = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < lineList.size(); i++) {
            height += lineList.get(i).getHeight();
        }
        height += (lineList.size() - 1) * verticalSpacing;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            if (i > 0) {
                paddingTop += lineList.get(i - 1).getHeight() + verticalSpacing;
            }
            ArrayList<View> viewList = line.getViewList();
            int remainSpacing = getLineRemainSpacing(line);
            float perSpacing = remainSpacing / viewList.size();
            for (int j = 0; j < viewList.size(); j++) {
                View childView = viewList.get(j);
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childView.getMeasuredWidth() + perSpacing), MeasureSpec.EXACTLY);
                childView.measure(widthMeasureSpec, 0);
                if (j == 0) {
                    childView.layout(paddingLeft, paddingTop, paddingLeft + childView.getMeasuredWidth(),
                            paddingTop + childView.getMeasuredHeight());
                } else {
                    View preView = viewList.get(j - 1);
                    int left = preView.getRight() + horizontalSpacing;
                    childView.layout(left, preView.getTop(), left + childView.getMeasuredWidth(),
                            preView.getBottom());
                }
            }
        }
    }

    /**
     * 获取line扣除textview后的剩余区域
     *
     * @param line
     * @return
     */
    private int getLineRemainSpacing(Line line) {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - line.getWidth();
    }

    class Line {
        private ArrayList<View> viewList = new ArrayList<View>();        // 用来记录当前行的所以 TextView
        private int width;              // 表示当前行所以的 TextView的宽，还有他们之间的水平间距
        private int height;             // 当前行的高度

        /**
         * 获取当前 Line 中的所有 TextView
         *
         * @return
         */
        public ArrayList<View> getViewList() {
            return viewList;
        }

        /**
         * 获取当前 Line 的宽度
         *
         * @return
         */
        public int getWidth() {
            return width;
        }

        /**
         * 获取当前 Line 的高度
         *
         * @return
         */
        public int getHeight() {
            return height;
        }

        /**
         * 添加一个 TextView 到集合对象中
         *
         * @param lineView
         */
        public void addLineView(View lineView) {
            if (!viewList.contains(lineView)) {
                viewList.add(lineView);

                // 更新 LineView（TextView）的宽度
                if (viewList.size() == 1) {
                    // 如果是第一个 TextView，那么 width 就是 LineView 的宽度
                    width = lineView.getMeasuredWidth();
                } else {
                    // 如果不是第一个，则在当前 width 的基础上 + 水平间距 + lineView 的宽度
                    width += horizontalSpacing + lineView.getMeasuredWidth();
                }
                /**
                 * 更新高度，在此所有的TextView的高度都是一样的，通过一个高度计算，来获取高度的最大值
                 * 因为当 line 大与一行时，这个时候的高度还是 lineView 的高度，但是 height 就得设置成 lineView 的高度了
                 * 所以 lineView 的高度一直没变，只不过往下偏移了。
                 */
                height = Math.max(height, lineView.getMeasuredHeight());
            }
        }
    }
}
