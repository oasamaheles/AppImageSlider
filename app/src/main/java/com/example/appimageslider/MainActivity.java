package com.example.appimageslider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private int currentPosition;
    private final List<ImageModel> mImageList = new ArrayList<>();
    private final Handler mHandler = new Handler();
    private int mPosition = 0;

    private GestureDetectorCompat gestureDetectorCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        indicatorLayout = findViewById(R.id.indicator_layout);


//        // Initialize the image list with some sample images
//        mImageList.add("https://thumbs.dreamstime.com/b/tree-field-orange-sky-14335903.jpg");
//        mImageList.add("https://thumbs.dreamstime.com/b/tree-field-orange-sky-14335903.jpg");
//        mImageList.add("https://thumbs.dreamstime.com/b/tree-field-orange-sky-14335903.jpg");
//        mImageList.add("https://thumbs.dreamstime.com/b/tree-field-orange-sky-14335903.jpg");

        mImageList.add(new ImageModel("https://thumbs.dreamstime.com/b/tree-field-orange-sky-14335903.jpg"));
        mImageList.add(new ImageModel("https://avatars.hsoubcdn.com/91b0651dfc6368928100501dabbcbb96?s=256.png"));
        mImageList.add(new ImageModel("https://1.bp.blogspot.com/-mmTfFtZxM88/XmBRwyRx-6I/AAAAAAAAFHk/ogxIaYHHs7U7AZWSbxdMqs02p0v_xNB_QCLcBGAsYHQ/w532-h640/86298192_2671850256203770_637164806452805632_o.jpg"));
        mImageList.add(new ImageModel("https://1.bp.blogspot.com/-bNSVaNMg2MM/XmBRz4d_H4I/AAAAAAAAFIA/98BcVdNaNScPweTI2Zfjs-VAw4Y1T0m4ACLcBGAsYHQ/w640-h346/hacker.jpg"));






        Toast.makeText(this, ""+mImageList.get(1), Toast.LENGTH_SHORT).show();
        Log.d(mImageList.get(1)+"","img");


        ImageSliderAdapter adapter = new ImageSliderAdapter(this, mImageList);
        // Set up the RecyclerView and ViewPager2
        viewPager.setAdapter(adapter);


        // Get references to the next and back buttons
        Button previousButton = findViewById(R.id.previousButton);
        Button nextButton = findViewById(R.id.nextButton);

// Set click listeners on the buttons
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });



        // in onCreate or onCreateView method
        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                int currentItem = viewPager.getCurrentItem();
                if (e1.getX() < e2.getX()) {
                    // swiped right
                    viewPager.setCurrentItem(currentItem - 1, true);
                } else if (e1.getX() > e2.getX()) {
                    // swiped left
                    viewPager.setCurrentItem(currentItem + 1, true);
                }
                return true;
            }
        });


        // Start the timer to switch between the images
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPosition++;
                if (mPosition >= mImageList.size()) {
                    mPosition = 0;
                }
                viewPager.setCurrentItem(mPosition);
                mHandler.postDelayed(this, 3000); // Change image every 5 seconds
            }
        }, 5000); // S


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // not used
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updateIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // not used
            }
        });

        addIndicators(mImageList.size());
        updateIndicator();
    }

    private void addIndicators(int count) {
        for (int i = 0; i < count; i++) {
            View indicatorView = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.indicator_size),
                    getResources().getDimensionPixelSize(R.dimen.indicator_size));
            params.setMargins(getResources().getDimensionPixelSize(R.dimen.indicator_margin), 0, 0, 0);
            indicatorView.setLayoutParams(params);
            indicatorView.setBackgroundResource(R.drawable.indicator_background);
            indicatorLayout.addView(indicatorView);
        }
    }

    private void updateIndicator() {
        for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
            View indicatorView = indicatorLayout.getChildAt(i);
            indicatorView.setBackgroundResource(i == currentPosition ? R.drawable.indicator_selected : R.drawable.indicator_unselected);
        }
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri croppedImageUri = result.getUri();
//                // TODO: Do something with the cropped image URI
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                // TODO: Handle cropping error
//            }
//        }
//    }

}

