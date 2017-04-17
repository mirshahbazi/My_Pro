package top.linjia.wizarposapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.fragments.GiftCardFragmentUseCard;

/**
 * Created by 河南巧脉信息技术 on 2016/11/9 17:58
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 卡券的activity对应类
 */

public class GiftCardActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    protected ImageView commonTopBackIv;
    protected RadioButton activityGiftCardRbUsecard;
    protected RadioButton activityGiftCardRbGoodscard;
    protected FrameLayout activityGiftCardFramelayout;
    protected TextView commonTopText;
    protected RadioGroup activityGiftCardRg;
    private FragmentManager fm;
    private Fragment giftCardFragmentUseCard, giftGiftCardFragmentGoodsCard, currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_gift_card);
        fm = getSupportFragmentManager();
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_gift_card_top_back_iv) {
            finish();
        }
    }

    private void initView() {
        commonTopBackIv = (ImageView) findViewById(R.id.activity_gift_card_top_back_iv);
        commonTopBackIv.setOnClickListener(GiftCardActivity.this);
        activityGiftCardRbUsecard = (RadioButton) findViewById(R.id.activity_gift_card_rb_usecard);
        activityGiftCardRbGoodscard = (RadioButton) findViewById(R.id.activity_gift_card_rb_goodscard);
        activityGiftCardFramelayout = (FrameLayout) findViewById(R.id.activity_gift_card_framelayout);
        //实例化两个fragment
        giftCardFragmentUseCard = new GiftCardFragmentUseCard();
        giftGiftCardFragmentGoodsCard = new GiftCardFragmentUseCard();


        commonTopText = (TextView) findViewById(R.id.activity_gift_card_top_text);
        activityGiftCardRg = (RadioGroup) findViewById(R.id.activity_gift_card_rg);
        activityGiftCardRg.setOnCheckedChangeListener(this);

        // 先设置提交giftCardFragmentUseCard
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.activity_gift_card_framelayout, giftCardFragmentUseCard).commit();
        currentFragment = giftCardFragmentUseCard;

        //改变抵用券和购物券的字体颜色
        changeTextColor(activityGiftCardRbUsecard);
        returnTextColor(activityGiftCardRbGoodscard);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_gift_card_rb_usecard:
                changeTextColor(activityGiftCardRbUsecard);
                returnTextColor(activityGiftCardRbGoodscard);
                // TODO: 2016/10/25 切换fragment
                showFragment(giftCardFragmentUseCard);

                break;
            case R.id.activity_gift_card_rb_goodscard:
                changeTextColor(activityGiftCardRbGoodscard);
                returnTextColor(activityGiftCardRbUsecard);
                // TODO: 2016/10/25  切换fragment
                showFragment(giftGiftCardFragmentGoodsCard);

                break;
        }
    }

    // 设置选中的字体颜色为白色
    private void changeTextColor(RadioButton tv) {
        tv.setTextColor(getResources().getColor(R.color.white));
    }


    //设置未选中的字体颜色为黄色
    private void returnTextColor(RadioButton tv) {
        tv.setTextColor(getResources().getColor(R.color.yellow));
    }



    /**
     * @MethoName: showFragment
     * @Parame: fg 要显示的fragment的实例
     * @Date: 2016/10/27  15:38
     * @Description: 用来切换抵用券和购物券两个fragment
     * @author： 陈文豪
     */
    private void showFragment(Fragment fg) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (!fg.isAdded()) {
            transaction.hide(currentFragment).add(R.id.activity_gift_card_framelayout, fg);
        } else {
            transaction.hide(currentFragment).show(fg);
        }
        currentFragment = fg;
        transaction.commit();
    }
}
