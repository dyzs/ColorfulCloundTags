package com.dyzs.labels.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * create by maidou
 */
public class LabelsLayout extends ViewGroup {
    private static final int DEFAULT_SPACING = 10;
    private int horizontalSpacing = DEFAULT_SPACING;            // lineView 之间的水平间距
    private int verticalSpacing = DEFAULT_SPACING;              // TextView 之间的垂直间距

    private ArrayList<Line> lineList = new ArrayList<LabelsLayout.Line>();
    public LabelsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public LabelsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public LabelsLayout(Context context) {
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
//			childView.getViewTreeObserver().addOnGlobalLayoutListener(listener)// 这个添加的是全局的布局监听
            // 如果当前 Line 中没有 TextView ，则直接放入当前 Line 中
            if (line.getViewList().size() == 0) {
                line.addLineView(childView);
            } else if (line.getWidth() + horizontalSpacing + childView.getMeasuredWidth() > noPaddingWidth) {
                // 如果当前line的宽+水平间距+childView的宽大于noPaddingWidth，则换行
                lineList.add(line); // 换行之前先保存之前的 line 对象
                line = new Line();  // 重新创建 line

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
            // += 完上下的padding值后，再把每个 line 的高度加进来
            height += lineList.get(i).getHeight();
        }
        // 再在 height 上加上每个 line 的间距
        height += (lineList.size() - 1) * verticalSpacing;
        setMeasuredDimension(width, height);
        // System.out.println("onMeasure width:" + width + "/height:" + height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);    // 获取每个 line 对象

            // 从第二行开始，他们的top总是比上一行多一个行高+垂直间距
            if (i > 0) {
                paddingTop += lineList.get(i - 1).getHeight() + verticalSpacing;
            }
            // 因为我们要获取的是每个 line 里面的 textview，所以通过 getViewList
            ArrayList<View> viewList = line.getViewList();
            // 计算出当前 line 的剩余空间区域的值
            int remainSpacing = getLineRemainSpacing(line);
            // 计算每个 TextView 分配留白（剩余空间）的值
            float perSpacing = remainSpacing / viewList.size();
            // 因为我们要获取的是每个 line 里面的 textview，所以通过 getViewList
            for (int j = 0; j < viewList.size(); j++) {
                View childView = viewList.get(j);    // 获取每个 textView
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childView.getMeasuredWidth() + perSpacing), MeasureSpec.EXACTLY);
                childView.measure(widthMeasureSpec, 0);

                if (j == 0) {    // 表示当前是第一个 TextView
                    // 摆放每行的第一个 TextView
                    childView.layout(paddingLeft, paddingTop, paddingLeft + childView.getMeasuredWidth(),
                            paddingTop + childView.getMeasuredHeight());
                } else {
                    // 摆放后面的 textView ，需要参照前一个 View
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
