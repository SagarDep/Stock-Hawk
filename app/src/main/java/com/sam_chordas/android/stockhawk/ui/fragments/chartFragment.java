package com.sam_chordas.android.stockhawk.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.utils.Constants;

import butterknife.ButterKnife;


/**
 * A fragment for displaying the stock chart
 */
public class ChartFragment extends Fragment {

    public static ChartFragment newInstance(String symbol) {
        ChartFragment chartFragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(Constants.SYMBOL, symbol);
        chartFragment.setArguments(args);
        return chartFragment;
    }

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String symbol = getArguments().getString(Constants.SYMBOL);
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

                    //(WebView) view.findViewById(R.id.webView)
        WebView webView = ButterKnife.findById(view, R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://empyrean-aurora-455.appspot.com/charts.php?symbol=" + symbol);
        return view;
    }

}
