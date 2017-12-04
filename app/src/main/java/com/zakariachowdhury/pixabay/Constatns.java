package com.zakariachowdhury.pixabay;

/**
 * Created by Zakaria Chowdhury on 6/30/17.
 */

public final class Constatns {
    public static final class Pixabay {
        public static final String PIXABAY_BASE_URL = "https://pixabay.com/api/";
        public static final String PIXABAY_API_KEY = "3626573-eea19fdf59a1a709f863a16c2";
        public static final int RESULTS_PER_PAGE = 100;
        public static final String PIXABAY_API_PATH = "?key=" + PIXABAY_API_KEY + "&image_type=photo&per_page=" + RESULTS_PER_PAGE;
    }
}
