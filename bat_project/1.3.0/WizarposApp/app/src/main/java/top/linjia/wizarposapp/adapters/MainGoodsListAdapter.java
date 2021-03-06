package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
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
    boolean notClick = false;
    int tempNumber = 0;

    public MainGoodsListAdapter(Context context, TextView cartnum) {
        this.mContext = context;
        userDao = new UserDao(context);
        cartInfos = new ArrayList<>();
        this.cartnum = cartnum;
        curnum = cartInfos.size();
    }

    /**
     * handler 实现更新ui
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int number = msg.getData().getInt("number");
            int position = msg.getData().getInt("position");
            TextView num = (TextView) msg.obj;

            if (number != -1) {
                //写到集合里面
                mList.get(position).setCartNumber(number);
                /**
                 * 设置单个数量
                 */
                num.setText(String.valueOf(number));
                /**
                 * 决定显示与否
                 */
                CartNumberHelper.cartSumCount(cartnum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
                MyToast.showToast(mContext.getString(R.string.operation_succeed));
                notifyDataSetChanged();
            }
            notClick = false;
        }
    };

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
            mMainGoodsHolder.moqTag = (TextView) convertView.findViewById(R.id.main_goodsitem_moq_tag);
            mMainGoodsHolder.praiseTag = (TextView) convertView.findViewById(R.id.main_goodsitem_praise_tag);

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
        mMainGoodsHolder.praise.setText(String.valueOf(mList.get(position).getPraise()));
        mMainGoodsHolder.moq.setText(String.valueOf(mList.get(position).getMoq()));
        /**
         * 指定限订按钮显示方式，’不限/n‘两种方式
         * */
        if (mList.get(position).getPraise() == 0 && mMainGoodsHolder.praise.getVisibility() != View.GONE) {
            mMainGoodsHolder.praise.setVisibility(View.GONE);
            mMainGoodsHolder.praiseTag.setVisibility(View.GONE);
        } else if (mList.get(position).getPraise() != 0 && mMainGoodsHolder.praise.getVisibility() != View.VISIBLE) {
            mMainGoodsHolder.praise.setVisibility(View.VISIBLE);
            mMainGoodsHolder.praiseTag.setVisibility(View.VISIBLE);
        }
        /**
         * 指定起订显示方式
         */
        if (mList.get(position).getMoq() == 1 && mMainGoodsHolder.moq.getVisibility() != View.GONE) {
            mMainGoodsHolder.moq.setVisibility(View.GONE);
            mMainGoodsHolder.moqTag.setVisibility(View.GONE);
        } else if (mList.get(position).getMoq() != 1 && mMainGoodsHolder.moq.getVisibility() != View.VISIBLE) {
            mMainGoodsHolder.moq.setVisibility(View.VISIBLE);
            mMainGoodsHolder.moqTag.setVisibility(View.VISIBLE);
        }

        /**
         * 遍历数据库判断是否展开操作数量加减号
         * */
        mMainGoodsHolder.click.setVisibility(View.VISIBLE);
        mMainGoodsHolder.afterclick.setVisibility(View.GONE);
        flag = mList.get(position).getCartNumber() != 0;
        if (flag) {
            mMainGoodsHolder.num.setText(String.valueOf(mList.get(position).getCartNumber()));
            mMainGoodsHolder.click.setVisibility(View.GONE);
            mMainGoodsHolder.afterclick.setVisibility(View.VISIBLE);
        }

        /**
         * 点击加号安钮将商品按起订数量加入购物车，并且展开数量选择符号
         * */
        mMainGoodsHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notClick) {
                    MyToast.showToast(mContext.getString(R.string.not_click));
                    return;
                }
                //不可进入点击
                notClick = true;
                mMainGoodsHolder.afterclick.setTag(position);
                mMainGoodsHolder.click.setTag(position);

                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /**
                             * 如果返回-1就是更新失败
                             */
                            tempNumber = CartNumberHelper.upCartNumberMethod(CartNumberHelper.FIRST_ADD_CART_NUMBER,
                                    position, mList, mContext);
                            CartNumberHelper.upUI(new Runnable() {
                                @Override
                                public void run() {
                                    if (tempNumber != -1) {
                                        mMainGoodsHolder.click.setVisibility(View.GONE);
                                        mMainGoodsHolder.afterclick.setVisibility(View.VISIBLE);
                                    }
                                }
                            }, mContext);
                        } catch (IOException e) {
                            CartNumberHelper.upUI(mContext.getString(R.string.internet_errer), mContext);
                        } finally {
                            Bundle bundle = new Bundle();
                            bundle.putInt("number", tempNumber);
                            bundle.putInt("position", position);
                            Message message = handler.obtainMessage();
                            message.setData(bundle);
                            message.obj = mMainGoodsHolder.num;
                            handler.sendMessage(message);

                        }
                    }
                });
            }
        });

        /**
         * 点击减号按钮减去该商品的一个数量，数据库数量减1，展示数量同时减1，库存同时减1
         * */
        mMainGoodsHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notClick) {
                    MyToast.showToast(mContext.getString(R.string.not_click));
                    return;
                }
                //不可进入点击
                notClick = true;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /**
                             * 如果返回-1就是更新失败
                             */
                            tempNumber = CartNumberHelper.upCartNumberMethod(CartNumberHelper.SUBSTRAT_CART_NUMBER,
                                    position, mList, mContext);

                        } catch (IOException e) {
                            CartNumberHelper.upUI(mContext.getString(R.string.internet_errer), mContext);
                        } finally {
                            Bundle bundle = new Bundle();
                            bundle.putInt("number", tempNumber);
                            bundle.putInt("position", position);
                            Message message = handler.obtainMessage();
                            message.setData(bundle);
                            message.obj = mMainGoodsHolder.num;
                            handler.sendMessage(message);
                        }
                    }
                });

            }
        });

        /**
         * 点击加号增添一个该商品的数量，数据库添加一个该商品，并且商品展示数量加一，库存量减一
         * */
        mMainGoodsHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notClick) {
                    MyToast.showToast(mContext.getString(R.string.not_click));
                    return;
                }
                //不可进入点击
                notClick = true;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /**
                             * 如果返回-1就是更新失败
                             */
                            tempNumber = CartNumberHelper.upCartNumberMethod(CartNumberHelper.ADD_CART_NUMBER,
                                    position, mList, mContext);

                        } catch (IOException e) {
                            CartNumberHelper.upUI(mContext.getString(R.string.internet_errer), mContext);
                        } finally {
                            Bundle bundle = new Bundle();
                            bundle.putInt("number", tempNumber);
                            bundle.putInt("position", position);
                            Message message = handler.obtainMessage();
                            message.setData(bundle);
                            message.obj = mMainGoodsHolder.num;
                            handler.sendMessage(message);
                        }
                    }
                });
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
        TextView moqTag;
        TextView praiseTag;

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
