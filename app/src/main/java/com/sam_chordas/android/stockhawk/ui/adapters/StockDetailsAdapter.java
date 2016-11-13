package com.sam_chordas.android.stockhawk.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.model.CurrentStockModel;

import java.text.DecimalFormat;
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

        if (view == null) {
            view = inflater.inflate(R.layout.current_stock_list_item, parent, false);
        }

        TextView detailsTitle = ButterKnife.findById(view, R.id.tv_details_label);
        TextView detailsValue = ButterKnife.findById(view, R.id.tv_details_value);
        detailsTitle.setText(currentStockModelList.get(position).getLabel());
        detailsTitle.setContentDescription(currentStockModelList.get(position).getLabel());

        if (!(currentStockModelList.get(position).getLabel().equalsIgnoreCase("change") ||
                currentStockModelList.get(position).getLabel().equalsIgnoreCase("changeytd"))) {
            detailsValue.setText(currentStockModelList.get(position).getValue());
            detailsValue.setContentDescription(currentStockModelList.get(position).getValue());

        } else {
            String[] changeArr = currentStockModelList.get(position).getValue().split(" ");
            DecimalFormat decimalFormat = new DecimalFormat("###.##");

            double change = Double.parseDouble(changeArr[0]);
            double changePercent = Double.parseDouble(changeArr[1]);

            // Proper formatting for the values of the "change" label as well and up/down arrows
            if (changePercent > 0) {
                SpannableStringBuilder ssb = new SpannableStringBuilder(
                        decimalFormat.format(change) + "(+" +
                                decimalFormat.format(changePercent) + "%) ");
                Bitmap arrow = BitmapFactory.decodeResource(view.getResources(), R.drawable.up);
                ssb.setSpan(new ImageSpan(view.getContext(), arrow), ssb.length() - 1,
                        ssb.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                detailsValue.setText(ssb, TextView.BufferType.SPANNABLE);
                detailsValue.setContentDescription(ssb.toString());

            } else {
                SpannableStringBuilder ssb = new SpannableStringBuilder(
                        decimalFormat.format(change) + "(" +
                                decimalFormat.format(changePercent) + "%) ");
                Bitmap arrow = BitmapFactory.decodeResource(view.getResources(), R.drawable.down);
                ssb.setSpan(new ImageSpan(view.getContext(), arrow), ssb.length() - 1,
                        ssb.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                detailsValue.setText(ssb, TextView.BufferType.SPANNABLE);
                detailsValue.setContentDescription(ssb.toString());
            }

        }

        return view;
    }
}
