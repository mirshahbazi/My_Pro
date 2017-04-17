package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: MainGoodsListAdapter
 * @Description: 主页面商品列表适配器。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class MainGoodsListAdapter extends BaseAdapter {
    private List<GoodsList.ResultBean.ListBean> mList;// TODO: 2016/11/2 如果报出空指针异常 就找这里
    private Context mContext;
    TextView cartnum;
    UserDao userDao;
    List<User_CartInfo> cartInfos;
    int goodsnum = 0;   //展示出的商品数量
    int goodsNumber = 0;  //库存
    int curnum = 0;    //  购物车中当前商品种类数
    int goodsAllNum = 0;  //购物车中所有商品总数量
    int shownum = 0;
    boolean flag;
    int[] positions;

    public MainGoodsListAdapter(Context context, TextView cartnum) {
        this.mContext = context;
        userDao = new UserDao(context);
        cartInfos = new ArrayList<>();
        cartInfos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        this.cartnum = cartnum;
        curnum = cartInfos.size();
    }

    /**
     * @Title: setGoods_datalength
     * @Description: 设置购物车中商品数据的长度
     * @param: int
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void setGoods_datalength(int length) {
        positions = new int[length];
    }

    /**
     * @Title: setGoods_datamoq
     * @Description: 为购物车中的条目数据设置每个条目的商品的起订数量
     * @param: int[]
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void setGoods_datamoq(int[] positions) {
        this.positions = positions;
    }

    /**
     * @Title: setmList
     * @Description: 为购物车设置List数据
     * @param: List<GoodsList.ResultBean.ListBean>
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void setmList(List<GoodsList.ResultBean.ListBean> mList) {
        this.mList = mList;
    }

    /**
     * @Title: addmList
     * @Description: 为购物车List增添新的数据数据
     * @param: List<GoodsList.ResultBean.ListBean>
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void addmList(List<GoodsList.ResultBean.ListBean> mList) {
        this.mList.addAll(mList);
    }

    /**
     * @Title: getmList
     * @Description: 获取适配器中的list数据
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public List<GoodsList.ResultBean.ListBean> getmList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MainGoodsHolder mMainGoodsHolder;
        goodsNumber = mList.get(position).getNumber();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.main_goodslist_item, null);
            mMainGoodsHolder = new MainGoodsHolder();
            mMainGoodsHolder.click = (LinearLayout) convertView.findViewById(R.id.main_goodsitem_click);
            mMainGoodsHolder.afterclick = (LinearLayout) convertView.findViewById(R.id.main_goodsitem_afterclick);
            mMainGoodsHolder.plus = (ImageView) convertView.findViewById(R.id.main_goodsitem_plus);
            mMainGoodsHolder.reduce = (TextView) convertView.findViewById(R.id.main_goodsitem_reduce);
            mMainGoodsHolder.num = (TextView) convertView.findViewById(R.id.main_goodsitem_num);
            mMainGoodsHolder.add = (TextView) convertView.findViewById(R.id.main_goodsitem_add);
            mMainGoodsHolder.moq = (TextView) convertView.findViewById(R.id.main_goodsitem_moq);
            mMainGoodsHolder.praise = (TextView) convertView.findViewById(R.id.main_goodsitem_praise);
            mMainGoodsHolder.name = (TextView) convertView.findViewById(R.id.main_goodsitem_name);
            mMainGoodsHolder.number = (TextView) convertView.findViewById(R.id.main_goodsitem_store);
            mMainGoodsHolder.price = (TextView) convertView.findViewById(R.id.main_goodsitem_price);
            mMainGoodsHolder.stname = (TextView) convertView.findViewById(R.id.main_goodsitem_stname);
            convertView.setTag(mMainGoodsHolder);
        } else {
            mMainGoodsHolder = (MainGoodsHolder) convertView.getTag();
        }
        mMainGoodsHolder.setGoodsNo(mList.get(position).getGoodsNo());
        mMainGoodsHolder.stname.setText(mList.get(position).getStname());
        mMainGoodsHolder.name.setText(mList.get(position).getName());
        mMainGoodsHolder.price.setText("￥ " + String.valueOf(mList.get(position).getPrice()));
        mMainGoodsHolder.number.setText(String.valueOf(mList.get(position).getNumber()));
        mMainGoodsHolder.moq.setText(String.valueOf(mList.get(position).getMoq()));
        mMainGoodsHolder.num.setText(String.valueOf(positions[position]));
        /**
         * 指定限订按钮显示方式，’不限/n‘两种方式
         * */
        if (mList.get(position).getPraise() <= 0 || mList.get(position).getPraise() < mList.get(position).getMoq() || mList.get(position).getPraise() > mList.get(position).getNumber()) {
            mMainGoodsHolder.praise.setText(mContext.getResources().getString(R.string.select_coupon_dialog_listitem_not_limit_time));
        } else {
            mMainGoodsHolder.praise.setText(mList.get(position).getPraise() + "");
        }

        /**
         * 遍历数据库判断是否展开操作数量加减号
         * */
        mMainGoodsHolder.click.setVisibility(View.VISIBLE);
        mMainGoodsHolder.afterclick.setVisibility(View.GONE);
        cartInfos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        flag = cartTraverse(cartInfos, mList.get(position).getName());
        if (flag) {
            mMainGoodsHolder.num.setText(String.valueOf(userDao.queryGoodsNum(mList.get(position).getName())));
            mMainGoodsHolder.click.setVisibility(View.GONE);
            mMainGoodsHolder.afterclick.setVisibility(View.VISIBLE);
        }

        /**
         * 点击加号安钮将商品按起订数量加入购物车，并且展开数量选择符号
         * */
        mMainGoodsHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainGoodsHolder.afterclick.setTag(position);
                mMainGoodsHolder.click.setTag(position);
                mMainGoodsHolder.click.setVisibility(View.GONE);
                mMainGoodsHolder.afterclick.setVisibility(View.VISIBLE);
                User_CartInfo user_cartInfo = new User_CartInfo();
                user_cartInfo.setGoodsname(mList.get(position).getName());
                user_cartInfo.setStname(mList.get(position).getStname());
                user_cartInfo.setPrice(mList.get(position).getPrice());
                user_cartInfo.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                user_cartInfo.setNum(positions[position]);
                user_cartInfo.setGoodsId(mList.get(position).getGoodsId());
                user_cartInfo.setPraise(mList.get(position).getPraise());
                user_cartInfo.setMoq(mList.get(position).getMoq());
                int disDyq;
                int disGwq;
                if (mList.get(position).isDisDyq()) {
                    disDyq = 0;
                } else {
                    disDyq = 1;
                }
                if (mList.get(position).isDisGwq()) {
                    disGwq = 0;
                } else {
                    disGwq = 1;
                }
                user_cartInfo.setDisDyq(disDyq);
                user_cartInfo.setDisGwq(disGwq);
                if (mList.get(position).getNumber() > 0 && mList.get(position).getMoq() <= mList.get(position).getNumber()) {
                    flag = userDao.insertCart(user_cartInfo);
                    if (flag) {
                        MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_successadd));
                        goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
                        cartnum.setVisibility(View.VISIBLE);
                        cartnum.setText(String.valueOf(goodsAllNum));
                        mMainGoodsHolder.num.setText(String.valueOf(userDao.queryGoodsNum(mList.get(position).getName())));
                    } else {
                        MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_wrong));
                        mMainGoodsHolder.num.setText(String.valueOf(mList.get(position).getMoq()));
                    }
                } else {
                    MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_shortstore));
                    mMainGoodsHolder.click.setVisibility(View.VISIBLE);
                    mMainGoodsHolder.afterclick.setVisibility(View.GONE);
                }

            }
        });

        /**
         * 点击减号按钮减去该商品的一个数量，数据库数量减1，展示数量同时减1，库存同时减1
         * */
        mMainGoodsHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartInfos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
                flag = cartTraverse(cartInfos, mList.get(position).getName());
                if (flag) {
                    /**
                     * 每次点击减号后去购物车中获取该商品的购物车数量然后由这个数量决定是否减1
                     * */
                    goodsnum = userDao.queryGoodsNum(mList.get(position).getName());
                    if (goodsnum > mList.get(position).getMoq()) {
                        goodsnum -= 1;
                        boolean flag = userDao.updateCart(goodsnum, mList.get(position).getName());
                        if (flag) {
                            mMainGoodsHolder.num.setText(String.valueOf(goodsnum));
                            goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
                            cartnum.setText(String.valueOf(goodsAllNum));
                            MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_reducegoods));
                        } else {
                            MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_wrong));
                        }
                    } else {
                        boolean flag = userDao.deletefromCart(mList.get(position).getName());
                        if (flag) {
                            goodsnum = 0;
                            goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
                            positions[position] = mList.get(position).getMoq();
                            mMainGoodsHolder.num.setText(String.valueOf(0));
                            cartnum.setText(String.valueOf(goodsAllNum));
                            isEmptyCart(goodsAllNum);
                            mMainGoodsHolder.click.setVisibility(View.VISIBLE);
                            mMainGoodsHolder.afterclick.setVisibility(View.GONE);
                            MyToast.showToast(mContext.getResources().getString(R.string.goods_pruchase_deletegoods));
                        } else {
                            MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_wrong));
                        }
                    }
                } else {
                    MyToast.showToast(mContext.getResources().getString(R.string.goods_pruchase_noincludegoods));
                }
            }
        });

        /**
         * 点击加号增添一个该商品的数量，数据库添加一个该商品，并且商品展示数量加一，库存量减一
         * */
        mMainGoodsHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "当前点击的条目是：----------------------------" + position);
                cartInfos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
                flag = cartTraverse(cartInfos, mList.get(position).getName());
                if (flag) {
                    goodsnum = userDao.queryGoodsNum(mList.get(position).getName());
                    mMainGoodsHolder.num.setText(String.valueOf(goodsnum));
                    if ((goodsnum + 1) <= mList.get(position).getNumber()) {
                        User_CartInfo user_cartInfo = new User_CartInfo();
                        user_cartInfo.setGoodsname(mList.get(position).getName());
                        user_cartInfo.setStname(mList.get(position).getStname());
                        user_cartInfo.setPrice(mList.get(position).getPrice());
                        user_cartInfo.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                        user_cartInfo.setNum(1);
                        user_cartInfo.setGoodsId(mList.get(position).getGoodsId());
                        user_cartInfo.setPraise(mList.get(position).getPraise());
                        user_cartInfo.setMoq(mList.get(position).getMoq());
                        int disDyq;
                        int disGwq;
                        if (mList.get(position).isDisDyq()) {
                            disDyq = 0;
                        } else {
                            disDyq = 1;
                        }
                        if (mList.get(position).isDisGwq()) {
                            disGwq = 0;
                        } else {
                            disGwq = 1;
                        }
                        user_cartInfo.setDisDyq(disDyq);
                        user_cartInfo.setDisGwq(disGwq);
                        Log.i("test", "已经加入购物车");
                        if (mList.get(position).getPraise() < 0 || mList.get(position).getMoq() > mList.get(position).getPraise()) {
                            /**
                             * 该情况视为不限
                             * */
                            goodsnum += 1;
                            boolean flag = userDao.insertCart(user_cartInfo);
                            if (flag) {
                                MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_addcart));
                                mMainGoodsHolder.num.setText(String.valueOf(goodsnum));
                            } else {
                                MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_wrong));
                            }
                        } else if (mList.get(position).getMoq() < mList.get(position).getPraise()) {
                            /**
                             * 该请况是包含限定的情况
                             * */
                            if (goodsnum + 1 <= mList.get(position).getPraise()) {
                                goodsnum += 1;
                                boolean flag = userDao.insertCart(user_cartInfo);
                                if (flag) {
                                    MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_addgoods));
                                    mMainGoodsHolder.num.setText(String.valueOf(goodsnum));
                                } else {
                                    MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_wrong));
                                }
                            } else {
                                MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_praisenum));
                            }
                        } else {

                        }
                    } else {
                        MyToast.showToast(mContext.getResources().getString(R.string.goods_pruchase_nomoregoods));
                    }
                    goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
                    cartnum.setVisibility(View.VISIBLE);
                    cartnum.setText(String.valueOf(goodsAllNum));
                } else {
                    /**
                     * 购物车中不包含该种商品，点击加号是按照起订量加入购物车
                     * */
                    flag = true;
                    int disDyq;
                    int disGwq;
                    User_CartInfo user_cartInfo = new User_CartInfo();
                    user_cartInfo.setGoodsname(mList.get(position).getName());
                    user_cartInfo.setStname(mList.get(position).getStname());
                    user_cartInfo.setPrice(mList.get(position).getPrice());
                    user_cartInfo.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                    user_cartInfo.setNum(mList.get(position).getMoq());
                    user_cartInfo.setGoodsId(mList.get(position).getGoodsId());
                    user_cartInfo.setPraise(mList.get(position).getPraise());
                    user_cartInfo.setMoq(mList.get(position).getMoq());
                    if (mList.get(position).isDisDyq()) {
                        disDyq = 0;
                    } else {
                        disDyq = 1;
                    }
                    if (mList.get(position).isDisGwq()) {
                        disGwq = 0;
                    } else {
                        disGwq = 1;
                    }
                    user_cartInfo.setDisDyq(disDyq);
                    user_cartInfo.setDisGwq(disGwq);
                    boolean insertcart = userDao.insertCart(user_cartInfo);
                    if (insertcart) {
                        MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_addcart));
                        mMainGoodsHolder.num.setText(String.valueOf(mList.get(position).getMoq()));
                        cartnum.setVisibility(View.VISIBLE);
                        goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
                        cartnum.setText(String.valueOf(goodsAllNum));
                    }
                }
            }
        });
        return convertView;
    }

    /**
     * @ClassName: MainGoodsHolder
     * @Description: 为GridView中的item设置一个static类型holder类，其中为item布局中的控件。
     * @Author: 李鹏鹏
     * @Data: 2016/12/19
     * @Copyright: 河南巧脉信息技术有限公司
     */
    public static class MainGoodsHolder {
        String goodsNo;//商品id
        TextView moq;//起订
        TextView name;//商品名称
        TextView number;//库存
        TextView path;//图片路径
        TextView price;//单价
        TextView stname;//规格
        TextView praise; //限订
        TextView reduce;
        TextView add;
        ImageView plus;
        TextView num;
        LinearLayout click;
        LinearLayout afterclick;

        public String getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
        }
    }

    /**
     * @Title: cartTraverse
     * @Description: 遍历购物车，返回布尔类型的值用来表示购物车中是否包含该商品
     * @param: List<User_CartInfo> String
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public boolean cartTraverse(List<User_CartInfo> cartInfos, String goodsname) {
        for (int i = 0; i < cartInfos.size(); i++) {
            if (cartInfos.get(i).getGoodsname().equals(goodsname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Title: isEmptyCart
     * @Description: 遍历购物车，返回布尔类型的数据来决定购物车是否为空
     * @param: int
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void isEmptyCart(int curnum) {
        if (curnum != 0) {
            cartnum.setVisibility(View.VISIBLE);
        } else {
            cartnum.setVisibility(View.GONE);
        }
    }
}
