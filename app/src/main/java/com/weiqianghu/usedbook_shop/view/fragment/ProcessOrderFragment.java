package com.weiqianghu.usedbook_shop.view.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.AddressBean;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.BookImgsBean;
import com.weiqianghu.usedbook_shop.model.entity.OrderBean;
import com.weiqianghu.usedbook_shop.model.entity.OrderModel;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.presenter.QueryAddressPresenter;
import com.weiqianghu.usedbook_shop.presenter.QueryUserPresenter;
import com.weiqianghu.usedbook_shop.presenter.UpdatePresenter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.util.OrderStateUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.view.IUpdateView;

import java.util.List;


public class ProcessOrderFragment extends BaseFragment implements IUpdateView {
    public static final String TAG = ProcessOrderFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private OrderModel mOrderModel = new OrderModel();
    private OrderBean mOrderBean = new OrderBean();

    private SimpleDraweeView mBookImgIv;
    private TextView mBookNameTv;
    private TextView mBookAuthorTv;
    private TextView mPercentDescribeTv;
    private TextView mOrderStateTv;
    private TextView mBookPriceTv;
    private TextView mAmountTv;

    private TextView mUserNameTv;
    private TextView mUserSexTv;
    private TextView mUserMobileTv;

    private TextView mAddressNameTv;
    private TextView mAddressMobileTv;
    private TextView mAddressTv;

    private Button mSubmitBtn;

    private QueryUserPresenter mQueryUserPresenter;
    private QueryAddressPresenter mQueryAddressPresenter;
    private UpdatePresenter<OrderBean> mUpdatePresenter;

    private TextView mTimeTv;

    private boolean isGoneBtn = false;

    @Override
    protected int getLayoutId() {
        Fresco.initialize(getActivity());
        return R.layout.fragment_process_order;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initView(savedInstanceState);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mOrderModel = bundle.getParcelable(Constant.DATA);
            mOrderBean = mOrderModel.getOrderBean();
            isGoneBtn = bundle.getBoolean(Constant.IS_BTN_GONE);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.center_toolbar);
        mToolbar.setTitle(R.string.detail);

        mBookImgIv = (SimpleDraweeView) mRootView.findViewById(R.id.iv_book_img);
        mBookNameTv = (TextView) mRootView.findViewById(R.id.tv_book_name);
        mBookAuthorTv = (TextView) mRootView.findViewById(R.id.tv_book_author);
        mPercentDescribeTv = (TextView) mRootView.findViewById(R.id.tv_percent_describe);
        mOrderStateTv = (TextView) mRootView.findViewById(R.id.tv_order_state);
        mBookPriceTv = (TextView) mRootView.findViewById(R.id.tv_price);
        mAmountTv = (TextView) mRootView.findViewById(R.id.tv_amount);

        BookBean book = mOrderBean.getBook();
        List<BookImgsBean> bookImgs = mOrderModel.getBookImgs();

        if (bookImgs != null && bookImgs.size() > 0) {
            Uri uri = Uri.parse(bookImgs.get(0).getImg());
            mBookImgIv.setImageURI(uri);
        } else {
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook/" + R.mipmap.upload_img);
            mBookImgIv.setImageURI(uri);
        }
        if (book != null) {
            mBookNameTv.setText(book.getBookName());
            mBookAuthorTv.setText(book.getAuthor());
            mBookPriceTv.setText(book.getPercentDescribe());
            mOrderStateTv.setText(OrderStateUtil.getStrByOrderState(mOrderBean.getOrderState()));
            mBookPriceTv.setText(String.valueOf(book.getPrice() + "￥"));
            mAmountTv.setText(String.valueOf("X" + mOrderBean.getAmount()));
        }

        mUserNameTv = (TextView) mRootView.findViewById(R.id.tv_user_name);
        mUserSexTv = (TextView) mRootView.findViewById(R.id.tv_user_sex);
        mUserMobileTv = (TextView) mRootView.findViewById(R.id.tv_user_mobile_no);

        mAddressNameTv = (TextView) mRootView.findViewById(R.id.tv_name);
        mAddressMobileTv = (TextView) mRootView.findViewById(R.id.tv_mobile_no);
        mAddressTv = (TextView) mRootView.findViewById(R.id.tv_address);

        mQueryUserPresenter = new QueryUserPresenter(queryUserHandler);
        mQueryAddressPresenter = new QueryAddressPresenter(queryAddressHandler);

        mQueryUserPresenter.QueryUser(getActivity(), mOrderBean.getUser().getObjectId());
        mQueryAddressPresenter.QueryAddress(getActivity(), mOrderBean.getAddress().getObjectId());
        mUpdatePresenter = new UpdatePresenter<OrderBean>(this, updateOrderHandler);

        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(new Click());
        if (isGoneBtn) {
            mSubmitBtn.setVisibility(View.GONE);
        }

        mTimeTv = (TextView) mRootView.findViewById(R.id.tv_time);
        mTimeTv.setText(mOrderBean.getCreatedAt().substring(0, 10));
    }

    CallBackHandler queryUserHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    UserBean user = new UserBean();
                    if (bundle != null) {
                        user = bundle.getParcelable(Constant.DATA);
                    }
                    mUserNameTv.setText(user.getUsername());
                    mUserSexTv.setText(user.getSexStr());
                    mUserMobileTv.setText(user.getMobilePhoneNumber());
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    CallBackHandler queryAddressHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    AddressBean address = bundle.getParcelable(Constant.DATA);

                    mAddressNameTv.setText(address.getName());
                    mAddressMobileTv.setText(address.getMobileNo());
                    String addressStr = new StringBuffer().append(address.getProvince()).
                            append(address.getCity()).
                            append(address.getCounty()).
                            append(address.getDetailAddress()).
                            toString();
                    mAddressTv.setText(addressStr);
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    handleOrder();
                    break;
            }
        }
    }

    private void handleOrder() {
        mOrderBean.setOrderState(Constant.EXPRESS);
        mUpdatePresenter.update(getActivity(), mOrderBean, mOrderBean.getObjectId());
    }

    CallBackHandler updateOrderHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Toast.makeText(getActivity(), "订单处理成功", Toast.LENGTH_SHORT).show();
                    mSubmitBtn.setClickable(false);
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

}
