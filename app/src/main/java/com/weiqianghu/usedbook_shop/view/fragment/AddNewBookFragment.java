package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.ShopBean;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.presenter.SavePresenter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.customview.ClearEditText;
import com.weiqianghu.usedbook_shop.view.view.ISaveView;

import cn.bmob.v3.BmobUser;

public class AddNewBookFragment extends BaseFragment implements ISaveView {
    public static final String TAG = AddNewBookFragment.class.getSimpleName();

    private ClearEditText mBookNameEt;
    private ClearEditText mISBNEt;
    private ClearEditText mPriceEt;
    private ClearEditText mPressEt;
    private ClearEditText mAuthorEt;
    private ClearEditText mCategoryEt;
    private ClearEditText mStockEt;
    private ClearEditText mPercentDescribeEt;
    private Button mSubmitBtn;

    private String bookName;
    private String isbn;
    private double price;
    private String press;
    private String author;
    private String category;
    private int stock;
    private String percentDescribe;

    private BookBean book;

    private SavePresenter mSavePresenter;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private Toolbar mToolBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_new_book;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolBar= (Toolbar) getActivity().findViewById(R.id.center_toolbar);
        mToolBar.setTitle(R.string.add_new_book);
        mBookNameEt = (ClearEditText) mRootView.findViewById(R.id.et_book_name);
        mISBNEt = (ClearEditText) mRootView.findViewById(R.id.et_isbn);
        mPriceEt = (ClearEditText) mRootView.findViewById(R.id.et_price);
        mPressEt = (ClearEditText) mRootView.findViewById(R.id.et_press);
        mAuthorEt = (ClearEditText) mRootView.findViewById(R.id.et_author);
        mCategoryEt = (ClearEditText) mRootView.findViewById(R.id.et_category);
        mStockEt = (ClearEditText) mRootView.findViewById(R.id.et_stock);
        mPercentDescribeEt = (ClearEditText) mRootView.findViewById(R.id.et_percent_describe);

        Click click = new Click();

        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(click);

        mSavePresenter = new SavePresenter(this, saveHandler);
    }

    CallBackHandler saveHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    mSubmitBtn.setClickable(true);
                    gotoUploadBookImgs();
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mSubmitBtn.setClickable(true);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private void gotoUploadBookImgs() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(UploadBookImgsFragment.TAG);
        if (mFragment == null) {
            mFragment = new UploadBookImgsFragment();
        }
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.BOOK,book);
        mFragment.setArguments(bundle);

        Fragment from = mFragmentManager.findFragmentByTag(AddNewBookFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, UploadBookImgsFragment.TAG);
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    if (beforeSubmit()) {
                        mSubmitBtn.setClickable(false);
                        mSavePresenter.save(getActivity(), book);
                    }
                    break;
            }
        }
    }

    private boolean beforeSubmit() {
        getInput();
        if (bookName == null || bookName.length() < 1 ||
                isbn == null || isbn.length() < 1 ||
                price == 0 ||
                press == null || press.length() < 1 ||
                author == null || author.length() < 1 ||
                stock == 0 ||
                category == null || category.length() < 1 ||
                percentDescribe == null || percentDescribe.length() < 1) {
            Toast.makeText(getActivity(), "请填写完所有内容后再提交", Toast.LENGTH_SHORT).show();
            return false;
        }

        ShopBean shopBean = BmobUser.getCurrentUser(getActivity(), UserBean.class).getShop();

        if (book == null) {
            book = new BookBean();
        }

        book.setBookName(bookName);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setAuthor(author);
        book.setPress(press);
        book.setCategory(category);
        book.setPercentDescribe(percentDescribe);
        book.setStock(stock);
        book.setSell(true);
        book.setSalesVolume(0);
        book.setPercent(0);
        book.setShop(shopBean);

        return true;
    }

    private void getInput() {
        bookName = mBookNameEt.getText().toString().trim();
        isbn = mISBNEt.getText().toString().trim();
        String priceStr = mPriceEt.getText().toString().trim();
        if (priceStr != null && priceStr.length() > 0) {
            price = Double.valueOf(priceStr);
        }
        press = mPressEt.getText().toString().trim();
        author = mAuthorEt.getText().toString().trim();

        String stockStr = mStockEt.getText().toString().trim();
        if (stockStr != null && stockStr.length() > 0) {
            stock = Integer.valueOf(stockStr);
        }
        category = mCategoryEt.getText().toString().trim();
        percentDescribe = mPercentDescribeEt.getText().toString().trim();
    }

}
