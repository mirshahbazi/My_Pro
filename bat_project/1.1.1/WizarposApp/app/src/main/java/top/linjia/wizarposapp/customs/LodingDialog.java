package top.linjia.wizarposapp.customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import top.linjia.wizarposapp.R;

/**
 * Created by 巧脉 on 2016/10/26 17:23
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 自定义的dialog 负责在activity显示时候提示加载中
 */
public class LodingDialog extends Dialog{

    private TextView mTextView;
    private String msg = "登录中...";
    private Context mContext;
    private boolean isBreack = true;

    public LodingDialog(Context context) {
        super(context,R.style.MyDialog);
        this.mContext = context;
    }
    public CacelDialogEvent mCacelDialogEvent = new CacelDialogEvent() {
        @Override
        public void cacelEvent() {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
        initView();
        initData();
    }

    public void setCacelDialogEvent(CacelDialogEvent mCacelDialogEvent){
        this.mCacelDialogEvent = mCacelDialogEvent;
    }

    public boolean isBreack() {
        return isBreack;
    }

    public void setBreack(boolean breack) {
        isBreack = breack;
    }

    @Override
    public void onBackPressed() {
        mCacelDialogEvent.cacelEvent();
        if(isBreack){

            super.onBackPressed();
        }
    }

    private void initData() {
        mTextView.setText(msg);
    }

    /**
     * 设置Dialog中message的内容
     * @param msg
     */
    public void setmTextView(String msg){
        this.msg = msg;
    }


    /**
     * 初始化view
     */
    private void initView() {
        mTextView = (TextView) findViewById(R.id.dialog_mag);
    }

    public interface CacelDialogEvent{
        void cacelEvent();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp =  getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
    }
}
