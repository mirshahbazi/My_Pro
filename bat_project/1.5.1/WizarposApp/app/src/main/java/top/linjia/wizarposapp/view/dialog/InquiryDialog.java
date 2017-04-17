package top.linjia.wizarposapp.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import top.linjia.wizarposapp.R;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.view.dialog InquiryDialog
 * @description: 询问用户是否的dialog管理类
 * @crete 2017/2/10 10:49
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class InquiryDialog implements View.OnClickListener {
    private static InquiryDialog mInquiryDialog = new InquiryDialog();
    private Context mContext;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private Button dialog_cancel;
    private Button dialog_enter;
    private IsEnter mIsEnter;
    private TextView dialog_msg;


    /**
     * @param mContext
     * @return InquiryDialog
     * @Title: InquiryDialog
     * @Description: 获取一个操作dialog的对象
     * @date 2017/2/10 10:50
     * @author 陈文豪
     */
    public static InquiryDialog getInquiryDialog(Context mContext) {
        mInquiryDialog.mContext = mContext;
        return mInquiryDialog;
    }

    public void setIsEnter(IsEnter mIsEnter){
        mInquiryDialog.mIsEnter = mIsEnter;
    }

    /**
     * @Title: prepareShow
     * @Description: 准备显示dialog
     * @param msg
     * @date 2017/2/10 10:56
     * @author 陈文豪
     */
    public void prepareShow(String msg) {
        mBuilder = new AlertDialog.Builder(mContext);
        View dialogView = getDialogView(msg);
        mBuilder.setView(dialogView);
        mBuilder.setCancelable(false);
        mAlertDialog = mBuilder.create();
    }

    /**
     * @Title: showDialog
     * @Description: 显示对话框
     * @date 2017/2/10 11:31
     * @author 陈文豪
     */
    public void showDialog(){
        mAlertDialog.show();
        if (mAlertDialog.getWindow().getAttributes().width != 400) {
            mAlertDialog.getWindow().setLayout(400,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.cart_num_back);
        }
    }

    /**
     * @Title: getDialogView
     * @Description:  获取布局
     * @date 2017/2/10 11:17
     * @author 陈文豪
     */
    public View getDialogView(String msg) {
        View view = View.inflate(mContext, R.layout.inquiry_dialog_layout, null);
        dialog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
        dialog_cancel.setOnClickListener(this);
        dialog_enter = (Button) view.findViewById(R.id.dialog_enter);
        dialog_enter.setOnClickListener(this);
        dialog_msg = (TextView) view.findViewById(R.id.dialog_msg);
        if(msg != null) {
            dialog_msg.setText(msg);
        }
        return view;
    }

    /**
     * @Title: onClick
     * @Description: 事件处理方法
     * @date 2017/2/10 11:22
     * @author 陈文豪
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_cancel:
                dismiss();
                break;
            case R.id.dialog_enter:
                enter();
                break;
        }
    }

    /**
     * @Title: enter
     * @Description: 点击确认所调用的方法
     * @date 2017/2/10 11:23
     * @author 陈文豪
     */
    private void enter() {
        mIsEnter.enter();
        mAlertDialog.dismiss();
    }

    /**
     * @Title: dissmiss
     * @Description:  点击取消按钮所触发的方法
     * @date 2017/2/10 11:21
     * @author 陈文豪
     */
    private void dismiss(){
        mAlertDialog.dismiss();
        mIsEnter.dismiss();
    }

    /**
     * @className: top.linjia.wizarposapp.view.dialog InquiryDialog
     * @description: 回调接口
     * @author 陈文豪
     * @crete 2017/2/10 11:27
     * @copyright: 2017 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    public interface IsEnter{
        public void enter();
        public void dismiss();
    }
}
