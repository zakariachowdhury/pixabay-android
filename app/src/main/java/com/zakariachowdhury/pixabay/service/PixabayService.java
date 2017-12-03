package com.zakariachowdhury.pixabay.service;

import com.zakariachowdhury.pixabay.model.ImageSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.zakariachowdhury.pixabay.Constatns.Pixabay.PIXABAY_API_PATH;

/**
 * Created by Zakaria Chowdhury on 6/26/17.
 */

public interface PixabayService {
    @GET(PIXABAY_API_PATH + "&image_type=photo")
    Call<ImageSearch> imageSearch( @Query("q") String keywords );
}
