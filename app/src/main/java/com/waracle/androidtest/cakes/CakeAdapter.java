package com.waracle.androidtest.cakes;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.Cake;
import com.waracle.androidtest.R;
import com.waracle.androidtest.image.ImageLoader;

import java.util.List;

/**
 * Created by Jonas Boateng on 04/07/2018.
 */

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder>{

    private List<Cake> cakeItems;
    private ImageLoader imageLoader;
    private Activity activity;

    private CakeItemListener mItemListener;

    public CakeAdapter(Activity activity, List<Cake> cakeItems, CakeItemListener itemListener) {
        this.activity = activity;
        this.cakeItems = cakeItems;
        mItemListener = itemListener;
        imageLoader = new ImageLoader(activity);
    }

    @Override
    public int getItemCount() {
        return cakeItems.size();
    }

    public void clear(){
        cakeItems.clear();
    }

    private Cake getItem(int position){
        return cakeItems.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);

        return new ViewHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.titleTextView.setText(cakeItems.get(position).getTitle());
        viewHolder.descTextView.setText(cakeItems.get(position).getDetails());
        imageLoader.DisplayImage(cakeItems.get(position).getImageURL(), viewHolder.imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CakeItemListener mItemListener;
        TextView titleTextView;
        TextView descTextView;
        CardView container;
        ImageView imageView;

        private ViewHolder(View root, CakeItemListener listener) {
            super(root);

            mItemListener = listener;
            container = (CardView) root.findViewById(R.id.container);
            titleTextView = (TextView) root.findViewById(R.id.title);
            descTextView = (TextView) root.findViewById(R.id.desc);
            imageView = (ImageView) root.findViewById(R.id.image);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Cake cake = getItem(position);
                    mItemListener.onCakeClick(cake);
                }
            });
        }
    }
}
