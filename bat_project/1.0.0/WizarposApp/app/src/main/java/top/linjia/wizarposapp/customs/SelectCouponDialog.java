package top.linjia.wizarposapp.customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.CouponListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/25 14:22
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * <p>
 * 自定义选择卡券dialog
 */
public class SelectCouponDialog extends Dialog {

    private String couponType = "g";
    private ListView mCustomDialogCouponListview;
    public final String OFFSET_COUPON = "d";
    public final String SHOP_COUPON = "g";
    private Context mContext;
    private ProgressBar mProgressbar;
    private CouponInfo mCouponInfo;
    private CouponBean request;
    private TextView mTextViewNotCoupon;

    public SelectCouponDialog(Context context) {
        super(context, R.style.select_coupon_dialog);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.select_coupon_layout);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initView();
    }

    @Override
    public void show() {
        super.show();
        mProgressbar.setVisibility(View.VISIBLE);
        mCustomDialogCouponListview.setVisibility(View.GONE);
        mTextViewNotCoupon.setVisibility(View.GONE);
        initData();
    }

    private void initData() {

        WizarPosApp.getInternetThreadPool().execute(new Runnable() {

            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                /**
                 *  {"appToken":"0d6dae266a8e4a378bb5c1107e9c432d","couponStatus":1,"prefix":"d" }
                 "appToken"           :  必填
                 "couponStatus"    :  查询状态："0"查询所有（不填默认为0）,"1"查询可用券,"2"查询已使用,"3"查询已过期
                 "prefix"                   :查询卡券的类别："d" 抵扣券  "g"购物券
                 */
                builder.add("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
                builder.add("couponStatus", "1");//查询模式
                builder.add("prefix", couponType);//查询类型
                FormBody build = builder.build();
                try {
                    request = OkHttpUtil.postBeanFormServer(Url.REQUEST_COUPON_DATA_URL, build, CouponBean.class);
                    if (request != null) {
                        if (!request.equals("")) {
                            mProgressbar.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressbar.setVisibility(View.GONE);
                                    if(request.getResult().getList().size() != 0) {
                                        mCustomDialogCouponListview.setVisibility(View.VISIBLE);
                                        mCustomDialogCouponListview.setAdapter(new CouponListAdapter(mContext, request.getResult().getList()));
                                    }else{
                                        mTextViewNotCoupon.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }else{
                            requestFail("无返回");
                        }
                    }else {
                        requestFail("返回值为空");
                    }
                } catch (IOException e) {
                    requestFail("网络访问出错");
                }


            }

            private void requestFail(final String msg) {
                mProgressbar.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressbar.setVisibility(View.GONE);
                        MyToast.showToast(msg + "╮(╯▽╰)╭");
                    }
                });
            }

        });
    }


    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    private void initView() {
        mCustomDialogCouponListview = (ListView) findViewById(R.id.custom_dialog_coupon_listview);
        mProgressbar = (ProgressBar) findViewById(R.id.coupon_loading);
        mTextViewNotCoupon = (TextView) findViewById(R.id.select_coupon_dialog_text);

        mCustomDialogCouponListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CouponBean.ResultBean.ListBean listBean = request.getResult().getList().get(position);
                mCouponInfo.getSelCouponInfoID(listBean);
            }
        });
    }

    public void setItemCouponID(CouponInfo couponInfo){
        mCouponInfo = couponInfo;
    }

    public interface CouponInfo{
        void getSelCouponInfoID(CouponBean.ResultBean.ListBean card);
    }
}
