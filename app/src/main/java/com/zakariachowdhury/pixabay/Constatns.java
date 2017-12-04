package com.zakariachowdhury.pixabay;

/**
 * Created by Zakaria Chowdhury on 6/30/17.
 */

public final class Constatns {
    public static final class Pixabay {
        public static final String PIXABAY_BASE_URL = "https://pixabay.com/api/";
        private static final String PIXABAY_API_KEY = "3626573-eea19fdf59a1a709f863a16c2";
        private static final int RESULTS_PER_PAGE = 200;

        public static final String PIXABAY_API_PATH = "?image_type=photo"
                + "&key=" + PIXABAY_API_KEY
                + "&per_page=" + RESULTS_PER_PAGE;
    }
}
