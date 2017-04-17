package top.linjia.wizarposapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 河南巧脉信息技术 on 2016/11/18 17:34
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class PayResultInfo implements Parcelable {


    /**
     * code : -1
     * out_trade_no : 2016102800427
     * message : 订单不存在！
     */

    private String code;
    private String out_trade_no;
    private String message;

    public PayResultInfo() {
    }

    protected PayResultInfo(Parcel in) {
        code = in.readString();
        out_trade_no = in.readString();
        message = in.readString();
    }

    public static final Creator<PayResultInfo> CREATOR = new Creator<PayResultInfo>() {
        @Override
        public PayResultInfo createFromParcel(Parcel in) {
            return new PayResultInfo(in);
        }

        @Override
        public PayResultInfo[] newArray(int size) {
            return new PayResultInfo[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(out_trade_no);
        dest.writeString(message);
    }

    @Override
    public String toString() {
        return "PayResultInfo{" +
                "code='" + code + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
