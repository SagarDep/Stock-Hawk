package com.sam_chordas.android.stockhawk.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.model.NewsModel;
import com.sam_chordas.android.stockhawk.ui.fragments.NewsFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * {@link NewsAdapter} is used for adapting the list of NewsModel data to the
 * listView contained in {@link NewsFragment}
 */

public class NewsAdapter extends ArrayAdapter<NewsModel> {

    private List<NewsModel> newsModelList;
    private Context context;

    public NewsAdapter(Context context, List<NewsModel> newsModelList) {
        super(context, 0, newsModelList);
        this.newsModelList = newsModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null) {
            view = inflater.inflate(R.layout.news_single_list_item, parent, false);
        }

        // Initialize views
        TextView newsTitle = ButterKnife.findById(view, R.id.tv_news_title);
        TextView newsContent = ButterKnife.findById(view, R.id.tv_news_content);
        TextView newsPublisher = ButterKnife.findById(view, R.id.tv_publisher);
        TextView newsDate = ButterKnife.findById(view, R.id.tv_publish_date);

        newsTitle.setText(newsModelList.get(position).getTitle());
        newsTitle.setContentDescription(newsModelList.get(position).getTitle());
        newsTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        newsContent.setText(newsModelList.get(position).getContent());
        newsContent.setContentDescription(newsModelList.get(position).getContent());
        newsPublisher.setText(newsModelList.get(position).getPublisher());
        newsPublisher.setContentDescription(newsModelList.get(position).getPublisher());
        newsDate.setText(newsModelList.get(position).getDate());
        newsDate.setContentDescription(newsModelList.get(position).getDate());
        return view;
    }
}
