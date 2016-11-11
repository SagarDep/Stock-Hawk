package com.sam_chordas.android.stockhawk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.model.CurrentStockModel;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by galaxywizkid on 11/10/16.
 */

public class StockDetailsAdapter extends ArrayAdapter<CurrentStockModel> {

    private List<CurrentStockModel> currentStockModelList;
    private Context context;


    public StockDetailsAdapter(Context context, List<CurrentStockModel> stockModelList) {
        super(context, 0, stockModelList);
        this.currentStockModelList = stockModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null) {
            view = inflater.inflate(R.layout.list_item_current_stock, parent, false);
        }

        TextView detailsTitle = ButterKnife.findById(view, R.id.tv_details_label);
        TextView detailsValue = ButterKnife.findById(view, R.id.tv_details_value);
        detailsTitle.setText(currentStockModelList.get(position).getLabel());


        detailsValue.setText(currentStockModelList.get(position).getValue());

        return view;
    }
}
