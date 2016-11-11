package com.sam_chordas.android.stockhawk;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam_chordas.android.stockhawk.adapters.StockDetailsAdapter;
import com.sam_chordas.android.stockhawk.api.StocksApi;
import com.sam_chordas.android.stockhawk.model.CurrentStockModel;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * CurrentStockDetailsFragment provides more information on a selected stock
 */
public class CurrentStockDetailsFragment extends Fragment {


    public static CurrentStockDetailsFragment newInstance(String symbol){
        CurrentStockDetailsFragment detailsFragment = new CurrentStockDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.SYMBOL, symbol);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    public CurrentStockDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_stock, container, false);

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

        Call<List<CurrentStockModel>> call = service.getStockDetails(symbol);
        call.enqueue(new Callback<List<CurrentStockModel>>() {
            @Override
            public void onResponse(Call<List<CurrentStockModel>> call, Response<List<CurrentStockModel>> response) {

                List<CurrentStockModel> stockDetailsList = response.body();
                CurrentStockModel status = stockDetailsList.remove(0);

                if(status.getValue().equals("SUCCESS")) {
                    StockDetailsAdapter detailsAdapter = new StockDetailsAdapter(getContext(), stockDetailsList);
                    getActivity().setTitle(stockDetailsList.get(0).getValue());
                    ListView listView = ButterKnife.findById(view, R.id.non_scrollable_stock_details_list);
                    listView.setAdapter(detailsAdapter);

                }


            }

            @Override
            public void onFailure(Call<List<CurrentStockModel>> call, Throwable t) {

            }
        });

        return view;
    }

}
