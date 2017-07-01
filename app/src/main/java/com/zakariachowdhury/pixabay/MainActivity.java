package com.zakariachowdhury.pixabay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zakariachowdhury.pixabay.model.ImageSearch;
import com.zakariachowdhury.pixabay.service.PixabayServiceProvider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private PixabayServiceProvider pixabayServiceProvider;
    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventManager = EventManager.getInstance();
        pixabayServiceProvider = PixabayServiceProvider.getInstance();
        pixabayServiceProvider.imageSearch("Yellow+Flowers");
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
        Log.v(TAG, "Total Hits = " + String.valueOf(imageSearch.getTotal()));
    }
}
