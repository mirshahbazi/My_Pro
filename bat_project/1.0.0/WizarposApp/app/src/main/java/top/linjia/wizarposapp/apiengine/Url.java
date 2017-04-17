package top.linjia.wizarposapp.apiengine;

/**
 * Created by 邻家新锐 on 2016/10/17
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class Url {
    public static final String BASE_URL = "http://test.linjia.top/linjia-api/api/v1.0/";
    //    public static final String BASE_URL = "http://192.168.133.38:8080/linjia-api/api/v1.0/";
    public static final String ORDERFORM_URL = BASE_URL + "order/";
    public static final String IMAGE_URL = "www://test.linjia.top/";
    public static final String CREATE_ORDERFORM = BASE_URL + "order/orderCreate";
    public static final String TO_PAY_URL = BASE_URL + "order/orderPayOnWizarpos";
    public static final String TO_PAY_BY_COUPON = BASE_URL + "coupon/couponList";
    public static final String REQUEST_COUPON_DATA_URL = BASE_URL + "coupon/couponList";
    public static final String USER_LOGIN_URL = BASE_URL + "member/login";
    public static final String GOODSLIST_URL = BASE_URL + "goods/goodsList";
    //coupon/couponList
}
