package com.zakariachowdhury.pixabay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
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

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        Toast.makeText(this, "Total Images = " + String.valueOf(imageSearch.getImages().size()), Toast.LENGTH_SHORT).show();
        if (imageSearch.getImages().size() > 0) {
            List<Image> images = imageSearch.getImages();
            Picasso.with(this).load(images.get(0).getWebformatURL()).into(imageView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
