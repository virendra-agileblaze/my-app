package com.agile.ireality.vuforia.CloudRecognition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.agile.ireality.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by sarath on 10/2/16.
 */
public class CloudRecoImageActivity extends AppCompatActivity{

    private ImageView imageView;
    private PhotoViewAttacher mAttacher;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_layout);
        imageUrl = getIntent().getStringExtra("imagePath");
        initialize();
    }

    private void initialize() {
        imageView  = (ImageView)findViewById(R.id.imageview);
        setupControls();
    }

    private void setupControls() {
        Picasso.with(CloudRecoImageActivity.this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder_image)
                .into(imageView,imageLoadedCallback);
    }

    Callback imageLoadedCallback = new Callback() {

        @Override
        public void onSuccess() {
            if(mAttacher!=null){
                mAttacher.update();
            }else{
                mAttacher = new PhotoViewAttacher(imageView);
            }
        }

        @Override
        public void onError() {
        }
    };

}
