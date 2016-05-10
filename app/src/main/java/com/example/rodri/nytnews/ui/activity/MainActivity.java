package com.example.rodri.nytnews.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {

    private ListView articlesListView;
    private EditText etQuery;

    private LayoutInflater inflater;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeCustomComponents();
        this.setTitle(R.string.articles_title);
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
                Toast.makeText(MainActivity.this, "query: " + etQuery.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        builder.show();


    }

}
