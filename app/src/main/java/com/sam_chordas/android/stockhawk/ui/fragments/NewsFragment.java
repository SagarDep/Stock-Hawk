package com.sam_chordas.android.stockhawk.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam_chordas.android.stockhawk.utils.Constants;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.adapters.NewsAdapter;
import com.sam_chordas.android.stockhawk.api.StocksApi;
import com.sam_chordas.android.stockhawk.model.NewsModel;

import java.util.List;

import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *  The NewsFragment fetches news data for each stock
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
    private ProgressDialog progress;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Inspired by rahulravindran0108
        String symbol = getArguments().getString(Constants.SYMBOL);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        // Retrofit library is used for making HTTP calls
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empyrean-aurora-455.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        StocksApi service = retrofit.create(StocksApi.class);

        loadProgressDialog();

        Call<List<NewsModel>> call = service.getNews(symbol);
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                List<NewsModel> newsList = response.body();
                progress.dismiss();

                NewsAdapter newsAdapter = new NewsAdapter(getContext(), newsList);
                ListView listView = ButterKnife.findById(view, R.id.news_list_view);
                listView.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                failedToGetDataToast();
                Log.d(LOG_TAG, t.toString());
            }
        });

        return view;
    }

    public void failedToGetDataToast(){
        Toast.makeText(getActivity(), R.string.failed_to_retrieve_data, Toast.LENGTH_SHORT).show();
    }

    private void loadProgressDialog(){
        progress = new ProgressDialog(getContext());
        progress.setTitle(getString(R.string.loading));
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }
}
