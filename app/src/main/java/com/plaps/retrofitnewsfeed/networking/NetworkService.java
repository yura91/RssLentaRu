package com.plaps.retrofitnewsfeed.networking;


import com.plaps.retrofitnewsfeed.models.RssFeed;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET
    Observable<RssFeed> profilePicture(@Url String url);

}
