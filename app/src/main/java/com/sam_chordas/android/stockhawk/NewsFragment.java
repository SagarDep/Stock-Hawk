package com.sam_chordas.android.stockhawk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 */
public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(String symbol){
        NewsFragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.SYMBOL, symbol);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    public static final String LOG_TAG = NewsFragment.class.getSimpleName();

    public NewsFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Inspired by rahulravindran0108
        String symbol = getArguments().getString(Constants.SYMBOL);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empyrean-aurora-455.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MyApi service = retrofit.create(MyApi.class);

        Call<List<NewsModel>> call = service.getNews(symbol);
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                List<NewsModel> newsList = response.body();
                for(NewsModel news: newsList){
                    Log.v(LOG_TAG, news.getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                failedToGetDataToast();

            }
        });


        return view;
    }

    public void failedToGetDataToast(){
        Toast.makeText(getActivity(), "Failed to retrieve news", Toast.LENGTH_SHORT).show();
    }
}
