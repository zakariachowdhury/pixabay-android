package com.zakariachowdhury.pixabay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zakariachowdhury.pixabay.R;
import com.zakariachowdhury.pixabay.event.EventManager;
import com.zakariachowdhury.pixabay.event.ImageDetailsEvent;
import com.zakariachowdhury.pixabay.model.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zakaria Chowdhury on 12/3/17.
 */

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.CustomViewHolder> {
    private final EventManager eventManager;
    private List<Image> imageList;
    private Context context;

    public ImageRecyclerViewAdapter(Context context, EventManager eventManager) {
        this.context = context;
        this.imageList = new ArrayList<>();
        this.eventManager = eventManager;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Image image = imageList.get(position);
        if (image.getWebformatURL() != null) {
            Picasso.with(context).load(image.getWebformatURL())
                    .error(android.R.drawable.picture_frame)
                    .placeholder(null)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return imageList != null ? imageList.size() : 0;
    }

    public void clear() {
        if (imageList.size() > 0) {
            imageList.clear();
            notifyDataSetChanged();
        }
    }

    public void addAll(List<Image> imageList) {
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView imageView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Image image = imageList.get(position);
            ImageDetailsEvent imageDetailsEvent = new ImageDetailsEvent(image);
            eventManager.postImageDetailsEvent(imageDetailsEvent);
        }
    }
}
