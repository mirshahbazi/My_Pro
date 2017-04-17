package top.linjia.wizarposapp.view.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.SecondTypeRecyclerAdapter;
import top.linjia.wizarposapp.beans.SecondLevelGoodsBean;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.view.recyclerview.ListViewDecoration;
import top.linjia.wizarposapp.view.widget.NXHooldeView;

/**
 * @className: top.linjia.wizarposapp.view.fragment SecondLevelContentFragment
 * @description: 二级分类中的具体内容
 * @author 陈文豪
 * @crete 2017/2/11 11:04
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SecondLevelContentFragment extends Fragment implements SecondTypeRecyclerAdapter.FoodActionCallback{

    private View contentView = null;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<SecondLevelGoodsBean> mList;

    public SecondLevelContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {//判断回收池是否为空
            contentView = initLayout(inflater, container, false);
            initView();
        }
        if (contentView != null) {
            return contentView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView() {
        swipeMenuRecyclerView = (SwipeMenuRecyclerView) contentView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.fragment_swipe_layout);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        swipeMenuRecyclerView.setLayoutManager(mLinearLayoutManager);// 布局管理器。
        swipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        swipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        initData();
        swipeMenuRecyclerView.setAdapter(new SecondTypeRecyclerAdapter(getContext(),mList,this));
        swipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    private void initData() {
        mList = new ArrayList<SecondLevelGoodsBean>();
        for (int i = 0; i < 10; i++) {
            mList.add(new SecondLevelGoodsBean());
        }

    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    MyToast.showToast("刷新成功(づ￣ 3￣)づ");
                }
            }, 2000);
        }
    };

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
                swipeMenuRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.showToast("加載成功(づ￣ 3￣)づ");
                    }
                }, 2000);
            }
        }
    };

    private View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View inflate = inflater.inflate(R.layout.second_level_content, container, b);
        return inflate;
    }


    @Override
    public void addAction(View view) {
        NXHooldeView nxHooldeView = new NXHooldeView(getContext());
        int position[] = new int[2];
        view.getLocationInWindow(position);
        nxHooldeView.setStartPosition(new Point(position[0], position[1]));
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        rootView.addView(nxHooldeView);
        int endPosition[] = new int[2];
//        tv_good_fitting_num.getLocationInWindow(endPosition);
        endPosition[0] = 880;
        endPosition[1] = 20;
        nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        nxHooldeView.startBeizerAnimation();
    }
}
