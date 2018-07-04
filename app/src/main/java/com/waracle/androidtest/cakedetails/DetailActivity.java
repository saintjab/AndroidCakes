
package com.waracle.androidtest.cakedetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.waracle.androidtest.R;
import com.waracle.androidtest.image.ImageLoader;

/**
 * Displays cake details screen.
 */
public class DetailActivity extends AppCompatActivity implements DetailContract.View{

    private TextView mDetailTitle;
    private TextView mDetailDescription;
    private ImageView mDetailImage;
    private ProgressBar progressBar;
    private ImageLoader imageLoader;

    DetailPresenter presenter;

    public static final String Cake_Title = "Title";
    public static final String Cake_Desc = "Desc";
    public static final String Image_URL = "Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailTitle = (TextView) findViewById(R.id.title);
        mDetailDescription = (TextView) findViewById(R.id.description);
        mDetailImage = (ImageView) findViewById(R.id.big_image);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        imageLoader = new ImageLoader(this);

        String title = getIntent().getStringExtra(Cake_Title);
        String desc = getIntent().getStringExtra(Cake_Desc);
        String imageURL = getIntent().getStringExtra(Image_URL);

        loadImage(imageURL);

        presenter = new DetailPresenter(this);
        presenter.showCakeDetails(title, desc);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        mDetailDescription.setVisibility(View.VISIBLE);
        mDetailDescription.setText(description);
    }

    @Override
    public void showTitle(String title) {
      mDetailTitle.setVisibility(View.VISIBLE);
      mDetailTitle.setText(title);
    }

    @Override
    public void loadImage(String imageURL){
        setProgressIndicator(true);
        imageLoader.DisplayImage(imageURL, mDetailImage);
        setProgressIndicator(false);
    }
}
