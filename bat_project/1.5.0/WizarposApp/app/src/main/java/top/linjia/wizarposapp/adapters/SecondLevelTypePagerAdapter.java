package top.linjia.wizarposapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @className: top.linjia.wizarposapp.adapters SecondLevelTypePagerAdapter
 * @description: 二级分类中viewpager的adapter
 * @author 陈文豪
 * @crete 2017/2/10 14:36
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SecondLevelTypePagerAdapter extends FragmentPagerAdapter{

    //定一个Fragment数据源
    private List<Fragment> fragments;
    public SecondLevelTypePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
