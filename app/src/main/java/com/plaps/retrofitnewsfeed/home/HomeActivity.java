package com.plaps.retrofitnewsfeed.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.plaps.retrofitnewsfeed.BaseApp;
import com.plaps.retrofitnewsfeed.R;
import com.plaps.retrofitnewsfeed.models.RssFeed;
import com.plaps.retrofitnewsfeed.models.RssFeedItem;
import com.plaps.retrofitnewsfeed.networking.Service;

import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView list;
    HomePresenter presenter;
    @Inject
    public  Service service;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);


        renderView();
        init();

        presenter = new HomePresenter(service, this);
        presenter.getRssList(null);
    }

    public  void renderView(){
        setContentView(R.layout.activity_home);
        list = (RecyclerView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getRssList(swipeContainer);
            }
        });

    }

    public void init(){
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getRssListSuccess(RssFeed rssListResponse,SwipeRefreshLayout swipeContainer) {

        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), rssListResponse.getChannel().getItemList(),
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(RssFeedItem Item) {
                        Toast.makeText(getApplicationContext(), Item.getTitle(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        list.setAdapter(adapter);

        if(swipeContainer != null)
        {
            swipeContainer.setRefreshing(false);
        }

    }


}
