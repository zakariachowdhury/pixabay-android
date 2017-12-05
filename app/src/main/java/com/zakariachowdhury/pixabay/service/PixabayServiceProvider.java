package com.zakariachowdhury.pixabay.service;

import com.zakariachowdhury.pixabay.event.ErrorEvent;
import com.zakariachowdhury.pixabay.event.EventManager;
import com.zakariachowdhury.pixabay.ui.MainActivity;
import com.zakariachowdhury.pixabay.model.PixabayResponse;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zakariachowdhury.pixabay.util.Constatns.Pixabay.PIXABAY_BASE_URL;

/**
 * Created by Zakaria Chowdhury on 6/26/17.
 */

public class PixabayServiceProvider {
    private static final String TAG = MainActivity.class.getSimpleName();

    private PixabayService pixabayService;
    private EventManager eventManager;

    private PixabayServiceProvider() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PIXABAY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        pixabayService = retrofit.create(PixabayService.class);
        this.eventManager = EventManager.getInstance();
    }

    private static class Singleton {
        private static final PixabayServiceProvider INSTANCE = new PixabayServiceProvider();
    }

    public static PixabayServiceProvider getInstance() {
        return Singleton.INSTANCE;
    }

    public void imageSearch(String keywords) {
        Call<PixabayResponse> call = pixabayService.imageSearch(keywords);
        call.enqueue(imageSearchCallback);
    }

    public void editorsChoice() {
        Call<PixabayResponse> call = pixabayService.editorsChoice();
        call.enqueue(imageSearchCallback);
    }

    private Callback imageSearchCallback = new Callback<PixabayResponse>() {
        @Override
        public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
            int statusCode = response.code();
            if(statusCode == 200) {
                PixabayResponse pixabayResponse = response.body();
                eventManager.postImageSearchResult(pixabayResponse);
            }
            else {
                try {
                    eventManager.postErrorEvent(new ErrorEvent(response.errorBody().string()));
                } catch (IOException e) {
                    eventManager.postErrorEvent(new ErrorEvent(e.getMessage()));
                }
            }
        }

        @Override
        public void onFailure(Call<PixabayResponse> call, Throwable t) {
            eventManager.postErrorEvent(new ErrorEvent("Unable to perform image search"));
        }
    };
}
