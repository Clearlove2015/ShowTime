package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.video.videoplay.VideoPlayActivity;
import com.example.rumens.showtime.video.videoplay.VideoPlayerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */
@Module
public class VideoLivePlayerModule {
    private final VideoPlayActivity mView;
    private final LiveListItemBean mVideoLiveData;

    public VideoLivePlayerModule(VideoPlayActivity mView, LiveListItemBean mVideoLiveData) {
        this.mView = mView;
        this.mVideoLiveData = mVideoLiveData;
    }
    @PerActivity
    @Provides
    public IVideoPresenter providePresenter() {
        return new VideoPlayerPresenter(mView, mVideoLiveData);
    }
}
