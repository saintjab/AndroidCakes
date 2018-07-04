package com.waracle.androidtest.cakes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.waracle.androidtest.Cake;
import com.waracle.androidtest.R;
import com.waracle.androidtest.cakedetails.DetailActivity;

import java.util.List;

public class CakesActivity extends AppCompatActivity implements CakesContract.View {
    private RecyclerView mListView;
    private CakeAdapter mAdapter;
    private ProgressBar progressBar;

    private CakesContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setIndeterminate(true);

        mListView = (RecyclerView) findViewById(R.id.list);
        mListView.setHasFixedSize(true);
        final LinearLayoutManager horizontalManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListView.setLayoutManager(horizontalManager);

        mActionsListener = new CakesPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    CakeItemListener mItemListener = new CakeItemListener() {
        @Override
        public void onCakeClick(Cake clickedCake) {
            mActionsListener.showCakeDetails(clickedCake);
        }
    };

    @Override
    public void setProgressIndicator(final boolean active) {
        if (active)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCakes(List<Cake> cakes) {
        mAdapter = new CakeAdapter(this, cakes, mItemListener);
        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);

        setProgressIndicator(false);
    }

    @Override
    public void showCakeDetailUi(Cake cake) {
        Intent intent = new Intent(CakesActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.Cake_Title, cake.getTitle());
        intent.putExtra(DetailActivity.Cake_Desc, cake.getDetails());
        intent.putExtra(DetailActivity.Image_URL, cake.getImageURL());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mAdapter.clear();
            mActionsListener.reloadCakes(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

