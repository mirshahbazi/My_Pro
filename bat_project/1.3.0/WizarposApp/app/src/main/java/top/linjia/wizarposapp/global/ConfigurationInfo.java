package top.linjia.wizarposapp.global;

import java.math.BigDecimal;

/**
 * Created by 邻家新锐 on 2016/10/27 17:45
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class ConfigurationInfo {
    public static final int GOODSLIST_LOADING_PAGE_NUMBER = 20;//下拉加载每次加载的条目
    public static final BigDecimal FREIGHT_MONEY = new BigDecimal(9);//运费
    public static final BigDecimal EXEMPT_FREIGHT = new BigDecimal(199);
    public static final String CODE_MODULE＿TOTAL = "QR_CODE,ONE_D_FORMATS";//扫码模式
    public static final String CODE_MODULE＿OR_CODE = "QR_CODE";//扫码模式
}
