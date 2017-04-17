package top.linjia.wizarposapp.apiengine;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.apiengine Url
 * @description: 管理网络请求的地址 统统是静态常量
 * @crete 2016/12/23 18:08
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class Url {
    public static final String BASE_URL = "http://test.linjia.top/linjia-api/api/v1.0/";
    public static final String ORDERFORM_URL = BASE_URL + "order/";
    public static final String IMAGE_URL = "http://test.linjia.top/";
    public static final String CREATE_ORDERFORM = BASE_URL + "order/orderCreate";
    public static final String TO_PAY_URL = BASE_URL + "order/orderPayOnWizarpos";
    public static final String TO_PAY_BY_COUPON = BASE_URL + "coupon/couponList";
    public static final String REQUEST_COUPON_DATA_URL = BASE_URL + "coupon/couponList";
    public static final String USER_LOGIN_URL = BASE_URL + "member/login";
    public static final String GOODSLIST_URL = BASE_URL + "goods/goodsList";
    public static final String COUPON_VERIFICATION = BASE_URL + "order/calcOrderByCoupon";
    public static final String UPDATA_CART_NUMBER = BASE_URL + "cart/updateCart";
    public static final String QUERY_CART_TOTAL_GOODS = BASE_URL + "cart/cartItemList";
    public static final String DELETE_SELECT_CART_GOODS = BASE_URL + "cart/deleteCartItem";
    public static final String GET_CONFIGRATION_INFO = BASE_URL + "config/getConfig";
    public static final String GET_CART_NUM = BASE_URL + "cart/getCartGoodsNum";
    public static final String CART_UPDATA_BY_INPUT = BASE_URL + "cart/updateCartByInput";
}