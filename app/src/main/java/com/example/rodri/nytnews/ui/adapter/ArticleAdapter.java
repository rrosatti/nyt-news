package com.example.rodri.nytnews.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.nytnews.R;
import com.example.rodri.nytnews.article.Article;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rodri on 5/12/2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {


    private ArrayList<Article> articles;
    private Activity activity;
    private LayoutInflater inflater = null;

    public ArticleAdapter(Activity activity, int textViewResourceId, ArrayList<Article> articles) {
        super (activity, textViewResourceId, articles);
        try {
            this.activity = activity;
            this.articles = articles;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    public class ViewHolder {
        public TextView displayBody;
        public TextView displayTitle;
        public TextView displayUrl;
        public TextView displayDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_article_item, null);

            holder.displayTitle = (TextView) v.findViewById(R.id.txtTitle);
            holder.displayBody = (TextView) v.findViewById(R.id.txtBody);
            holder.displayDate = (TextView) v.findViewById(R.id.txtDate);
            holder.displayUrl = (TextView) v.findViewById(R.id.txtUrl);

            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        Article article = articles.get(position);
        holder.displayBody.setText(article.getBody());
        holder.displayTitle.setText(article.getTitle());
        holder.displayUrl.setText(article.getUrl());

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse(article.getDate());
            System.out.println("before: " + article.getDate());
            String date = sdf.format(d);
            System.out.println("after: " + date);
            holder.displayDate.setText(date);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            if (article.getDate() != null) {
                //System.out.println("Fuck this date -> " + article.getDate());
                Date date = format.parse(article.getDate());
                holder.displayDate.setText(date.getDay() + 1 + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900));
            } else {
                holder.displayDate.setText("no date found");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return v;
    }

}
