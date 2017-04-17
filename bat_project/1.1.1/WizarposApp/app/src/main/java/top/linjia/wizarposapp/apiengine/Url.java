package top.linjia.wizarposapp.apiengine;

/**
 * @className: top.linjia.wizarposapp.apiengine Url
 * @description: TODO(管理网络请求的地址 统统是静态常量)
 * @author 陈文豪
 * @crete 2016/12/23 18:08
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class Url {
    public static final String BASE_URL = "http://www.linjia.top/linjia-api/api/v1.0/";
    public static final String ORDERFORM_URL = BASE_URL + "order/";
    public static final String IMAGE_URL = "http://www.linjia.top/";
    public static final String CREATE_ORDERFORM = BASE_URL + "order/orderCreate";
    public static final String TO_PAY_URL = BASE_URL + "order/orderPayOnWizarpos";
    public static final String TO_PAY_BY_COUPON = BASE_URL + "coupon/couponList";
    public static final String REQUEST_COUPON_DATA_URL = BASE_URL + "coupon/couponList";
    public static final String USER_LOGIN_URL = BASE_URL + "member/login";
    public static final String GOODSLIST_URL = BASE_URL + "goods/goodsList";
    public static final String COUPON_VERIFICATION = BASE_URL + "order/calcOrderByCoupon";
}