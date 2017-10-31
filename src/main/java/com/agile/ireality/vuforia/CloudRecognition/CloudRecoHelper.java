package com.agile.ireality.vuforia.CloudRecognition;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sarath on 10/2/16.
 */
public class CloudRecoHelper {

    private AppCompatActivity mParentActivity;
    private Intent mCloudrecoHelperActivityIntent;

    public static MEDIA_TYPE getMediaType(String s){
        for (MEDIA_TYPE mediaType : MEDIA_TYPE.values()) {
            if(s.toLowerCase().equals(mediaType.getStringType()))
                return mediaType;
        }
        return MEDIA_TYPE.UNKNOWN;
    }

    @SuppressLint("NewApi")
    public boolean load(MetaData metaData) throws ActivityNotFoundException
    {
        if(metaData.getContentType() == MEDIA_TYPE.VIDEO ||
                metaData.getContentType() == MEDIA_TYPE.VIDEO_URL) {
            mCloudrecoHelperActivityIntent = new Intent(mParentActivity,
                    CloudRecoVideoPlaybackActivity.class);
            mCloudrecoHelperActivityIntent
                    .setAction(android.content.Intent.ACTION_VIEW);
            mCloudrecoHelperActivityIntent.putExtra("requestedOrientation",
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mCloudrecoHelperActivityIntent.putExtra("videoPath", metaData.getContent());
            mParentActivity.startActivityForResult(mCloudrecoHelperActivityIntent, 1);
        }else if(metaData.getContentType() == MEDIA_TYPE.IMAGE ||
                metaData.getContentType() == MEDIA_TYPE.IMAGE_URL){
            mCloudrecoHelperActivityIntent = new Intent(mParentActivity,
                    CloudRecoImageActivity.class);
            mCloudrecoHelperActivityIntent
                    .setAction(android.content.Intent.ACTION_VIEW);
            mCloudrecoHelperActivityIntent.putExtra("imagePath", metaData.getContent());
            mParentActivity.startActivityForResult(mCloudrecoHelperActivityIntent, 1);
        }else if(metaData.getContentType() == MEDIA_TYPE.YOUTUBE){
            mParentActivity.startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(metaData.getContent())));
        }else if(metaData.getContentType() == MEDIA_TYPE.PDF){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(metaData.getContent()));
            //intent.setData(Uri.parse("https://helpx.adobe.com/pdf/adobe_reader_reference.pdf"));
           // intent.setType("application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent target = Intent.createChooser(intent, "Open File");
            mParentActivity.startActivity(target);
        }else if(metaData.getContentType() == MEDIA_TYPE.LINK){
            mParentActivity.startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(metaData.getContent())));
        }else if(metaData.getContentType() == MEDIA_TYPE.TEXT){
            showDialog(metaData.getContent());
        }
        return true;
    }

    public void setActivity(AppCompatActivity newActivity)
    {
        mParentActivity = newActivity;
    }

    public enum MEDIA_TYPE
    {
        YOUTUBE                 (0),
        IMAGE                   (1),
        IMAGE_URL               (2),
        LINK                    (3),
        VIDEO                   (4),
        VIDEO_URL               (5),
        PDF                     (6),
        TEXT                    (7),
        UNKNOWN                 (8);

        private int type;
        private String[] strings= {"youtube url", "image", "image url", "url",
                "video","video url", "pdf","text", "unknown"};


        MEDIA_TYPE(int i)
        {
            this.type = i;
        }
        public int getNumericType()
        {
            return type;
        }

        public String getStringType()
        {
            return strings[type];
        }

    }

    private void showDialog(String message) {
        FragmentTransaction ft = mParentActivity.getSupportFragmentManager().beginTransaction();
        Fragment prev = mParentActivity.getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        TextDialog newFragment = TextDialog.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        newFragment.setArguments(bundle);
        newFragment.show(ft, "dialog");
    }



}
