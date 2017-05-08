package com.example.rumens.showtime.video.kankan;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideInBottomAdapter;
import com.example.rumens.showtime.adapter.VideoLocalListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.VideoListItemBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerVideoListComponent;
import com.example.rumens.showtime.inject.modules.VideoListModule;
import com.example.rumens.showtime.utils.MyAsyncQueryHandler;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */

public class VideoListFragment extends BaseFragment<IBasePresenter> implements IVideoListView<List<VideoListItemBean>> {
    private static final String VIDEO_TYPE = "videotype";
    @BindView(R.id.video_recyclerview)
    RecyclerView mVideoRecyclerview;
    @Inject
    BaseQuickAdapter mAdapter;
    @Nullable
    @BindView(R.id.video_listView)
    ListView mLocalVideoListView;
    private String mVideoType;
    private VideoLocalListAdapter adapter;

    @Override
    public void loadData(List<VideoListItemBean> videoListItemBeen) {

    }

    @Override
    public void loadLocalVideoData() {

    }

    @Override
    public void loadLocalVideoMoreData() {

    }

    @Override
    public void loadMoreData(List<VideoListItemBean> videoListItemBeen) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    protected int attachLayoutRes() {
        if (TextUtils.equals(mVideoType, "本地")) {
            return R.layout.fragment_layout_local_video;
        } else {
            return R.layout.fragment_video_type_list;
        }

    }

    @Override
    protected void initInjector() {
        if (TextUtils.equals(mVideoType, "本地")) {
            ContentResolver resolver = getContext().getContentResolver();
            MyAsyncQueryHandler queryHandler = new MyAsyncQueryHandler(resolver);
            adapter = new VideoLocalListAdapter(getContext(), null,false);
            mLocalVideoListView.setAdapter(adapter);
            queryHandler.startQuery(0,adapter, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    new String[]{io.vov.vitamio.provider.MediaStore.Video.Media._ID, io.vov.vitamio.provider.MediaStore.Video.Media.TITLE, io.vov.vitamio.provider.MediaStore.Video.Media.DURATION, io.vov.vitamio.provider.MediaStore.Video.Media.SIZE, io.vov.vitamio.provider.MediaStore.Video.Media.DATA},null,null,null);
        } else {
            DaggerVideoListComponent.builder()
                    .appComponent(getAppComponent())
                    .videoListModule(new VideoListModule(this, mVideoType))
                    .build()
                    .inject(this);
        }

    }

    @Override
    protected void initViews() {
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewG(mContext, mVideoRecyclerview, slideAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
    }

    @Override
    protected void updateViews() {
//        mPresenter.getData();
    }

    public static Fragment lunch(String videoType) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_TYPE, videoType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mVideoType = getArguments().getString(VIDEO_TYPE);
        super.onCreate(savedInstanceState);
    }

}
