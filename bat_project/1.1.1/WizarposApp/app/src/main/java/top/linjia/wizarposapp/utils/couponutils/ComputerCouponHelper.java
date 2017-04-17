package top.linjia.wizarposapp.utils.couponutils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.beans.User_CartInfo;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.utils.couponutils ComputerCouponHelper
 * @description: 这个类负责卡券的计算逻辑
 * @crete 2016/12/23 18:31
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class ComputerCouponHelper {

    public static final int TOTAL_HAVE = 0;//两种卡券都有
    public static final int OFFSET_NOT = 1;//抵用券没有
    public static final int SHOP_NOT = 2;//购物券没有
    public static final int TOTAL_NOT = 3;//都没有

    /**
     * 卡券种类
     */
    public static final int COUPON_SHOP = 0;
    public static final int COUPON_OFFSET = 1;
    private BigDecimal bd;

     /**
       * @Title: computerUsableCoupon
       * @Description: 计算出当前有哪些卡券是可以选择的
       * @param couponList
       * @param couponType
       * @param goodsList
       * @param stateCode
       * @param totalMoney
       * @date 2016/12/26 18:24
       * @author 陈文豪
       */
    public static List<CouponBean.ResultBean.ListBean> computerUsableCoupon(
            List<CouponBean.ResultBean.ListBean> couponList, int couponType, List<User_CartInfo> goodsList,
            int stateCode, BigDecimal totalMoney) {
        /**
         * 根据卡券类型返回
         */
        switch (couponType) {
            case COUPON_SHOP:
                if (stateCode != SHOP_NOT) {
                    CouponComprehensiveInfo shopCouponUsable = getShopCouponUsable(couponList, goodsList, totalMoney);
                    if (shopCouponUsable != null) {
                        return shopCouponUsable.getmUsableCouponList();
                    } else {
                        return null;
                    }

                }
                break;
            case COUPON_OFFSET:
                if (stateCode != OFFSET_NOT) {
                    CouponComprehensiveInfo offsetCouponUsable = getOffsetCouponUsable(couponList, goodsList, totalMoney);
                    if (offsetCouponUsable != null) {
                        return offsetCouponUsable.getmUsableCouponList();
                    } else {
                        return null;
                    }
                }
                break;
        }
        return null;
    }

     /**
       * @Title: parse2Coupon
       * @Description: 解析成两个集合
       * @param totalList
       * @param shopList
       * @param offsetlList
       * @date 2016/12/26 18:25
       * @author 陈文豪
       */
    public static int parse2Coupon(List<CouponBean.ResultBean.ListBean> totalList,
                                   List<CouponBean.ResultBean.ListBean> shopList,
                                   List<CouponBean.ResultBean.ListBean> offsetlList) {
        for (int i = 0; i < totalList.size(); i++) {
            if (totalList.get(i).getPrefix().equals("g")) {
                shopList.add(totalList.get(i));
            } else {
                offsetlList.add(totalList.get(i));
            }
        }

        /**
         * 根据状态码去操作集合
         */
        if (shopList.size() == 0 && offsetlList.size() == 0) {
            return TOTAL_NOT;
        } else if (shopList.size() == 0) {
            Collections.sort(offsetlList);
            Collections.reverse(offsetlList);
            return SHOP_NOT;
        } else if (offsetlList.size() == 0) {
            Collections.sort(shopList);
            Collections.reverse(shopList);
            return OFFSET_NOT;
        } else {
            Collections.sort(shopList);
            Collections.sort(offsetlList);
            Collections.reverse(shopList);
            Collections.reverse(offsetlList);
            return TOTAL_HAVE;
        }
    }

    /**
     * @Title: 
     * @Description: 计算自动选择的卡券
     * @param shopList
     * @param offsetList
     * @param goodsList
     * @param totalMoney
     * @date 2016/12/26 18:26
     * @author 陈文豪
     */
    public static List<CouponBean.ResultBean.ListBean> automationSelectCoupon(
            List<CouponBean.ResultBean.ListBean> shopList,
            List<CouponBean.ResultBean.ListBean> offsetList,
            List<User_CartInfo> goodsList,
            BigDecimal totalMoney) {
        /**
         * 暂时只是计算购物券
         */

        return automationSelectShopCoupon(shopList, goodsList, totalMoney);
    }

    /**
     * @Title: automationSelectShopCoupon
     * @Description: 自动选择购物券
     * @param shopList
     * @param goodsList
     * @param totalMoney
     * @date 2016/12/26 18:27
     * @author 陈文豪
     */
    private static List<CouponBean.ResultBean.ListBean> automationSelectShopCoupon(
            List<CouponBean.ResultBean.ListBean> shopList,
            List<User_CartInfo> goodsList,
            BigDecimal totalMoney) {
        BigDecimal bd = new BigDecimal("0");
        int num = 0;
        List<CouponBean.ResultBean.ListBean> resultList = new ArrayList<>();
        /**
         * 判断这个可选择的购物券券是否存在
         */
        CouponComprehensiveInfo shopCouponUsable = getShopCouponUsable(shopList, goodsList, totalMoney);
        if (shopCouponUsable == null) {
            return null;
        }
        BigDecimal goodsMoney = shopCouponUsable.getmApprovalMoney();
        int goodsNum = shopCouponUsable.getmApprovalNum();
        List<CouponBean.ResultBean.ListBean> listBeen = shopCouponUsable.getmUsableCouponList();
        int size = listBeen.size();
        for (int i = 0; i < size; i++) {
            BigDecimal tempBd = bd.add(new BigDecimal(String.valueOf(listBeen.get(i).
                    getMinimumQuantity())));
            int tempNum = num + listBeen.get(i).getSkuNum();
            if (goodsMoney.compareTo(tempBd) != -1 && goodsNum >= tempNum) {
                resultList.add(listBeen.get(i));
                bd = tempBd;
                num = tempNum;
            }
        }
        return resultList;
    }

    /**
     * @Title: automationSelectOffsetCoupon
     * @Description: 自动选择抵扣券
     * @param offsetList
     * @param goodsList
     * @param totalMoney
     * @date 2016/12/26 18:27
     * @author 陈文豪
     */
    @Deprecated
    private static List<CouponBean.ResultBean.ListBean> automationSelectOffsetCoupon(
            List<CouponBean.ResultBean.ListBean> offsetList,
            List<User_CartInfo> goodsList,
            BigDecimal totalMoney) {
        List<CouponBean.ResultBean.ListBean> resultList = new ArrayList<>();
        CouponComprehensiveInfo offsetCouponUsable = getOffsetCouponUsable(offsetList, goodsList, totalMoney);
        if (offsetCouponUsable == null) {
            return null;
        }
        BigDecimal goodsMoney = offsetCouponUsable.getmApprovalMoney();
        int goodsNum = offsetCouponUsable.getmApprovalNum();
        List<CouponBean.ResultBean.ListBean> listBeen = offsetCouponUsable.getmUsableCouponList();
        int size = listBeen.size();
        for (int i = 0; i < size; i++) {
            int temp = i + 1;
            if (temp == size) {
                if (i == 0) {
                    resultList = listBeen;
                    break;
                }
                break;
            }
            BigDecimal bd = new BigDecimal(String.valueOf(listBeen.get(temp).
                    getMinimumQuantity())).add(new BigDecimal(String.valueOf(listBeen.get(i).
                    getMinimumQuantity())));
            int num = listBeen.get(temp).getSkuNum() + listBeen.get(i).getSkuNum();
            if (goodsMoney.compareTo(bd) != -1) {
                resultList.add(listBeen.get(i));
            }
        }
        return resultList;
    }

    /**
     * @Title:getShopCouponUsable
     * @Description: 计算购物券可用的集合 没有可用就返回空
     * @param shopList
     * @param goodsList
     * @param totalMoney
     * @date 2016/12/26 18:28
     * @author 陈文豪
     */
    public static CouponComprehensiveInfo getShopCouponUsable(List<CouponBean.ResultBean.ListBean> shopList,
                                                              List<User_CartInfo> goodsList,
                                                              BigDecimal totalMoney) {

        BigDecimal bd = new BigDecimal(0);
        if (shopList == null) {
            return null;
        }
        int notDiscountCount = 0;
        thisListShop thisListShop = getThisListShopNotUsable(goodsList, bd, notDiscountCount);
        int sum = goodsList.size() - thisListShop.num;
        thisListShop.bd = totalMoney.subtract(thisListShop.bd);


        for (int i = 0; i < shopList.size(); i++) {
            int compareTo = thisListShop.bd.compareTo(new BigDecimal(shopList.get(i).getMinimumQuantity()));
            if (compareTo != -1 && sum >= shopList.get(i).getSkuNum()) {
                return new CouponComprehensiveInfo(shopList.subList(i, shopList.size()), thisListShop.bd, sum);
            }
        }
        return null;
    }

    /**
     * @author 陈文豪
     * @className: top.linjia.wizarposapp.utils.couponutils ComputerCouponHelper
     * @description: 不参加卡券活动的封装类
     * @crete 2016/12/23 18:29
     * @copyright: 2016 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    public static class thisListShop {
        public BigDecimal bd;
        public int num;

        public thisListShop(BigDecimal bd, int num) {
            this.bd = bd;
            this.num = num;
        }
    }

    /**
     * @Title: getThisListShopNotUsable
     * @Description: 计算得出不能参加购物券的信息
     * @param
     * @date 2016/12/26 18:29
     * @author 陈文豪
     */
    public static thisListShop getThisListShopNotUsable(List<User_CartInfo> goodsList, BigDecimal bd, int notDiscountCount) {
        for (int i = 0; i < goodsList.size(); i++) {
            User_CartInfo user_cartInfo = goodsList.get(i);
            if (user_cartInfo.getDisGwq() == 0) {
                bd = bd.add(new BigDecimal(user_cartInfo.getNum()).multiply(
                        new BigDecimal(user_cartInfo.getPrice())));
//                notDiscountCount++;
//                不进行叠加 排除种类过滤
            }
        }
        return new thisListShop(bd, notDiscountCount);
    }

    /**
     * @Title: getOffsetCouponUsable
     * @Description: 计算抵用券可用的集合 没有可用就返回空
     * @param offsetList
     * @param goodsList
     * @param totalMoney
     * @date 2016/12/26 18:30
     * @author 陈文豪
     */
    private static CouponComprehensiveInfo getOffsetCouponUsable(List<CouponBean.ResultBean.ListBean> offsetList,
                                                                 List<User_CartInfo> goodsList,
                                                                 BigDecimal totalMoney) {
        BigDecimal bd = new BigDecimal(0);
        int notDiscountCount = 0;
        if (offsetList == null) {
            return null;
        }
        thisListShop notUsableOffsetCoupon = getNotUsableOffsetCoupon(goodsList, bd, notDiscountCount);

        notUsableOffsetCoupon.bd = totalMoney.subtract(notUsableOffsetCoupon.bd);
        int sum = offsetList.size() - notUsableOffsetCoupon.num;
        for (int i = 0; i < offsetList.size(); i++) {
            int compareTo = notUsableOffsetCoupon.bd.compareTo(new BigDecimal(offsetList.get(i).getMinimumQuantity()));
            if (compareTo != -1) {
                return new CouponComprehensiveInfo(offsetList.subList(i, offsetList.size()), notUsableOffsetCoupon.bd, sum);
            }
        }
        return null;

    }

    /**
     * @Title: getNotUsableOffsetCoupon
     * @Description: 计算得出不能参加抵用券活动的信息
     * @param
     * @date 2016/12/26 18:30
     * @author 陈文豪
     */
    public static thisListShop getNotUsableOffsetCoupon(List<User_CartInfo> goodsList, BigDecimal bd, int notDiscountCount) {
        for (int i = 0; i < goodsList.size(); i++) {
            User_CartInfo user_cartInfo = goodsList.get(i);
            if (user_cartInfo.getDisDyq() == 0) {
                bd = bd.add(new BigDecimal(user_cartInfo.getNum()).multiply(
                        new BigDecimal(user_cartInfo.getPrice())));
                notDiscountCount++;
            }
        }

        return new thisListShop(bd, notDiscountCount);
    }

    /**
     * @className: top.linjia.wizarposapp.utils.couponutils ComputerCouponHelper
     * @description: 当前可用卡券信息类
     * @author 陈文豪
     * @crete 2016/12/26 18:31
     * @copyright: 2016 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    public static class CouponComprehensiveInfo {
        private List<CouponBean.ResultBean.ListBean> mUsableCouponList;
        private BigDecimal mApprovalMoney;
        private int mApprovalNum;

        public CouponComprehensiveInfo(List<CouponBean.ResultBean.ListBean> mUsableCouponList, BigDecimal mApprovalMoney, int mApprovalNum) {
            this.mUsableCouponList = mUsableCouponList;
            this.mApprovalMoney = mApprovalMoney;
            this.mApprovalNum = mApprovalNum;
        }

        public List<CouponBean.ResultBean.ListBean> getmUsableCouponList() {
            return mUsableCouponList;
        }

        public void setmUsableCouponList(List<CouponBean.ResultBean.ListBean> mUsableCouponList) {
            this.mUsableCouponList = mUsableCouponList;
        }

        public BigDecimal getmApprovalMoney() {
            return mApprovalMoney;
        }

        public void setmApprovalMoney(BigDecimal mApprovalMoney) {
            this.mApprovalMoney = mApprovalMoney;
        }

        public int getmApprovalNum() {
            return mApprovalNum;
        }

        public void setmApprovalNum(int mApprovalNum) {
            this.mApprovalNum = mApprovalNum;
        }
    }

}
