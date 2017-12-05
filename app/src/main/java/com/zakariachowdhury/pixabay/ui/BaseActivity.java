package com.zakariachowdhury.pixabay.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zakariachowdhury.pixabay.R;
import com.zakariachowdhury.pixabay.event.EventManager;

import butterknife.ButterKnife;

/**
 * Created by Zakaria Chowdhury on 12/4/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventManager = EventManager.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventManager.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventManager.stop();
    }
}
