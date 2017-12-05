package com.zakariachowdhury.pixabay.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zakariachowdhury.pixabay.R;
import com.zakariachowdhury.pixabay.event.EventManager;
import com.zakariachowdhury.pixabay.event.ImageDetailsEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ButterKnife.bind(this);
        eventManager = EventManager.getInstance();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onImageDetailsEvent(ImageDetailsEvent imageDetailsEvent) {
        Toast.makeText(this, imageDetailsEvent.getImage().getTags(), Toast.LENGTH_SHORT).show();
        eventManager.clearImageDetailsEvent();
    }
}
