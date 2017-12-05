package com.zakariachowdhury.pixabay.ui;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zakariachowdhury.pixabay.R;
import com.zakariachowdhury.pixabay.event.EventManager;
import com.zakariachowdhury.pixabay.event.ImageDetailsEvent;
import com.zakariachowdhury.pixabay.model.Image;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    @BindView(R.id.imageView)
    public ImageView imageView;

    private EventManager eventManager;
    private Image image;
    private WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ButterKnife.bind(this);
        eventManager = EventManager.getInstance();
        wallpaperManager = WallpaperManager.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_set_wallpaper:
                onClickSetWallpaperMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onImageDetailsEvent(ImageDetailsEvent imageDetailsEvent) {
        eventManager.clearImageDetailsEvent();
        this.image = imageDetailsEvent.getImage();
        Picasso.with(this).load(image.getWebformatURL()).into(imageView);
    }

    private void onClickSetWallpaperMenu() {
        if (image != null) {
            Picasso.with(this).load(image.getWebformatURL()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        wallpaperManager.setBitmap(bitmap);
                        displaySetWallpaperSuccess();
                    } catch (IOException e) {
                        displaySetWallpaperError();
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    displaySetWallpaperError();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
    }

    private void displaySetWallpaperError() {
        Toast.makeText(this, "Unable to set wallpaper.", Toast.LENGTH_SHORT).show();
    }

    private void displaySetWallpaperSuccess() {
        Toast.makeText(this, "Wallpaper has been changed.", Toast.LENGTH_SHORT).show();
    }
}
