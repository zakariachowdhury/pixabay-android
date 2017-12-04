package com.zakariachowdhury.pixabay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zakariachowdhury.pixabay.adapter.ImageRecyclerViewAdapter;
import com.zakariachowdhury.pixabay.event.ErrorEvent;
import com.zakariachowdhury.pixabay.event.EventManager;
import com.zakariachowdhury.pixabay.model.Image;
import com.zakariachowdhury.pixabay.model.ImageSearch;
import com.zakariachowdhury.pixabay.service.PixabayServiceProvider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private PixabayServiceProvider pixabayServiceProvider;
    private EventManager eventManager;

    @BindView(R.id.recycler_view)
    RecyclerView imageRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventManager = EventManager.getInstance();
        pixabayServiceProvider = PixabayServiceProvider.getInstance();
        //pixabayServiceProvider.imageSearch("Red+Flowers");
        pixabayServiceProvider.editorsChoice();
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventManager.stop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageSearchResult(ImageSearch imageSearch) {
        if (imageSearch.getImages().size() > 0) {
            ImageRecyclerViewAdapter adapter = new ImageRecyclerViewAdapter(this, imageSearch.getImages());
            imageRecyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No images available", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}
