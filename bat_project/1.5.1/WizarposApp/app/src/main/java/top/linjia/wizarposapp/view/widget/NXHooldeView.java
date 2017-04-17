package top.linjia.wizarposapp.view.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

/**
 * @className: view NXHooldeView
 * @description: 自定义textview
 * @author 陈文豪
 * @crete 2017/2/14 11:03
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class NXHooldeView extends ImageView implements ValueAnimator.AnimatorUpdateListener {

    public static final int VIEW_SIZE = 20;

    protected Context mContext;
    protected Paint mPaint4Circle;
    protected int radius;

    protected Point startPosition;
    protected Point endPosition;


    /**
     * 继承了Imageview三种构造方法
     * @param context
     */
    public NXHooldeView(Context context) {
        this(context, null);
    }

    public NXHooldeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NXHooldeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mPaint4Circle = new Paint();
        mPaint4Circle.setColor(Color.RED);
        mPaint4Circle.setAntiAlias(true);
    }

    /**
     * 设置动画开始的位置
     * @param startPosition
     */
    public void setStartPosition(Point startPosition) {
        startPosition.y -= 10;
        startPosition.x += 10;
        this.startPosition = startPosition;
    }

    /**
     * 设置动画结束的位置
     * @param endPosition
     */
    public void setEndPosition(Point endPosition) {
        this.endPosition = endPosition;
    }


    /**
     * 测量控件
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int PX4SIZE = (int) convertDpToPixel(VIEW_SIZE, mContext);
        setMeasuredDimension(PX4SIZE, PX4SIZE);
        radius = PX4SIZE / 2;
    }

    /**
     * 绘制控件
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, mPaint4Circle);
        super.onDraw(canvas);
    }


    /**
     * 开始执行动画
     */
    public void startBeizerAnimation() {
        if (startPosition == null || endPosition == null) return;
        int pointX = (startPosition.x + endPosition.x) / 2;
        int pointY = (int) (startPosition.y - convertDpToPixel(100, mContext));
        Point controllPoint = new Point(pointX, pointY);
        BezierEvaluator bezierEvaluator = new BezierEvaluator(controllPoint);
        ValueAnimator anim = ValueAnimator.ofObject(bezierEvaluator, startPosition, endPosition);
        anim.addUpdateListener(this);
        anim.setDuration(400);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup viewGroup = (ViewGroup) getParent();
                viewGroup.removeView(NXHooldeView.this);
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Point point = (Point) animation.getAnimatedValue();
        setX(point.x);
        setY(point.y);
        invalidate();
    }


    /**
     * @className: top.linjia.wizarposapp.view.widget NXHooldeView
     * @description: 创建动画估值器
     * @author 陈文豪
     * @crete 2017/2/14 16:06
     * @copyright: 2017 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    public class BezierEvaluator implements TypeEvaluator<Point> {

        private Point controllPoint;

        public BezierEvaluator(Point controllPoint) {
            this.controllPoint = controllPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
            int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
            return new Point(x, y);
        }
    }

    /**
     *
     * @param dp
     * @param context
     * @return float
     *  提供dp转px的算法
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
