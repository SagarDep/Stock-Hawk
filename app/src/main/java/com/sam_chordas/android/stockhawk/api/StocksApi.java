package com.sam_chordas.android.stockhawk.api;

import com.sam_chordas.android.stockhawk.model.CurrentStockModel;
import com.sam_chordas.android.stockhawk.model.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  This is the interface required for making HTTP calls using the Retrofit library
 */

public interface StocksApi {

    @GET("mservice.php")
    Call<List<NewsModel>> getNews(@Query("news") String symbol);

    @GET("mservice.php")
    Call<List<CurrentStockModel>> getStockDetails(@Query("symbol") String symbol);
}
