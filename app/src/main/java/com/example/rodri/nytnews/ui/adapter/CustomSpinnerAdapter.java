package com.example.rodri.nytnews.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.nytnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 5/16/2016.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private List<String> items;
    private LayoutInflater inflater = null;

    public CustomSpinnerAdapter(Activity activity, int textViewResourceId, List<String> items) {
        super (activity, textViewResourceId, items);
        try {
            this.activity = activity;
            this.items = items;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public class ViewHolder {
        public TextView displayItem;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.spinner_item, null);

            holder.displayItem = (TextView) v.findViewById(R.id.txtSpinnerItem);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayItem.setText(items.get(position));

        return v;
    }
}
