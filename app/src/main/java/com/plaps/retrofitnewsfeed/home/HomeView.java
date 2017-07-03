package com.plaps.retrofitnewsfeed.home;

import android.support.v4.widget.SwipeRefreshLayout;

import com.plaps.retrofitnewsfeed.models.RssFeed;

/**
 * Created by ennur on 6/25/16.
 */
public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getRssListSuccess(RssFeed rssResponse, SwipeRefreshLayout swipeContainer);

}
