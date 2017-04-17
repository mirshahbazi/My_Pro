package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: GoodsGridAdapter
 * @Description: 这个适配器将从网上下载的数据填充到GridView中。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class GoodsGridAdapter extends BaseAdapter {
    Context mContext;
    List<GoodsList.ResultBean.ListBean> mList;
    TextView cartnum;
    UserDao userDao;
    List<User_CartInfo> cartInfos;
    int curnum = 0;           //购物车中所有商品个数
    int goodsnum = 0;        //购物车中具体某类商品数量
    int goodsNumber = 0;  //某类商品库存
    //int shownum=0;          //要展示的商品数量
    int goodsAllNum = 0;   //购物车中所有商品总数量
    int[] positions;
    boolean flag;
    boolean notClick = false;
    int tempNumber = 0;

    public GoodsGridAdapter(Context context, List<GoodsList.ResultBean.ListBean> goods_data, TextView cartnum) {
        this.mContext = context;
        this.mList = goods_data;
        this.cartnum = cartnum;
        userDao = new UserDao(context);
        cartInfos = new ArrayList<>();
        cartInfos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        curnum = cartInfos.size();
        Log.i("test", "数据库中商品列表长度是：" + curnum);
    }

    public void setGoods_datalength(int length) {
        positions = new int[length];
    }

    public void setGoods_datamoq(int[] positions) {
        this.positions = positions;
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        goodsNumber = mList.get(position).getNumber();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_classify_gridview_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_name);
            holder.stname = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_stname);
            holder.price = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_price);
            holder.image = (ImageView) convertView.findViewById(R.id.activity_classify_gridview_item_iv);
            holder.imagesoldout = (ImageView) convertView.findViewById(R.id.activity_classify_gridview_item_iv_soldout);
            holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.activity_classify_gridview_item_iv_fl);
            holder.unit = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_unit);
            holder.store = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_storetext);
            holder.storenum = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_store);
            holder.reduxce = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_reduce);
            holder.num = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_num);
            holder.add = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_plus);
            holder.moq = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_lastnum);
            holder.praise = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_praise);
            holder.llclick = (LinearLayout) convertView.findViewById(R.id.activity_classify_gridview_item_ll);
            holder.afterllclick = (LinearLayout) convertView.findViewById(R.id.activity_classify_gridview_item_cart_ll);
            holder.img = (ImageView) convertView.findViewById(R.id.activity_classify_gridview_item_img);
            holder.praiseTag = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_praise_tag);
            holder.moqTag = (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_lastnum_tag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /**
         * 初始化界面显示内容
         * */
        holder.name.setText(mList.get(position).getName());
        holder.price.setText("￥" + String.valueOf(mList.get(position).getPrice()));
        holder.stname.setText(mList.get(position).getStname());
        Picasso.with(mContext).load(Url.IMAGE_URL + mList.get(position).getPath()).placeholder(R.drawable.place_holder).into(holder.image);
        holder.num.setText(String.valueOf(mList.get(position).getCartNumber()));
        holder.storenum.setText(String.valueOf(mList.get(position).getNumber()));
        holder.moq.setText(mList.get(position).getMoq() + "");
        holder.praise.setText(String.valueOf(mList.get(position).getPraise()));

        /**
         * 判断库存是否售罄，决定是否加载售罄状态图片
         * */
        int curPos = position;
        if (mList.get(position).getNumber() == 0 || mList.get(position).getNumber() < mList.get(position).getMoq()) {
            holder.frameLayout.setTag(curPos);
            holder.imagesoldout.setTag(curPos);
        }
        if (holder.imagesoldout.getTag() != null && holder.imagesoldout.getTag().equals(curPos)) {
            holder.imagesoldout.setVisibility(View.VISIBLE);
            holder.imagesoldout.setImageResource(R.drawable.sold_out);
        } else {
            holder.imagesoldout.setVisibility(View.GONE);
        }

        if (!(mList.get(position).getUnit() == null)) {
            holder.unit.setText("/" + mList.get(position).getUnit());
        }

        /**
         * 指定限订按钮显示方式，’不限/n‘两种方式
         * */
        if (mList.get(position).getPraise() == 0 && holder.praise.getVisibility() != View.GONE) {
            holder.praise.setVisibility(View.GONE);
            holder.praiseTag.setVisibility(View.GONE);
        } else if (mList.get(position).getPraise() != 0 && holder.praise.getVisibility() != View.VISIBLE) {
            holder.praise.setVisibility(View.VISIBLE);
            holder.praiseTag.setVisibility(View.VISIBLE);
        }
        /**
         * 指定起订显示方式
         */
        if (mList.get(position).getMoq() == 1 && holder.moq.getVisibility() != View.GONE) {
            holder.moq.setVisibility(View.GONE);
            holder.moqTag.setVisibility(View.GONE);
        } else if (mList.get(position).getMoq() != 1 && holder.moq.getVisibility() != View.VISIBLE) {
            holder.moq.setVisibility(View.VISIBLE);
            holder.moqTag.setVisibility(View.VISIBLE);
        }

        /**
         * 遍历数据库判断是否展开操作数量加减号
         * */
        holder.llclick.setVisibility(View.VISIBLE);
        holder.afterllclick.setVisibility(View.GONE);
        flag = mList.get(position).getCartNumber() != 0;
        if (flag) {
            holder.num.setText(String.valueOf(mList.get(position).getCartNumber()));
            holder.llclick.setVisibility(View.GONE);
            holder.afterllclick.setVisibility(View.VISIBLE);
        }

        /**
         * 点击加号安钮将商品按起订数量加入购物车，并且展开数量选择符号
         * */
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 判断库存选择是否添加购物车
                 * */
                if (mList.get(position).getNumber() == 0 || mList.get(position).getNumber() < mList.get(position).getMoq()) {
                    MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_shortstore));
                    return;
                }
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
                            tempNumber = CartNumberHelper.upCartNumberMethod(CartNumberHelper.FIRST_ADD_CART_NUMBER,
                                    position, mList, mContext);

                        } catch (IOException e) {
                            CartNumberHelper.upUI(mContext.getString(R.string.internet_errer), mContext);
                        } finally {
                            Bundle bundle = new Bundle();
                            bundle.putInt("number", tempNumber);
                            bundle.putInt("position", position);
                            Message message = handler.obtainMessage();
                            message.setData(bundle);
                            message.obj = holder.num;
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });

        /**
         * 点击减号按钮减去该商品的一个数量，数据库数量减1，展示数量同时减1，库存同时减1
         * */
        holder.reduxce.setOnClickListener(new View.OnClickListener() {
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
                            message.obj = holder.num;
                            handler.sendMessage(message);
                        }
                    }
                });

            }
        });

        /**
         * 点击加号增添一个该商品的数量，数据库添加一个该商品，并且商品展示数量加一，库存量减一
         * */
        holder.add.setOnClickListener(new View.OnClickListener() {
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
                            message.obj = holder.num;
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });
        return convertView;
    }

    /**
     * @ClassName: ViewHolder
     * @Description: 为GridView中的item设置一个static类型holder类，其中为item布局中的控件。
     * @Author: 李鹏鹏
     * @Data: 2016/12/19
     * @Copyright: 河南巧脉信息技术有限公司
     */
    static class ViewHolder {
        TextView price;
        TextView name;
        FrameLayout frameLayout;
        ImageView image;
        ImageView imagesoldout;
        TextView stname;
        TextView unit;
        TextView store;
        TextView storenum;
        TextView moq;
        TextView praise;
        TextView num;
        TextView reduxce;
        TextView add;
        LinearLayout llclick;
        ImageView img;
        LinearLayout afterllclick;
        TextView praiseTag;
        TextView moqTag;
    }

    /**
     * @Title: cartTraverse
     * @Description: 遍历购物车，返回布尔类型的值用来表示购物车中是否包含该商品
     * @param: List<User_CartInfo> String
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    private boolean cartTraverse(List<User_CartInfo> cartInfos, String goodsname) {
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

    /**
     * @Title: isClick
     * @Description: 根据商品库存是否为零或者库存小于起订判定是否可加入购物车
     * @Params: img number moq
     * @Data: 2017/1/19 17:11
     * @Author: 李鹏鹏
     */
    public void isClick(ImageView img, int number, int moq) {
        if (number <= 0 || number < moq) {
            MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_shortstore));
            return;
        }
    }
}
