package com.example.rumens.showtime.reader.bookclassify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ClassifyListAdapter;
import com.example.rumens.showtime.api.bean.CategoryList;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerBookClassifyListComponent;
import com.example.rumens.showtime.inject.modules.BookClassifyListModule;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */

public class BookClassifyListFragment extends BaseFragment<IBasePresenter> implements IBookClassifyBaseView {
    private static final String BOOK_CLASSIFY_LIST = "bookclassifylist";
    @BindView(R.id.rv_checked_list)
    RecyclerView mRvMaleList;
    @BindView(R.id.rv_unchecked_list)
    RecyclerView mRvFemaleList;
    private String mBookType;
    @Inject
    ClassifyListAdapter adapter;

    @Override
    public void LoadCategoryList(CategoryList data) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_book_classify;
    }

    @Override
    protected void initInjector() {
        DaggerBookClassifyListComponent.builder()
                .appComponent(getAppComponent())
                .bookClassifyListModule(new BookClassifyListModule(this, mBookType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }

    public static Fragment lunch(String mBookType) {
        BookClassifyListFragment fragment = new BookClassifyListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_CLASSIFY_LIST, mBookType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mBookType = getArguments().getString(BOOK_CLASSIFY_LIST);
        super.onCreate(savedInstanceState);
    }
}
