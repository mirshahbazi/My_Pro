package top.linjia.wizarposapp.parentClass.holder;

import top.linjia.wizarposapp.beans.UpCartNumberBean;

/**
 * @className: top.linjia.wizarposapp.parentClass.holder UpCartNumber
 * @description: 修改服务器购物车数量的父类
 * @author 陈文豪
 * @crete 2017/1/5 15:27
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public abstract class UpCartNumber {
    private UpCartNumberBean upCartNumberBean;
    private int goodsId;
    private int moq;

    public int getMoq() {
        return moq;
    }

    public void setMoq(int moq) {
        this.moq = moq;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public UpCartNumberBean getUpCartNumberBean() {
        return upCartNumberBean;
    }

    public void setUpCartNumberBean(UpCartNumberBean upCartNumberBean) {
        this.upCartNumberBean = upCartNumberBean;
    }

}
