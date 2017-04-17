package top.linjia.wizarposapp.customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/17 14:49
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * <p/>
 * 选择支付方式的dialog
 */
public class SelectPayDialog extends Dialog implements View.OnClickListener {
    private Button mSelectPayDialogAlipayItem;
    private Button mSelectPayDialogWeixinItem;
    private Button mSelectPayDialogBaiduItem;
    private Button mSelectPayDialogTencentItem;

    private ItemClick mItemClick = new ItemClick() {
        @Override
        public void payClick(String code) {
            MyToast.showToast(code);
        }
    };

    //四种支付方式的英文缩写
    private final String WEIXIN = "W";
    private final String ALI = "A";
    private final String TENCENT = "T";
    private final String BAIDU = "B";

    public SelectPayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_select_pay_layout);
        setCanceledOnTouchOutside(false);
        setTitle(R.string.select_pay_dialog_title);
        initView();

    }

    private void initView() {
        mSelectPayDialogTencentItem = (Button) findViewById(R.id.select_pay_dialog_tencent_item);
        mSelectPayDialogAlipayItem = (Button) findViewById(R.id.select_pay_dialog_alipay_item);
        mSelectPayDialogBaiduItem = (Button) findViewById(R.id.select_pay_dialog_baidu_item);
        mSelectPayDialogWeixinItem = (Button) findViewById(R.id.select_pay_dialog_weixin_item);

        mSelectPayDialogAlipayItem.setOnClickListener(this);
        mSelectPayDialogBaiduItem.setOnClickListener(this);
        mSelectPayDialogTencentItem.setOnClickListener(this);
        mSelectPayDialogWeixinItem.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_pay_dialog_alipay_item:
                mItemClick.payClick(ALI);
                dismiss();
                break;
            case R.id.select_pay_dialog_baidu_item:
                mItemClick.payClick(BAIDU);
                dismiss();
                break;
            case R.id.select_pay_dialog_weixin_item:
                mItemClick.payClick(WEIXIN);
                dismiss();
                break;
            case R.id.select_pay_dialog_tencent_item:
                mItemClick.payClick(TENCENT);
                dismiss();
                break;

        }
    }

    public void setOnItemClick(ItemClick itemClick) {
        this.mItemClick = itemClick;
    }

    public interface ItemClick{
        public void payClick(String code);
    }

}
