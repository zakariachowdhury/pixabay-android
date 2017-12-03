package com.zakariachowdhury.pixabay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zakariachowdhury.pixabay.event.ErrorEvent;
import com.zakariachowdhury.pixabay.event.EventManager;
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
        pixabayServiceProvider.imageSearch("Red+Flowers");
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
        Toast.makeText(this, "Total Hits = " + String.valueOf(imageSearch.getTotal()), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
