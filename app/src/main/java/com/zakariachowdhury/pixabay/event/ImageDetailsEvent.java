package com.zakariachowdhury.pixabay.event;

import com.zakariachowdhury.pixabay.model.Image;

/**
 * Created by Zakaria Chowdhury on 12/4/17.
 */

public class ImageDetailsEvent {

    private final Image image;

    public ImageDetailsEvent(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
