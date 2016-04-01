package com.weiqianghu.usedbook_shop.view.fragment;


import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.BookImgsBean;
import com.weiqianghu.usedbook_shop.model.entity.BookModel;
import com.weiqianghu.usedbook_shop.presenter.UpdatePresenter;
import com.weiqianghu.usedbook_shop.presenter.adapter.MViewPagerAdapter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.view.IUpdateView;

import java.util.ArrayList;
import java.util.List;


public class BookDetailFragment extends BaseFragment implements IUpdateView {
    public static final String TAG = BookDetailFragment.class.getSimpleName();

    private Toolbar mToolbar;
    private ViewPager mBookImgVp;
    private TextView mPostionTv;
    private BookModel mBookModel;

    private TextView mBookNeamTv;
    private TextView mBookIsbnTv;
    private TextView mBookAuthorTv;
    private TextView mBookPressTv;
    private TextView mBookSalesVolumeTv;
    private TextView mBookPriceTv;
    private TextView mBookCategoryTv;
    private TextView mBookPercentescribeTv;
    private TextView mBookStockTv;

    private TextView mEditPriceTv;
    private TextView mEditCategoryTv;
    private TextView mEditPercentDescribeTv;
    private TextView mEditStockTv;

    private Button mBookIsSellBtn;

    private UpdatePresenter<BookBean> mUpdatePresenter;

    private FragmentManager mFragmentManager;

    private View mComment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initView(savedInstanceState);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBookModel = bundle.getParcelable(Constant.BOOK);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.center_toolbar);
        mToolbar.setTitle(R.string.detail);

        mBookImgVp = (ViewPager) mRootView.findViewById(R.id.vp_book_img);
        mPostionTv = (TextView) mRootView.findViewById(R.id.tv_position);

        mBookNeamTv = (TextView) mRootView.findViewById(R.id.tv_book_name);
        mBookIsbnTv = (TextView) mRootView.findViewById(R.id.tv_book_isbn);
        mBookAuthorTv = (TextView) mRootView.findViewById(R.id.tv_book_author);
        mBookPressTv = (TextView) mRootView.findViewById(R.id.tv_book_press);
        mBookSalesVolumeTv = (TextView) mRootView.findViewById(R.id.tv_book_sales_volume);
        mBookPriceTv = (TextView) mRootView.findViewById(R.id.tv_book_price);
        mBookCategoryTv = (TextView) mRootView.findViewById(R.id.tv_book_category);
        mBookPercentescribeTv = (TextView) mRootView.findViewById(R.id.tv_book_percent_describe);
        mBookStockTv = (TextView) mRootView.findViewById(R.id.tv_book_stock);

        mEditPriceTv = (TextView) mRootView.findViewById(R.id.tv_edit_price);
        mEditCategoryTv = (TextView) mRootView.findViewById(R.id.tv_edit_category);
        mEditPercentDescribeTv = (TextView) mRootView.findViewById(R.id.tv_edit_percent_describe);
        mEditStockTv = (TextView) mRootView.findViewById(R.id.tv_edit_stock);

        mBookIsSellBtn = (Button) mRootView.findViewById(R.id.btn_edit_is_sell);
        Click click = new Click();
        if (mBookModel != null) {
            setBookImgs(loadBookImgs(savedInstanceState, mBookModel.getBookImgs()));
            BookBean bookBean = mBookModel.getBook();

            mBookNeamTv.setText(bookBean.getBookName());
            mBookIsbnTv.setText(bookBean.getIsbn());
            mBookAuthorTv.setText(bookBean.getAuthor());
            mBookPressTv.setText(bookBean.getPress());
            mBookSalesVolumeTv.setText(String.valueOf(bookBean.getSalesVolume()));
            mBookPriceTv.setText(String.valueOf(bookBean.getPrice()));
            mBookCategoryTv.setText(bookBean.getCategory());
            mBookPercentescribeTv.setText(bookBean.getPercentDescribe());
            mBookStockTv.setText(String.valueOf(bookBean.getStock()));

            mBookIsSellBtn.setText(bookBean.isSell() ? R.string.shelves : R.string.added);

            mEditPriceTv.setOnClickListener(click);
            mEditCategoryTv.setOnClickListener(click);
            mEditPercentDescribeTv.setOnClickListener(click);
            mEditStockTv.setOnClickListener(click);
            mBookIsSellBtn.setOnClickListener(click);
        }

        mUpdatePresenter = new UpdatePresenter<>(this, updateHanler);
        mComment = mRootView.findViewById(R.id.comment);
        mComment.setOnClickListener(click);

    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_edit_price:
                    editBookPrice();
                    break;
                case R.id.tv_edit_category:
                    editCategory();
                    break;
                case R.id.tv_edit_percent_describe:
                    editPercentDescribe();
                    break;
                case R.id.tv_edit_stock:
                    editBookStock();
                    break;
                case R.id.btn_edit_is_sell:
                    editBookIsSell();
                    break;
                case R.id.comment:
                    gotoCommentList();
                    break;

            }
        }
    }

    CallBackHandler updateHanler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private void editBookPrice() {

        final String price = String.valueOf(mBookModel.getBook().getPrice());

        final EditText temp = new EditText(getActivity());
        temp.setText(price);
        temp.setInputType(android.text.InputType.TYPE_CLASS_NUMBER
                | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        temp.setFilters(filters);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改价格");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(temp);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (temp.getText().toString().length() > 0 && !temp.getText().toString().trim().equals(price)) {
                    BookBean bookBean = new BookBean();
                    bookBean.setPrice(Double.valueOf(temp.getText().toString().trim()));

                    BookBean book = mBookModel.getBook();
                    bookBean.setSell(book.isSell());
                    bookBean.setStock(book.getStock());
                    bookBean.setSalesVolume(book.getSalesVolume());

                    mUpdatePresenter.update(getActivity(), bookBean, mBookModel.getBook().getObjectId());
                    mBookPriceTv.setText(temp.getText().toString().trim());
                }
            }
        }).setNegativeButton("取消", null).show();
    }

    private void editCategory() {

        final String category = String.valueOf(mBookModel.getBook().getCategory());

        final EditText temp = new EditText(getActivity());
        temp.setText(category);
        InputFilter[] filters = {new InputFilter.LengthFilter(20)};
        temp.setFilters(filters);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改分类");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(temp);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (temp.getText().toString().length() > 0 && !temp.getText().toString().trim().equals(category)) {
                    BookBean bookBean = new BookBean();
                    bookBean.setCategory(temp.getText().toString().trim());

                    BookBean book = mBookModel.getBook();
                    bookBean.setPrice(book.getPrice());
                    bookBean.setSell(book.isSell());
                    bookBean.setStock(book.getStock());
                    bookBean.setSalesVolume(book.getSalesVolume());

                    mUpdatePresenter.update(getActivity(), bookBean, mBookModel.getBook().getObjectId());
                    mBookCategoryTv.setText(temp.getText().toString().trim());
                }
            }
        }).setNegativeButton("取消", null).show();
    }

    private void editPercentDescribe() {

        final String percentDescribe = String.valueOf(mBookModel.getBook().getPercentDescribe());

        final EditText temp = new EditText(getActivity());
        temp.setText(percentDescribe);
        InputFilter[] filters = {new InputFilter.LengthFilter(20)};
        temp.setFilters(filters);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改描述");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(temp);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (temp.getText().toString().length() > 0 && !temp.getText().toString().trim().equals(percentDescribe)) {
                    BookBean bookBean = new BookBean();
                    bookBean.setPercentDescribe(temp.getText().toString().trim());

                    BookBean book = mBookModel.getBook();
                    bookBean.setPrice(book.getPrice());
                    bookBean.setSell(book.isSell());
                    bookBean.setStock(book.getStock());
                    bookBean.setSalesVolume(book.getSalesVolume());

                    mUpdatePresenter.update(getActivity(), bookBean, mBookModel.getBook().getObjectId());
                    mBookPercentescribeTv.setText(temp.getText().toString().trim());
                }
            }
        }).setNegativeButton("取消", null).show();
    }

    private void editBookStock() {

        final String stock = String.valueOf(mBookModel.getBook().getStock());

        final EditText temp = new EditText(getActivity());
        temp.setText(stock);
        temp.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        temp.setFilters(filters);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改库存");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(temp);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (temp.getText().toString().length() > 0 && !temp.getText().toString().trim().equals(stock)) {
                    BookBean bookBean = new BookBean();
                    bookBean.setStock(Integer.valueOf(temp.getText().toString().trim()));

                    BookBean book = mBookModel.getBook();
                    bookBean.setPrice(book.getPrice());
                    bookBean.setSell(book.isSell());
                    bookBean.setSalesVolume(book.getSalesVolume());

                    mUpdatePresenter.update(getActivity(), bookBean, mBookModel.getBook().getObjectId());
                    mBookStockTv.setText(temp.getText().toString().trim());
                }
            }
        }).setNegativeButton("取消", null).show();
    }

    private void editBookIsSell() {
        String isSell = mBookModel.getBook().isSell() ? getResources().getString(R.string.shelves) : getResources().getString(R.string.added);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(isSell);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                BookBean bookBean = new BookBean();
                bookBean.setSell(!mBookModel.getBook().isSell());

                BookBean book = mBookModel.getBook();
                bookBean.setPrice(book.getPrice());
                bookBean.setSalesVolume(book.getSalesVolume());
                bookBean.setStock(book.getStock());

                mUpdatePresenter.update(getActivity(), bookBean, mBookModel.getBook().getObjectId());
                mBookIsSellBtn.setText(mBookModel.getBook().isSell() ? R.string.added : R.string.shelves);
            }
        }).setNegativeButton("取消", null).show();
    }


    public void setBookImgs(final List<View> imgs) {
        MViewPagerAdapter adapter = new MViewPagerAdapter(imgs, mBookImgVp);
        mBookImgVp.setAdapter(adapter);
        mBookImgVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBookImgVp.setCurrentItem(position);
                mPostionTv.setText(position + 1 + "/" + imgs.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public List<View> loadBookImgs(Bundle savedInstanceState, List<BookImgsBean> imgs) {
        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        List<View> views = new ArrayList<>(3);

        if (imgs != null) {
            for (int i = 0, length = imgs.size(); i < length; i++) {
                SimpleDraweeView img = (SimpleDraweeView) inflater.inflate(R.layout.item_book_detail_img, null);
                Uri uri = Uri.parse(imgs.get(i).getImg());
                img.setImageURI(uri);
                views.add(img);
            }
        }

        for (int i = 0, length = views.size(); i < 3 - length; i++) {
            SimpleDraweeView img = (SimpleDraweeView) inflater.inflate(R.layout.item_book_detail_img, null);
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook_shop/" + R.mipmap.upload_img);
            img.setImageURI(uri);
            views.add(img);
        }
        return views;
    }

    private void gotoCommentList() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(CommentListFragment.TAG);
        if (fragment == null) {
            fragment = new CommentListFragment();
        }

        BookBean book = mBookModel.getBook();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA, book);

        fragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(BookDetailFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, fragment, R.id.main_container, mFragmentManager, CommentListFragment.TAG);
    }
}
