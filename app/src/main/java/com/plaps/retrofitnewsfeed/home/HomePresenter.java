package com.plaps.retrofitnewsfeed.home;

import android.support.v4.widget.SwipeRefreshLayout;

import com.plaps.retrofitnewsfeed.models.RssFeed;
import com.plaps.retrofitnewsfeed.networking.NetworkError;
import com.plaps.retrofitnewsfeed.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ennur on 6/25/16.
 */
public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getRssList(final SwipeRefreshLayout swipeContainer) {
        view.showWait();

        Subscription subscription = service.getRssFeed(new Service.GetRssListCallback() {
            @Override
            public void onSuccess(RssFeed rssListResponse) {
                view.removeWait();
                view.getRssListSuccess(rssListResponse,swipeContainer);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
