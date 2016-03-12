package com.weiqianghu.usedbook_shop.model.entity;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/12.
 */
public class BookModel {
    private BookBean book;
    private List<BookImgsBean> bookImgs;

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public List<BookImgsBean> getBookImgs() {
        return bookImgs;
    }

    public void setBookImgs(List<BookImgsBean> bookImgs) {
        this.bookImgs = bookImgs;
    }
}
