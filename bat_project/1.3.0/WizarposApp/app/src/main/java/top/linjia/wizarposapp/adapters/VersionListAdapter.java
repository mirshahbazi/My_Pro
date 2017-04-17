package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import top.linjia.wizarposapp.R;

/**
 * Created by 河南巧脉信息技术 on 2016/12/29 15:50
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class VersionListAdapter extends BaseAdapter {
    private Context mContext;
    private HashMap<String, String>[] hashMap;

    public VersionListAdapter(Context mContext, HashMap<String, String>[] hashMap) {
        this.mContext = mContext;
        if (hashMap == null) {
            this.hashMap = new HashMap[0];
            Log.e("error", "hashMap is Null");
        } else {
            this.hashMap = hashMap;
        }
    }

    @Override
    public int getCount() {
        return hashMap.length;
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.version_list_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mVersionListviewItemTitle.setText(hashMap[position].get("version"));
        viewHolder.mVersionListviewItemContent.setText(hashMap[position].get("desc"));

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mVersionListviewItemTitle;
        public TextView mVersionListviewItemContent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mVersionListviewItemTitle = (TextView) rootView.findViewById(R.id.version_listview_item_title);
            this.mVersionListviewItemContent = (TextView) rootView.findViewById(R.id.version_listview_item_content);
        }

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
