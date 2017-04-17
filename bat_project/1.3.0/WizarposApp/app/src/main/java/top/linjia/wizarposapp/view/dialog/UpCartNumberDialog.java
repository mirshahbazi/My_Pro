package top.linjia.wizarposapp.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.view.dialog UpCartNumberDialog
 * @description: 输入更新购物车熟练的dialog
 * @crete 2017/1/16 9:47
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class UpCartNumberDialog implements View.OnClickListener {

    private Context mContext;

    private static UpCartNumberDialog upCartNumberDialog = new UpCartNumberDialog();
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText mCartNumberInputEdit;
    private Button mCartNumberInputCancel;
    private Button mCartNumberInputEnter;
    private int goodsId;
    private HashMap<String, Object> hashMap;
    private Map<String, Object> stringObjectMap;
    private TextView mCartNum;
    private TextView goodsNum;
    private upUnknown mUpUnknown;

    /**
     * @Title: UpCartNumberDialog
     * @Description: 私有化构造方法实现单例
     * @date 2017/1/16 10:11
     * @author 陈文豪
     */
    private UpCartNumberDialog() {
    }

    /**
     * @param mContext
     * @return upCartNumberDialog
     * @Title: getUpCartNumberDialog
     * @Description: 获取实例
     * @date 2017/1/16 11:05
     * @author 陈文豪
     */
    public static UpCartNumberDialog getUpCartNumberDialog(Context mContext, int goodsId, TextView mCartNum, TextView goodsNum) {
        upCartNumberDialog.mContext = mContext;
        upCartNumberDialog.goodsId = goodsId;
        upCartNumberDialog.mCartNum = mCartNum;
        upCartNumberDialog.goodsNum = goodsNum;
        return upCartNumberDialog;
    }

    /**
     * @Title: prepareShow
     * @Description: 准备dialog的参数 初始化
     * @date 2017/1/16 11:08
     * @author 陈文豪
     */
    public void prepareShow() {
        builder = new AlertDialog.Builder(mContext);
        View dialogView = getDialogView();
        builder.setView(dialogView);
        builder.setCancelable(false);
        alertDialog = builder.create();
    }

    /**
     * @Title: showUpDialog
     * @Description: 展示更新数量的按钮
     * @date 2017/1/16 11:06
     * @author 陈文豪
     */
    public void showUpDialog() {
        alertDialog.show();
        mCartNumberInputEdit.setText(goodsNum.getText());
        mCartNumberInputEdit.setSelection(0, goodsNum.getText().length());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { //让软键盘延时弹出，以更好的加载Activity

            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) mCartNumberInputEdit.getContext().
                                getSystemService(mContext.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(mCartNumberInputEdit, 0);
            }

        }, 400);
        //避免重复调用耗费资源
        if (alertDialog.getWindow().getAttributes().width != 400) {
            alertDialog.getWindow().setLayout(400,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.cart_num_back);
        }
    }

    /**
     * @return View
     * @Title: getDialogView
     * @Description: 为dialog提供view填充
     * @date 2017/1/16 11:20
     * @author 陈文豪
     */
    public View getDialogView() {
        LayoutInflater from = LayoutInflater.from(mContext);
        View inflate = from.inflate(R.layout.input_cart_number_dialog_layout, null);
        mCartNumberInputCancel = (Button) inflate.findViewById(R.id.cart_number_input_cancel);
        mCartNumberInputCancel.setOnClickListener(this);
        mCartNumberInputEnter = (Button) inflate.findViewById(R.id.cart_number_input_enter);
        mCartNumberInputEnter.setOnClickListener(this);
        mCartNumberInputEdit = (EditText) inflate.findViewById(R.id.cart_number_input_edit);

        return inflate;
    }

    /**
     * @param v
     * @Title: onClick
     * @Description: 处理点击事件
     * @date 2017/1/16 11:20
     * @author 陈文豪
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_number_input_enter:
                enterClick();
                break;
            case R.id.cart_number_input_cancel:
                cancelClick();
                break;
        }
    }

    /**
     * @Title: enterClick
     * @Description: 对编辑后的内容提交
     * @date 2017/1/16 11:30
     * @author 陈文豪
     */
    private void enterClick() {
        //{"appToken":"390b7fa643bf4ad7b343d3349519fb2c","goodsId":"450","number":"0"}
        hashMap = new HashMap<>();
        hashMap.put("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
        hashMap.put("goodsId", String.valueOf(goodsId));
        hashMap.put("number", mCartNumberInputEdit.getText().toString());


        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    stringObjectMap = OkHttpUtil.postMapFormServer(Url.CART_UPDATA_BY_INPUT, hashMap);
                    upUi(stringObjectMap);
                } catch (IOException e) {
                    upUi(stringObjectMap);
                }
            }
        });
    }

    /**
     * @Title: submit
     * @Description: 验证向服务器提交的数据是否合法
     * @date 2017/1/16 16:23
     * @author 陈文豪
     */
    private void submit() {
        String num = mCartNumberInputEdit.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            MyToast.showToast(mContext.getString(R.string.num_not_isnull));
            return;
        }
    }

    /**
     * @param map
     * @Title: upUi
     * @Description: 根据结果更新ui
     * @date 2017/1/16 13:59
     * @author 陈文豪
     */
    private void upUi(final Map<String, Object> map) {
        CartNumberHelper.upUI(new Runnable() {
            @Override
            public void run() {
                if (map == null) {
                    MyToast.showToast(mContext.getString(R.string.internet_errer));
                    return;
                }
                if (((Double) map.get("state")).intValue() == 0) {
                    String desc = (String) map.get("desc");
                    MyToast.showToast(desc);
                    return;
                }
                Map<String, Object> result = (Map<String, Object>) map.get("result");
                Double state = (Double) result.get("state");
                Double number = (Double) result.get("number");
                if (state.intValue() == 1) {
                    goodsNum.setText(String.valueOf(number.intValue()));
                    CartNumberHelper.cartSumCount(mCartNum, ((Double) result.get("cartGoodsNum")).intValue());
                    if(mUpUnknown != null){
                        mUpUnknown.upSucceedBody(result);
                    }
                }else if(mUpUnknown != null){
                    mUpUnknown.upFailureBody();
                }
                MyToast.showToast((String) result.get("desc"));
                dismiss();
            }
        }, mContext);
    }

    /**
     * @Title: cancelClick
     * @Description: 取消对数量进行编辑
     * @date 2017/1/16 11:30
     * @author 陈文豪
     */
    private void cancelClick() {
        dismiss();
    }


    /**
     * @Title: dismiss
     * @Description: 关闭dialog
     * @date 2017/1/16 11:30
     * @author 陈文豪
     */
    private void dismiss() {
        alertDialog.dismiss();
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @className: top.linjia.wizarposapp.view.dialog UpCartNumberDialog
     * @description: 策略设计 允许用户自己定义策略
     * @author 陈文豪
     * @crete 2017/1/16 17:32
     * @copyright: 2017 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    public interface upUnknown{
        public void upSucceedBody(Map<String,Object> map);
        public void upFailureBody();
    }


    public upUnknown getmUpUnknown() {
        return mUpUnknown;
    }

    public void setmUpUnknown(upUnknown mUpUnknown) {
        this.mUpUnknown = mUpUnknown;
    }
}
