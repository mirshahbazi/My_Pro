package top.linjia.wizarposapp.view.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yanzhenjie.recyclerview.swipe.ResCompat;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.global.WizarPosApp;

/**
 * @className: top.linjia.wizarposapp.view.recyclerview ListViewDecoration
 * @description: 只定义recycler对recyclerview添加左右两边的菜单 以备以后使用
 * @author 陈文豪
 * @crete 2017/2/16 11:04
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class ListViewDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;

    public ListViewDecoration() {
        mDrawable = ResCompat.getDrawable(WizarPosApp.mContext, R.drawable.divider_recycler);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 以下计算主要用来确定绘制的位置
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
    }
}
