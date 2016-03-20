package com.weiqianghu.usedbook_shop.presenter.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.BookImgsBean;
import com.weiqianghu.usedbook_shop.model.entity.BookModel;
import com.weiqianghu.usedbook_shop.view.ViewHolderForRecyclerView;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/11.
 */
public class BookAdapter extends CommonAdapterForRecycleView {

    public BookAdapter(List datas, int itemLayoutId) {
        super(datas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolderForRecyclerView helper, Object item) {
        BookModel bookModel = (BookModel) item;
        BookBean book = bookModel.getBook();
        List<BookImgsBean> bookImgs = bookModel.getBookImgs();

        helper.setText(R.id.tv_book_name, book.getBookName());
        helper.setText(R.id.tv_book_price, String.valueOf(book.getPrice()));
        helper.setText(R.id.tv_percent_describe, book.getPercentDescribe());
        helper.setText(R.id.tv_book_stock, String.valueOf(book.getStock()));
        helper.setText(R.id.tv_sales_volume, String.valueOf(book.getSalesVolume()));

        if (bookImgs.size() > 0) {
            Uri uri = Uri.parse(bookImgs.get(0).getImg());
            helper.setImageForSimpleDraweeViewUri(R.id.iv_book, uri);
        } else {
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook_shop/" + R.mipmap.upload_img);
            helper.setImageForSimpleDraweeViewUri(R.id.iv_book, uri);
        }
    }

    /*@Override
    public ViewHolderForRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == super.FOOTER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
            view.setOnClickListener(this);
        }
        return ViewHolderForRecyclerView.get(view);
    }*/
}
