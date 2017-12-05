package com.zakariachowdhury.pixabay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zakariachowdhury.pixabay.R;
import com.zakariachowdhury.pixabay.adapter.ImageRecyclerViewAdapter;
import com.zakariachowdhury.pixabay.event.ErrorEvent;
import com.zakariachowdhury.pixabay.event.EventManager;
import com.zakariachowdhury.pixabay.event.ImageDetailsEvent;
import com.zakariachowdhury.pixabay.model.PixabayResponse;
import com.zakariachowdhury.pixabay.service.PixabayServiceProvider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private PixabayServiceProvider pixabayServiceProvider;
    private EventManager eventManager;
    private ImageRecyclerViewAdapter recyclerViewAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView imageRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        eventManager = EventManager.getInstance();
        pixabayServiceProvider = PixabayServiceProvider.getInstance();
        pixabayServiceProvider.editorsChoice();

        recyclerViewAdapter = new ImageRecyclerViewAdapter(this, eventManager);
        imageRecyclerView.setAdapter(recyclerViewAdapter);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pixabayServiceProvider.editorsChoice();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPixabayResponse(PixabayResponse response) {
        if (response.getImages().size() > 0) {
            recyclerViewAdapter.clear();
            recyclerViewAdapter.addAll(response.getImages());
            swipeRefreshLayout.setRefreshing(false);
        } else {
            Toast.makeText(this, "No images available", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageDetailsEvent(ImageDetailsEvent imageDetailsEvent) {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }
}
