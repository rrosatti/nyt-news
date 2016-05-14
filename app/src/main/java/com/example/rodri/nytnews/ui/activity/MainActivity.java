package com.example.rodri.nytnews.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rodri.nytnews.R;
import com.example.rodri.nytnews.article.Article;
import com.example.rodri.nytnews.json.RemoteFetch;
import com.example.rodri.nytnews.ui.adapter.ArticleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private ListView articlesListView;
    private EditText etQuery;
    private ArticleAdapter adapter;

    private LayoutInflater inflater;
    private View view;

    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeCustomComponents();
        this.setTitle(R.string.articles_title);

        new ParseJsonAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        RelativeLayout relativeLayout = (RelativeLayout) menu.findItem(R.id.searchArticlesMenuItem).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.searchArticlesMenuItem) {
            showSearchArticleDialog();
        }

        return true;
    }

    public void initialize() {
        articlesListView = (ListView)findViewById(R.id.articlesListView);
        inflater = getLayoutInflater();
    }

    public void initializeCustomComponents() {
        view = inflater.inflate(R.layout.custom_search_article_dialog, null);

        etQuery = (EditText) view.findViewById(R.id.etQuery);
    }

    public void showSearchArticleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.query_dialog_message);

        if (view.getParent() == null) {
            builder.setView(view);
        } else {
            view = null;
            initializeCustomComponents();
            builder.setView(view);
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                query = etQuery.getText().toString();
                if (query.equals("")) {
                    Toast.makeText(MainActivity.this, "You must say something!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    verifyQuery();
                    new ParseJsonAsyncTask().execute();
                }
            }
        });

        builder.show();


    }

    public class ParseJsonAsyncTask extends AsyncTask<String, Integer, ArrayList<Article>> {

        JSONArray articleVenueArray;
        ArrayList<Article> articleList = new ArrayList<>();

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            try {

                RemoteFetch remoteFetch = new RemoteFetch();
                JSONObject json;

                if (query.equals("")) {
                    json = remoteFetch.getJSON("android");
                } else {
                    json = remoteFetch.getJSON(query);
                }

                articleVenueArray = json.getJSONObject("response").getJSONArray("docs");

                for (int i = 0; i < articleVenueArray.length(); i++) {
                    JSONObject data = articleVenueArray.getJSONObject(i);
                    Article article = new Article();

                    article.setTitle(data.optString("snippet"));
                    article.setUrl(data.optString("web_url"));

                    if (!data.optString("lead_paragraph").equals("null"))
                        article.setBody(data.optString("lead_paragraph"));
                    else
                        article.setBody("Description not found");

                    if (!data.optString("pub_date").equals("null"))
                        article.setDate(data.optString("pub_date"));
                    else
                        article.setDate("Date not found");


                    articleList.add(article);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return articleList;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            super.onPostExecute(articles);

            adapter = new ArticleAdapter(MainActivity.this, 0, articles);
            articlesListView.setAdapter(adapter);


        }
    }

    public void verifyQuery() {
        try {
            StringTokenizer tokenizer = new StringTokenizer(query, " ");
            String tmp = "";
            while (tokenizer.hasMoreElements()) {
                tmp += tokenizer.nextElement() + "+";
            }
            // remove the last '+' sign
            //tmp = tmp.replace(tmp.substring(tmp.length() - 1), "");
            System.out.println("look at -> " + tmp);
            query = tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
