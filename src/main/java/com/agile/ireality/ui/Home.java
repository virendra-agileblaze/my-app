package com.agile.ireality.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.agile.ireality.R;
import com.agile.ireality.vuforia.CloudRecognition.CloudRecoActivity;


public class Home extends Activity {

    private ImageButton scanImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scanImage = (ImageButton) findViewById(R.id.scan_icon);
        scanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoImageTarget = new Intent(Home.this, CloudRecoActivity.class);
                startActivity(gotoImageTarget);
            }
        });
    }

}
