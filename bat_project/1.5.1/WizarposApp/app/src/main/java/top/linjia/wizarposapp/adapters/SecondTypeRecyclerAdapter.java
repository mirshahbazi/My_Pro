package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.SecondLevelGoodsBean;

/**
 * @className: top.linjia.wizarposapp.adapters SecondTypeRecyclerAdapter
 * @description: 二级分类界面viewpager的adapter
 * @author 陈文豪
 * @crete 2017/2/16 11:06
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SecondTypeRecyclerAdapter extends SwipeMenuAdapter<SecondTypeRecyclerAdapter.SecondTypeViewHolder> implements View.OnClickListener {

    private List<SecondLevelGoodsBean> mList;
    private Context mContext;
    private FoodActionCallback callback;

    public SecondTypeRecyclerAdapter(Context mContext, List<SecondLevelGoodsBean> mList,FoodActionCallback callback) {
        this.mList = mList;
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(mContext).inflate(R.layout.second_level_type_goods_item_layout, parent, false);
    }

    @Override
    public SecondTypeViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new SecondTypeViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SecondTypeViewHolder holder, int position) {
        holder.second_level_item_price.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(callback==null) return;
        callback.addAction(v);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * @className: top.linjia.wizarposapp.adapters SecondTypeRecyclerAdapter
     * @description: 提供pager使用的holder
     * @author 陈文豪
     * @crete 2017/2/16 11:07
     * @copyright: 2017 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    class SecondTypeViewHolder extends RecyclerView.ViewHolder {
        public ImageView second_level_goods_image;
        public ImageView second_level_item_favorable_type;
        public TextView second_level_item_goods_name;
        public TextView second_level_item_standard;
        public TextView second_level_item_create_date;
        public TextView second_level_item_barcode;
        public ImageButton second_level_item_price;

        public SecondTypeViewHolder(View rootView) {
            super(rootView);
            this.second_level_goods_image = (ImageView) rootView.findViewById(R.id.second_level_goods_image);
            this.second_level_item_favorable_type = (ImageView) rootView.findViewById(R.id.second_level_item_favorable_type);
            this.second_level_item_goods_name = (TextView) rootView.findViewById(R.id.second_level_item_goods_name);
            this.second_level_item_standard = (TextView) rootView.findViewById(R.id.second_level_item_standard);
            this.second_level_item_create_date = (TextView) rootView.findViewById(R.id.second_level_item_create_date);
            this.second_level_item_barcode = (TextView) rootView.findViewById(R.id.second_level_item_barcode);
            this.second_level_item_price = (ImageButton) rootView.findViewById(R.id.second_level_item_price);
        }


    }

    public interface FoodActionCallback {
        void addAction(View view);
    }
}
