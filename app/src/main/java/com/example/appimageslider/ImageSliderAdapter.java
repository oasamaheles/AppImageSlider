package com.example.appimageslider;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {
    private List<ImageModel> imageList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ImageSliderAdapter(Context context, List<ImageModel> imageList) {
        this.context = context;
        this.imageList = imageList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.slider_item, container, false);
        ImageView imageView = view.findViewById(R.id.slider_image);

        Glide.with(context)
                .load(imageList.get(position).getmUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        container.addView(view);

//        // Set click listener on the ImageView to launch image cropping activity
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Launch image cropping activity
//                CropImage.activity(Uri.parse(imageList.get(position).getmUrl()))
//                        .start(context, ImageSliderAdapter.this);
//            }
//        });
//        Uri imageUri = // your image URI here
//                CropImage.activity(imageUri)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(this);
//

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "item", Toast.LENGTH_SHORT).show();
            }
        });

//        Picasso.get().load(imageList.get(position).getImageUrl()).into(imageView);
//        container.addView(imageView);

//        Picasso.get().load(imageUrlList.get(position)).into(imageView);
//        container.addView(imageView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}