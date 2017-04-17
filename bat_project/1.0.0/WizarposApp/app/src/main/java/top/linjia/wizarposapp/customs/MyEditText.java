package top.linjia.wizarposapp.customs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import top.linjia.wizarposapp.utils.MyToast;

public class MyEditText extends EditText{
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;
    private DrawableLeftListener leftListener;
    private DrawableRightListener rightListener;
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setDrawableLeftListener(DrawableLeftListener listener){
        this.leftListener=listener;
    }
    public void setDrawableRightListener(DrawableRightListener listener){
        this.rightListener=listener;
    }
    public interface DrawableLeftListener{
        public void onDrawableLeftClick(View view);
    }
    public interface DrawableRightListener{
        public void onDrawableRightClick(View view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                if(rightListener!=null){
                    Drawable drawableright=getCompoundDrawables()[DRAWABLE_RIGHT];
                    if(drawableright!=null&&event.getRawX()>=getRight()-drawableright.getBounds().width()){
                        rightListener.onDrawableRightClick(this);
                        Log.i("test","drawableright长度：" + drawableright.getBounds().width() + drawableright.getBounds().height());
                        return true;
                    }
                }if(leftListener!=null){
                    Drawable drawableleft=getCompoundDrawables()[DRAWABLE_LEFT];
                if(drawableleft!=null&&event.getRawY()<=getLeft()+drawableleft.getBounds().width()){
                    leftListener.onDrawableLeftClick(this);
                    return true;
                }
            }
                break;
        }
        return super.onTouchEvent(event);
    }
}
