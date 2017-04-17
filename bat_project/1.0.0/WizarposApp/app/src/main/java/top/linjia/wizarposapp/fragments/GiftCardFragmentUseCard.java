package top.linjia.wizarposapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.linjia.wizarposapp.R;

/**
 * @Created by:河南巧脉信息技术有限公司
 * @ClassName: GiftCardFragmentUseCard
 * @date 创建时间 2016/10/27
 * @author: 王文亮
 * @Description: 卡券fragment
 * @version: V1.0
 */
public class GiftCardFragmentUseCard extends Fragment implements View.OnClickListener {
    protected View rootView;
    protected TextView fragmentGiftCardCanusetext;
    protected TextView fragmentGiftCardCanusenum;
    protected View fragmentGiftCardCanuseview;
    protected RelativeLayout fragmentGiftCardRlCanuse;
    protected TextView fragmentGiftCardAlreadyusetext;
    protected TextView fragmentGiftCardAlreadyusenum;
    protected View fragmentGiftCardAlreadyuseview;
    protected RelativeLayout fragmentGiftCardRlAlreadyuse;
    protected TextView fragmentGiftCardOutofdatetext;
    protected TextView fragmentGiftCardOutofdatenum;
    protected View fragmentGiftCardOutofdateview;
    protected RelativeLayout fragmentGiftCardRlOutofdate;
    protected GridView fragmentGiftCardGridview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_gift_card, null);


        initView(rootView);
        changeTextColor(fragmentGiftCardCanusetext,fragmentGiftCardCanusenum,fragmentGiftCardCanuseview,fragmentGiftCardAlreadyusetext,fragmentGiftCardAlreadyusenum,fragmentGiftCardAlreadyuseview,fragmentGiftCardOutofdatetext,fragmentGiftCardOutofdatenum,fragmentGiftCardOutofdateview);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fragment_gift_card_rl_canuse) {
            changeTextColor(fragmentGiftCardCanusetext,fragmentGiftCardCanusenum,fragmentGiftCardCanuseview,fragmentGiftCardAlreadyusetext,fragmentGiftCardAlreadyusenum,fragmentGiftCardAlreadyuseview,fragmentGiftCardOutofdatetext,fragmentGiftCardOutofdatenum,fragmentGiftCardOutofdateview);
            // TODO: 2016/10/26   每次点击时刷新gridview中的数据


        } else if (view.getId() == R.id.fragment_gift_card_rl_alreadyuse) {
            changeTextColor(fragmentGiftCardAlreadyusetext,fragmentGiftCardAlreadyusenum,fragmentGiftCardAlreadyuseview,fragmentGiftCardCanusetext,fragmentGiftCardCanusenum,fragmentGiftCardCanuseview,fragmentGiftCardOutofdatetext,fragmentGiftCardOutofdatenum,fragmentGiftCardOutofdateview);
            // TODO: 2016/10/26   每次点击时刷新gridview中的数据


        } else if (view.getId() == R.id.fragment_gift_card_rl_outofdate) {
            changeTextColor(fragmentGiftCardOutofdatetext,fragmentGiftCardOutofdatenum,fragmentGiftCardOutofdateview,fragmentGiftCardCanusetext,fragmentGiftCardCanusenum,fragmentGiftCardCanuseview,fragmentGiftCardAlreadyusetext,fragmentGiftCardAlreadyusenum,fragmentGiftCardAlreadyuseview);
            // TODO: 2016/10/26   每次点击时刷新gridview中的数据

        }
    }

    private void initView(View rootView) {
        fragmentGiftCardCanusetext = (TextView) rootView.findViewById(R.id.fragment_gift_card_canusetext);
        fragmentGiftCardCanusenum = (TextView) rootView.findViewById(R.id.fragment_gift_card_canusenum);
        fragmentGiftCardCanuseview = (View) rootView.findViewById(R.id.fragment_gift_card_canuseview);
        fragmentGiftCardRlCanuse = (RelativeLayout) rootView.findViewById(R.id.fragment_gift_card_rl_canuse);
        fragmentGiftCardRlCanuse.setOnClickListener(GiftCardFragmentUseCard.this);
        fragmentGiftCardAlreadyusetext = (TextView) rootView.findViewById(R.id.fragment_gift_card_alreadyusetext);
        fragmentGiftCardAlreadyusenum = (TextView) rootView.findViewById(R.id.fragment_gift_card_alreadyusenum);
        fragmentGiftCardAlreadyuseview = (View) rootView.findViewById(R.id.fragment_gift_card_alreadyuseview);
        fragmentGiftCardRlAlreadyuse = (RelativeLayout) rootView.findViewById(R.id.fragment_gift_card_rl_alreadyuse);
        fragmentGiftCardRlAlreadyuse.setOnClickListener(GiftCardFragmentUseCard.this);
        fragmentGiftCardOutofdatetext = (TextView) rootView.findViewById(R.id.fragment_gift_card_outofdatetext);
        fragmentGiftCardOutofdatenum = (TextView) rootView.findViewById(R.id.fragment_gift_card_outofdatenum);
        fragmentGiftCardOutofdateview = (View) rootView.findViewById(R.id.fragment_gift_card_outofdateview);
        fragmentGiftCardRlOutofdate = (RelativeLayout) rootView.findViewById(R.id.fragment_gift_card_rl_outofdate);
        fragmentGiftCardRlOutofdate.setOnClickListener(GiftCardFragmentUseCard.this);
        fragmentGiftCardGridview = (GridView) rootView.findViewById(R.id.fragment_gift_card_gridview);
    }

    // 1 为选中状态  2和3为未选中状态
    private void changeTextColor(TextView tvname1,TextView tvnum1,View view1,TextView tvname2,TextView tvnum2,View view2,TextView tvname3,TextView tvnum3,View view3){

        tvname1.setTextColor(getResources().getColor(R.color.yellow));
        tvnum1.setTextColor(getResources().getColor(R.color.yellow));
        view1.setBackgroundColor(getResources().getColor(R.color.yellow));

        tvname2.setTextColor(getResources().getColor(R.color.black));
        tvnum2.setTextColor(getResources().getColor(R.color.black));
        view2.setBackgroundColor(getResources().getColor(R.color.realwhite));

        tvname3.setTextColor(getResources().getColor(R.color.black));
        tvnum3.setTextColor(getResources().getColor(R.color.black));
        view3.setBackgroundColor(getResources().getColor(R.color.realwhite));

    }



}
