package top.linjia.wizarposapp.apiengine;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.apiengine ConfigurationGet
 * @description: 获取从服务器配置信息
 * @crete 2017/1/20 13:41
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class ConfigurationGet {

    /**
     * @return int 免运费的标准 单位元
     * @Title: getFreightExemptValue
     * @Description: 这个方法返回免运费的标准
     * @date 2017/1/20 13:41
     * @author 陈文豪
     */
    public static int getFreightExemptValue() throws IOException {
        Double value = (Double) getFreightInfo().get("value");
        return value.intValue();
    }

    /**
     * @return int 免运费的标准 单位元
     * @Title: getFreightExemptValue
     * @Description: 这个方法返回免运费的标准
     * @date 2017/1/20 13:41
     * @author 陈文豪
     */
    public static void asycFreightExemptValue(final TextView textView, final Context mContext) throws IOException {
        OkHttpUtil.postMapFormServer(Url.GET_CONFIGRATION_INFO, structureParameter(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MyToast.showToast(mContext.getString(R.string.internet_errer));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                HashMap<String, Object> map = GsonUtil.parseJsonToMapObject(response.body().string());
                Map<String, Object> result = (Map<String, Object>) map.get("result");
                final String value = (String) result.get("value");

                Log.e("error", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                CartNumberHelper.upUI(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(String.format(mContext.getString(R.string.exempt_freight_text), value));
                    }
                }, mContext);

            }
        });
    }


    /**
     * @return
     * @Title: getFreightInfo
     * @Description: 获取运费信息
     * @date 2017/1/20 13:45
     * @author 陈文豪
     */
    public static Map<String, Object> getFreightInfo() throws IOException {
        Map resultMap = OkHttpUtil.postMapFormServer(Url.GET_CONFIGRATION_INFO, structureParameter());
        if (resultMap == null) {
            return null;
        }
        return (Map<String, Object>) resultMap.get("result");
    }

    public static Map<String, Object> structureParameter() {
        Map<String, Object> map = new HashMap();
        map.put("name", "freeshipping");
        return map;
    }
}
