package com.plaps.retrofitnewsfeed.deps;


import com.plaps.retrofitnewsfeed.home.HomeActivity;
import com.plaps.retrofitnewsfeed.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
