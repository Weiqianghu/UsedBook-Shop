package com.weiqianghu.usedbook_shop.presenter.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.BookImgsBean;
import com.weiqianghu.usedbook_shop.model.entity.BookModel;
import com.weiqianghu.usedbook_shop.view.ViewHolderForRecyclerView;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/11.
 */
public class BookAdapter extends CommonAdapterForRecycleView implements View.OnClickListener {

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
            Log.d("bookImgs", bookImgs.get(0).getImg());
            Uri uri = Uri.parse(bookImgs.get(0).getImg());
            helper.setImageForSimpleDraweeViewUri(R.id.iv_book, uri);
        } else {
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook_shop/" + R.mipmap.upload_img);
            helper.setImageForSimpleDraweeViewUri(R.id.iv_book, uri);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
