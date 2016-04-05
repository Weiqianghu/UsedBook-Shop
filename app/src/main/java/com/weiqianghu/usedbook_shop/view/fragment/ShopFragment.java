package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.BookModel;
import com.weiqianghu.usedbook_shop.model.entity.SerializableHandler;
import com.weiqianghu.usedbook_shop.presenter.QueryBookImgsPresenter;
import com.weiqianghu.usedbook_shop.presenter.QueryBooksPresenter;
import com.weiqianghu.usedbook_shop.presenter.adapter.BookAdapter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.customview.EmptyRecyclerView;
import com.weiqianghu.usedbook_shop.view.view.IRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends BaseFragment implements IRecycleViewItemClickListener {
    public static final String TAG = ShopFragment.class.getSimpleName();

    public ShopFragment() {
    }

    private Button mAddNewBookBtn;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private EmptyRecyclerView mRecyclerView;
    private List<BookModel> mData = new ArrayList();
    private List<BookBean> mBooks = new ArrayList<>();
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private QueryBooksPresenter mQueryBooksPresenter;
    private QueryBookImgsPresenter mQueryBookImgsPresenter;
    private boolean isRefresh = false;
    private int count = 0;
    private static final int STEP = 15;

    @Override
    protected int getLayoutId() {
        Fresco.initialize(getActivity());
        return R.layout.fragment_shop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (isFirstIn) {
            initView(savedInstanceState);
            isFirstIn = false;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();

        mAddNewBookBtn = (Button) mRootView.findViewById(R.id.btn_add_new_book);
        mAddNewBookBtn.setOnClickListener(click);

        mRecyclerView = (EmptyRecyclerView) mRootView.findViewById(R.id.recyclerview);
        View empty = mRootView.findViewById(R.id.book_empty);
        mRecyclerView.setEmptyView(empty);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new BookAdapter(mData, R.layout.item_book);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(onScrollListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                isRefresh = true;
                queryData(count * STEP, STEP);
            }
        });

        mQueryBooksPresenter = new QueryBooksPresenter(queryBooksHandler);
        mQueryBookImgsPresenter = new QueryBookImgsPresenter(queryBookImgsHandler);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 500);
    }

    private void initData() {
        Log.d("initData", "initData");
        isRefresh = true;
        count = 0;
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                queryData(count * STEP, STEP);
            }
        });

    }

    private void queryData(int start, int step) {
        mQueryBooksPresenter.queryBooks(getActivity(), start, step);
    }

    CallBackHandler queryBooksHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);
                    if (list != null && list.size() > 0) {
                        mBooks.clear();
                        mBooks.addAll(list);
                        for (int i = 0, length = mBooks.size(); i < length; i++) {
                            mQueryBookImgsPresenter.queryBookImgs(getActivity(), (BookBean) list.get(i));
                        }
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    CallBackHandler queryBookImgsHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);
                    BookBean bookBean = bundle.getParcelable(Constant.BOOK);

                    BookModel bookModel = new BookModel();
                    bookModel.setBook(bookBean);
                    bookModel.setBookImgs(list);

                    if (isRefresh) {
                        mData.clear();
                        isRefresh = false;
                    }
                    mData.add(bookModel);

                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void onItemClick(View view, int postion) {
        gotoBookDetail(postion);
    }

    private void gotoBookDetail(int position) {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(BookDetailFragment.TAG);
        if (mFragment == null) {
            mFragment = new BookDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BOOK, mData.get(position));

        mFragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, BookDetailFragment.TAG);
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_new_book:
                    gotoAddNewBook();
                    break;
            }
        }
    }

    private void gotoAddNewBook() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(AddNewBookFragment.TAG);
        if (mFragment == null) {
            mFragment = new AddNewBookFragment();
        }
        Fragment form = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(form, mFragment, R.id.main_container, mFragmentManager, AddNewBookFragment.TAG);
    }

    private void loadMore() {
        count++;
        queryData(count * STEP, STEP);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int totalItemCount;
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (lastVisibleItem >= totalItemCount - 1) {
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            totalItemCount = mLayoutManager.getItemCount();
        }
    };
}
