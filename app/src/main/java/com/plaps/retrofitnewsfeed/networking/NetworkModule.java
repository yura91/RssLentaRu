package com.plaps.retrofitnewsfeed.networking;

import com.plaps.retrofitnewsfeed.AdeptAndroid;
import com.plaps.retrofitnewsfeed.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.*;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.*;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by ennur on 6/28/16.
 */
@Module
public class NetworkModule {
    File cacheFile;
    public NetworkModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }
    private static final String CACHE_CONTROL = "Cache-Control";

    @Provides
    @Singleton
    Retrofit provideCall() {

        SimpleXmlConverterFactory conv = SimpleXmlConverterFactory.createNonStrict();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(provideOkHttpClient())
                .addConverterFactory(conv)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    private static OkHttpClient provideOkHttpClient ()
    {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .addNetworkInterceptor( provideCacheInterceptor() )
                .cache( provideCache() )
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
             Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }

    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( AdeptAndroid.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public static Interceptor provideCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();

                if ( !AdeptAndroid.hasNetwork() )
                {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }
}
