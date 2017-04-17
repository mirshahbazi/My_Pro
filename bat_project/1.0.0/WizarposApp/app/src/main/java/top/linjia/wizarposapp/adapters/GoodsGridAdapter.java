package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * ClassName: GoodsGridAdapter
 * Description: 这个适配器将从网上下载的数据填充到GridView中
 * Created by 邻家新锐 on 2016/10/28 9:20
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class GoodsGridAdapter extends BaseAdapter{
    Context context;
    List<GoodsList.ResultBean.ListBean> goods_data;
    TextView cartnum;
    UserDao userDao;
    List<User_CartInfo> cartInfos;
    int curnum=0;           //购物车中所有商品个数
    int goodsnum=0;        //购物车中具体某类商品数量
    int goodsNumber=0;  //某类商品库存
    int shownum=0;          //要展示的商品数量
    int [] positions;
    boolean flag;
    Handler handler;
    public GoodsGridAdapter(Context context, List<GoodsList.ResultBean.ListBean> goods_data,TextView cartnum){
        this.context=context;
        this.goods_data=goods_data;
        this.cartnum=cartnum;
        userDao=new UserDao(context);
        cartInfos=new ArrayList<>();
        cartInfos=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        curnum=cartInfos.size();
       // initHandler();
    }
    public void setGoods_datalength(int length){
        positions=new int[length];
    }
    public void setGoods_datamoq(int[] positions){
        this.positions=positions;
    }
    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what){
                    case 0:
                        cartnum.setText(cartInfos.size()+"");
                        break;
                }
            }
        };
    }
    @Override
    public int getCount() {
        return goods_data.size();
    }

    @Override
    public Object getItem(int position) {
        return goods_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        goodsNumber=goods_data.get(position).getNumber();
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_classify_gridview_item,null);
            holder.name=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_name);
            holder.price=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_price);
            holder.image=(ImageView)convertView.findViewById(R.id.activity_classify_gridview_item_iv);
            holder.unit=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_unit);
            holder.store=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_stname);
            holder.storenum= (TextView) convertView.findViewById(R.id.activity_classify_gridview_item_store);
            holder.reduxce=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_reduce);
            holder.num=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_num);
            holder.add=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_plus);
            holder.moq=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_lastnum);
            holder.praise=(TextView)convertView.findViewById(R.id.activity_classify_gridview_item_praise);
            holder.llclick=(LinearLayout)convertView.findViewById(R.id.activity_classify_gridview_item_ll);
            holder.afterllclick= (LinearLayout) convertView.findViewById(R.id.activity_classify_gridview_item_cart_ll);
            holder.img=(ImageView)convertView.findViewById(R.id.activity_classify_gridview_item_img);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        //初始化界面显示内容
        holder.name.setText(goods_data.get(position).getName());
        holder.price.setText("￥"+String.valueOf(goods_data.get(position).getPrice()));
        Picasso.with(context).load(Url.IMAGE_URL+goods_data.get(position).getPath()).placeholder(R.drawable.place_holder).into(holder.image);
        if(!(goods_data.get(position).getUnit()==null)){
            holder.unit.setText("/"+goods_data.get(position).getUnit());
        }
        holder.num.setText(String.valueOf(positions[position]));
        holder.storenum.setText(String.valueOf(goods_data.get(position).getNumber()));
        holder.moq.setText(goods_data.get(position).getMoq()+"");

        //指定限订按钮显示方式，’不限/n‘两种方式
        if(goods_data.get(position).getPraise()<=0||goods_data.get(position).getPraise()<goods_data.get(position).getMoq()||goods_data.get(position).getPraise()>goods_data.get(position).getNumber()){
            holder.praise.setText("不限");
        }else{
            holder.praise.setText(goods_data.get(position).getPraise()+"");
        }

        //遍历数据库判断是否展开操作数量加减号
        holder.llclick.setVisibility(View.VISIBLE);
        holder.afterllclick.setVisibility(View.GONE);
        cartInfos=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        flag=cartTraverse(cartInfos,goods_data.get(position).getName());
        if(flag){
                    holder.num.setText(String.valueOf(userDao.queryGoodsNum(goods_data.get(position).getName())));
                    holder.llclick.setVisibility(View.GONE);
                    holder.afterllclick.setVisibility(View.VISIBLE);
        }

        //点击加号安钮将商品按起订数量加入购物车，并且展开数量选择符号
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.afterllclick.setTag(position);
                holder.llclick.setTag(position);
                holder.llclick.setVisibility(View.GONE);
                holder.afterllclick.setVisibility(View.VISIBLE);
                User_CartInfo user_cartInfo=new User_CartInfo();
                user_cartInfo.setGoodsname(goods_data.get(position).getName());
                user_cartInfo.setStname(goods_data.get(position).getStname());
                user_cartInfo.setPrice(goods_data.get(position).getPrice());
                user_cartInfo.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                user_cartInfo.setNum(positions[position]);
                user_cartInfo.setGoodsId(goods_data.get(position).getGoodsId());
                user_cartInfo.setPraise(goods_data.get(position).getPraise());
                user_cartInfo.setMoq(goods_data.get(position).getMoq());
                int disDyq;
                int disGwq;
                if(goods_data.get(position).isDisDyq()){
                    disDyq=0;
                }else{
                    disDyq=1;
                }if(goods_data.get(position).isDisGwq()){
                    disGwq=0;
                }else{
                    disGwq=1;
                }
                user_cartInfo.setDisDyq(disDyq);
                user_cartInfo.setDisGwq(disGwq);
                flag=userDao.insertCart(user_cartInfo);
                if(flag){
                    MyToast.showToast("添加成功！");
                    cartnum.setText(String.valueOf(userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size()));
                    holder.num.setText(String.valueOf(userDao.queryGoodsNum(goods_data.get(position).getName())));
                }else{
                    MyToast.showToast("哎呀，出错啦！");
                    holder.num.setText(String.valueOf(goods_data.get(position).getMoq()));
                }
            }
        });

        //点击减号按钮减去该商品的一个数量，数据库数量减1，展示数量同时减1，库存同时减1
        holder.reduxce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cartInfos=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
                    flag=cartTraverse(cartInfos,goods_data.get(position).getName());
                    if(flag){
                        //每次点击减号后去购物车中获取该商品的购物车数量然后由这个数量决定是否减1
                        goodsnum=userDao.queryGoodsNum(goods_data.get(position).getName());
                        if(goodsnum>goods_data.get(position).getMoq()){
                            goodsnum-=1;
                            boolean flag=userDao.updateCart(goodsnum,goods_data.get(position).getName());
                            if(flag){
                                holder.num.setText(String.valueOf(goodsnum));
                                curnum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
                                cartnum.setText(""+curnum);
                                MyToast.showToast("成功减去一件商品");
                            }else{
                                MyToast.showToast("哎呀，出错啦！");
                            }
                        }else{
                            boolean flag=userDao.deletefromCart(goods_data.get(position).getName());
                            if(flag){
                                goodsnum=0;
                                curnum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
                                positions[position]=goods_data.get(position).getMoq();
                                holder.num.setText(String.valueOf(0));
                                cartnum.setText(curnum+"");
                                holder.llclick.setVisibility(View.VISIBLE);
                                holder.afterllclick.setVisibility(View.GONE);
                                MyToast.showToast("已从购物车中删除该商品");
                            }else{
                                MyToast.showToast("哎呀，出错啦！");
                            }
                        }
                    }else{
                        MyToast.showToast("购物车中暂时没有该商品");
                    }

            }
        });

        //点击加号增添一个该商品的数量，数据库添加一个该商品，并且商品展示数量加一，库存量减一
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test","当前点击的条目是：----------------------------"+position);
                cartInfos=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
                flag=cartTraverse(cartInfos,goods_data.get(position).getName());
                if(flag){
                    goodsnum=userDao.queryGoodsNum(goods_data.get(position).getName());
                    holder.num.setText(String.valueOf(goodsnum));
                        if((goodsnum+1)<=goods_data.get(position).getNumber()){
                            User_CartInfo user_cartInfo=new User_CartInfo();
                            user_cartInfo.setGoodsname(goods_data.get(position).getName());
                            user_cartInfo.setStname(goods_data.get(position).getStname());
                            user_cartInfo.setPrice(goods_data.get(position).getPrice());
                            user_cartInfo.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                            user_cartInfo.setNum(1);
                            user_cartInfo.setGoodsId(goods_data.get(position).getGoodsId());
                            user_cartInfo.setPraise(goods_data.get(position).getPraise());
                            user_cartInfo.setMoq(goods_data.get(position).getMoq());
                            int disDyq;
                            int disGwq;
                            if(goods_data.get(position).isDisDyq()){
                                disDyq=0;
                            }else{
                                disDyq=1;
                            }if(goods_data.get(position).isDisGwq()){
                                disGwq=0;
                            }else{
                                disGwq=1;
                            }
                            user_cartInfo.setDisDyq(disDyq);
                            user_cartInfo.setDisGwq(disGwq);
                            Log.i("test","已经加入购物车");
                            if(goods_data.get(position).getPraise()<0||goods_data.get(position).getMoq()>goods_data.get(position).getPraise()){
                                //该情况视为不限
                                goodsnum+=1;
                                boolean flag=userDao.insertCart(user_cartInfo);
                                if(flag){
                                    MyToast.showToast("已加入购物车");
                                    holder.num.setText(String.valueOf(goodsnum));
                                }else{
                                    MyToast.showToast("哎呀，出错啦！");
                                }
                            }else if(goods_data.get(position).getMoq()<goods_data.get(position).getPraise()){
                                //该请况是包含限定的情况
                                if(goodsnum<goods_data.get(position).getPraise()){
                                    goodsnum+=1;
                                    boolean flag=userDao.insertCart(user_cartInfo);
                                    if(flag){
                                        MyToast.showToast("成功添加一件商品");
                                        holder.num.setText(String.valueOf(goodsnum));
                                    }else{
                                        MyToast.showToast("哎呀，出错啦！");
                                    }
                                }else{
                                    MyToast.showToast("当前数量已是最大限订量");
                                }
                            }else{

                            }
                        }else{
                            MyToast.showToast("已经没有更多库存啦！");
                        }
                    curnum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
                    cartnum.setText(curnum+"");
                }else{
                    //MyToast.showToast("哎呀，出错啦！");
                    //需求变更后该情况不存在
                    //购物车中不包含该种商品，点击加号是按照起订量加入购物车
                    flag=true;
                    int disDyq;
                    int disGwq;
                    User_CartInfo user_cartInfo=new User_CartInfo();
                    user_cartInfo.setGoodsname(goods_data.get(position).getName());
                    user_cartInfo.setStname(goods_data.get(position).getStname());
                    user_cartInfo.setPrice(goods_data.get(position).getPrice());
                    user_cartInfo.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                    user_cartInfo.setNum(goods_data.get(position).getMoq());
                    user_cartInfo.setGoodsId(goods_data.get(position).getGoodsId());
                    user_cartInfo.setPraise(goods_data.get(position).getPraise());
                    user_cartInfo.setMoq(goods_data.get(position).getMoq());
                    if(goods_data.get(position).isDisDyq()){
                        disDyq=0;
                    }else{
                        disDyq=1;
                    }if(goods_data.get(position).isDisGwq()){
                        disGwq=0;
                    }else{
                        disGwq=1;
                    }
                    user_cartInfo.setDisDyq(disDyq);
                    user_cartInfo.setDisGwq(disGwq);
                    boolean insertcart=userDao.insertCart(user_cartInfo);
                    if(insertcart){
                        MyToast.showToast("已加入购物车");
                        holder.num.setText(String.valueOf(goods_data.get(position).getMoq()));
                        curnum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
                        cartnum.setText(curnum+"");
                    }
                                }
            }
        });
        return convertView;
    }
    static class ViewHolder{
        TextView price;
        TextView name;
        ImageView image;
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
    }
    private boolean cartTraverse(List<User_CartInfo> cartInfos,String goodsname){
        for(int i=0;i<cartInfos.size();i++){
            if(cartInfos.get(i).getGoodsname().equals(goodsname)){
                return true;
            }
        }
        return false;
    }
}
