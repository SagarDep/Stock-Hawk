package com.sam_chordas.android.stockhawk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by galaxywizkid on 11/9/16.
 */

public interface MyApi {

    @GET("mservice.php")
    Call<List<NewsModel>> getNews(@Query("news") String symbol);
}
