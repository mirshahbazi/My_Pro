package top.linjia.wizarposapp.view.fragment;

import android.app.Fragment;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;
import java.util.zip.Inflater;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.activities.CartActivity;
import top.linjia.wizarposapp.activities.ClassifyActivity;
import top.linjia.wizarposapp.activities.MainActivity;
import top.linjia.wizarposapp.activities.PersonActivity;
import top.linjia.wizarposapp.activities.SearchActivity;
import top.linjia.wizarposapp.customs.MyEditText;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.view.widget.RollTextView;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.view.fragment TitleFragment
 * @description: Title的fragment 增强代码复用
 * @crete 2017/2/14 16:43
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class TitleFragment extends Fragment implements View.OnClickListener {

    private View mContentView = null;
    private ImageView mCommonTopBackIv;
    private RollTextView mCommonTopText;
    private MyEditText mCommonTopSearch;
    private TextView mCommonTopZhaohuo;
    private TextView mCategoryIcon;
    private RelativeLayout mCommonTopCategory;
    private TextView mCategoryCartnum;
    private AbsoluteLayout mCommonTopCart;
    private TextView mCommonTopPersonal;
    private ImageView mCart;
    private TextView mCartNum;
    private AbsoluteLayout mCommonCart;
    public final static int FIND_THE_GOODS = 0;
    public final static int CLASSIFY = 1;
    public final static int PERSIONAL = 2;
    public final static int ORDER_FORM = 3;
    private View mOrderIndicator;
    private View mCategoryIndicator;
    private View mPersonalIndicator;
    private View mFindTheGoods;

    public TitleFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.app_title_layout, container, true);
            initView(mContentView);
        }
        if (mContentView != null) {
            return mContentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(View view) {
        mCommonTopZhaohuo = (TextView) view.findViewById(R.id.common_top_zhaohuo);
        mCommonTopCart = (AbsoluteLayout) view.findViewById(R.id.common_top_cart);
        mCommonTopPersonal = (TextView) view.findViewById(R.id.common_top_personal);
        mCommonTopSearch = (MyEditText) view.findViewById(R.id.common_top_search);
        mCommonTopBackIv = (ImageView) view.findViewById(R.id.common_top_back_iv);
        mCommonTopText = (RollTextView) view.findViewById(R.id.common_top_text);

        /**
         * 四个指示框
         */
        mCommonTopCategory = (RelativeLayout) view.findViewById(R.id.common_top_category);
        mOrderIndicator = view.findViewById(R.id.order_indicator);
        mCategoryIndicator = view.findViewById(R.id.category_indicator);
        mPersonalIndicator = view.findViewById(R.id.personal_indicator);
        mFindTheGoods = view.findViewById(R.id.zhaohuo_indicator);

        mCommonTopCategory.setOnClickListener(this);
        mCommonTopZhaohuo.setOnClickListener(this);
        mCommonTopCart.setOnClickListener(this);
        mCommonTopPersonal.setOnClickListener(this);
        mCommonTopSearch.setOnClickListener(this);
        mCommonTopBackIv.setOnClickListener(this);
        mCommonTopText.setText(WizarPosApp.getmPersonalInfo().getResult().getAddress());
    }

    public void thisWhereActivity(int flag) {
        switch (flag) {
            case FIND_THE_GOODS:
                mFindTheGoods.setVisibility(View.VISIBLE);
                break;
            case ORDER_FORM:
                mOrderIndicator.setVisibility(View.VISIBLE);
                break;
            case PERSIONAL:
                mPersonalIndicator.setVisibility(View.VISIBLE);
                break;
            case CLASSIFY:
                mCategoryIndicator.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.common_top_zhaohuo:
                intent = new Intent(getActivity(), MainActivity.class);
                break;
            case R.id.common_top_cart:
                intent = new Intent(getActivity(), CartActivity.class);
                break;
            case R.id.common_top_personal:
                intent = new Intent(getActivity(), PersonActivity.class);
                break;
            case R.id.common_top_category:
                intent = new Intent(getActivity(), ClassifyActivity.class);
                break;
            case R.id.common_top_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                break;
        }

        /**
         * 使用类的class文件判断而做出对应的跳转逻辑
         */
        if (intent != null) {
            if (getActivity().getClass().getName().equals(intent.getComponent().getClassName())) {
                MyToast.showToast("所要跳转的已经是当前界面！");
            } else {

                startActivity(intent);
                if (!intent.getComponent().getClassName().equals(SearchActivity.class.getName())) {
                    getActivity().finish();
                }
            }
        }
    }
}
