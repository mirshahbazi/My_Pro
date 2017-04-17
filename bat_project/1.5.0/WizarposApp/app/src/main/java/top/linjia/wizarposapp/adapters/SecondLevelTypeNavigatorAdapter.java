package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.title.ScaleTransitionPagerTitleView;

import java.util.List;

/**
 * @className: top.linjia.wizarposapp.adapters SecondLevelTypeNavigatorAdapter
 * @description: 二级分类界面指示器的adapter
 * @author 陈文豪
 * @crete 2017/2/16 11:08
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SecondLevelTypeNavigatorAdapter extends CommonNavigatorAdapter{
    private List<String> mList;
    private ViewPager mPager;

    public SecondLevelTypeNavigatorAdapter(List<String> mList, ViewPager mPager) {
        this.mList = mList;
        this.mPager = mPager;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setText(mList.get(index));
        simplePagerTitleView.setTextSize(22);
        simplePagerTitleView.setNormalColor(Color.GRAY);
        simplePagerTitleView.setSelectedColor(Color.BLACK);
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(index);
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        BezierPagerIndicator indicator = new BezierPagerIndicator(context);
        indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
        return indicator;
    }
}
