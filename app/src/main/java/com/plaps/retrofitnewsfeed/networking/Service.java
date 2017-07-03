package com.plaps.retrofitnewsfeed.networking;


import com.plaps.retrofitnewsfeed.BuildConfig;
import com.plaps.retrofitnewsfeed.models.RssFeed;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getRssFeed(final GetRssListCallback callback) {

        return networkService.profilePicture(BuildConfig.BASEURL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends RssFeed>>() {
                    @Override
                    public Observable<? extends RssFeed> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<RssFeed>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(RssFeed rssListResponse) {
                        callback.onSuccess(rssListResponse);

                    }
                });
    }

    public interface GetRssListCallback{
        void onSuccess(RssFeed rssListResponse);

        void onError(NetworkError networkError);
    }
}
