package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import top.linjia.wizarposapp.R;

/**
 * Created by 邻家新锐 on 2016/10/24 18:02
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class SpinnerAdapter extends BaseAdapter{

    private List<String> list;
    private LayoutInflater mInflater;
    public SpinnerAdapter(List<String> mList,Context mContext) {
        this.list = mList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView mTextView;
        if(convertView == null){
            convertView = this.mInflater.inflate(R.layout.login_spinner_item, null);
        }
        mTextView = (TextView) convertView.findViewById(R.id.login_spinner_text);
        mTextView.setText(list.get(position));

        return convertView;
    }
}
