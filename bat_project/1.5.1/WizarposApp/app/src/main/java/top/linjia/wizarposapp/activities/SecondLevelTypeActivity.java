package top.linjia.wizarposapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.SecondLevelTypeNavigatorAdapter;
import top.linjia.wizarposapp.adapters.SecondLevelTypePagerAdapter;
import top.linjia.wizarposapp.view.fragment.SecondLevelContentFragment;
import top.linjia.wizarposapp.view.fragment.TitleFragment;

/**
 * @className: top.linjia.wizarposapp.activities SecondLevelTypeActivity
 * @description: 二级分类界面 activity
 * @author 陈文豪
 * @crete 2017/2/16 11:08
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SecondLevelTypeActivity extends AppCompatActivity {

    private MagicIndicator mSecondLevelTypeIndicator;
    private ViewPager mSecondLevelTypeViewPager;
    private ArrayList<Fragment> al;
    private ArrayList<String> arrayList;
    private TitleFragment mSecondLevelTitleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level_type);
        initData();
        initView();
    }

    private void initData() {
        arrayList = new ArrayList<>();
        al = new ArrayList<>();
        arrayList.add("全部");
        arrayList.add("水果");
        arrayList.add("饮料");
        arrayList.add("酒水");
        arrayList.add("服装");
        al.add(new SecondLevelContentFragment());
        al.add(new SecondLevelContentFragment());
        al.add(new SecondLevelContentFragment());
        al.add(new SecondLevelContentFragment());
        al.add(new SecondLevelContentFragment());

    }

    private void initView() {
        mSecondLevelTypeIndicator = (MagicIndicator) findViewById(R.id.second_level_type_indicator);
        mSecondLevelTypeViewPager = (ViewPager) findViewById(R.id.second_level_type_view_pager);

        mSecondLevelTitleFragment = (TitleFragment) getFragmentManager().findFragmentById(R.id.second_level_title_fragment);
        mSecondLevelTitleFragment.thisWhereActivity(TitleFragment.CLASSIFY);

        pagerBind();

    }

    private void pagerBind() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new SecondLevelTypeNavigatorAdapter(arrayList,mSecondLevelTypeViewPager));
        mSecondLevelTypeIndicator.setNavigator(commonNavigator);
        SecondLevelTypePagerAdapter secondLevelTypePagerAdapter = new SecondLevelTypePagerAdapter(this.getSupportFragmentManager(),al);
        mSecondLevelTypeViewPager.setAdapter(secondLevelTypePagerAdapter);
        mSecondLevelTypeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSecondLevelTypeIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mSecondLevelTypeIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mSecondLevelTypeIndicator.onPageScrollStateChanged(state);
            }
        });
    }
}
