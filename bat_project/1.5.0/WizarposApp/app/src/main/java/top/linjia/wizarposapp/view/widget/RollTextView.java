package top.linjia.wizarposapp.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.widget.TextView;

/**
 * @className: top.linjia.wizarposapp.view.widget RollTextView
 * @description: 实现滚动的Textview
 * @author 陈文豪
 * @crete 2017/2/15 15:45
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class RollTextView extends TextView{
    public RollTextView(Context context) {
        super(context);
    }

    public RollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
