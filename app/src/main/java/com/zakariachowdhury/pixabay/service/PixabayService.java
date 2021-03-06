package com.zakariachowdhury.pixabay.service;

import com.zakariachowdhury.pixabay.model.PixabayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.zakariachowdhury.pixabay.util.Constatns.Pixabay.PIXABAY_API_PATH;

/**
 * Created by Zakaria Chowdhury on 6/26/17.
 */

public interface PixabayService {
    @GET(PIXABAY_API_PATH)
    Call<PixabayResponse> imageSearch(@Query("q") String keywords );

    @GET(PIXABAY_API_PATH + "&editors_choice=true")
    Call<PixabayResponse> editorsChoice();
}
