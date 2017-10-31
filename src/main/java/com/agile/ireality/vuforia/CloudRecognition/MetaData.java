package com.agile.ireality.vuforia.CloudRecognition;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sarath on 10/2/16.
 */
public class MetaData {
    private String name;
    String contentType;
    private String content;

    public static MetaData getInstance(JSONObject jsonObject) throws JSONException {
        MetaData metaData = new MetaData();
        metaData.contentType = jsonObject.getString("content_type");
        metaData.name = jsonObject.getString("name");
        metaData.content = jsonObject.getString("content");
        return metaData;
    }

    private MetaData(){}

    public String getName() {
        return name;
    }

    public CloudRecoHelper.MEDIA_TYPE getContentType() {
        CloudRecoHelper.MEDIA_TYPE mediaType= CloudRecoHelper.getMediaType(contentType);
        /*
        Do metadata validation
         */
        if(mediaType == CloudRecoHelper.MEDIA_TYPE.YOUTUBE
                && !content.startsWith("https://www.youtube.com/")){
            mediaType= CloudRecoHelper.MEDIA_TYPE.UNKNOWN;
        }else if((mediaType == CloudRecoHelper.MEDIA_TYPE.VIDEO ||
                mediaType == CloudRecoHelper.MEDIA_TYPE.VIDEO_URL)
                && !(content.startsWith("https://") || content.startsWith("http://"))){
            mediaType= CloudRecoHelper.MEDIA_TYPE.UNKNOWN;
        }else if((mediaType == CloudRecoHelper.MEDIA_TYPE.IMAGE ||
                mediaType == CloudRecoHelper.MEDIA_TYPE.IMAGE_URL)
                        && !(content.startsWith("https://") || content.startsWith("http://"))){
            mediaType= CloudRecoHelper.MEDIA_TYPE.UNKNOWN;
        }else if(mediaType == CloudRecoHelper.MEDIA_TYPE.LINK
                        && !(content.startsWith("https://") || content.startsWith("http://"))){
            mediaType= CloudRecoHelper.MEDIA_TYPE.UNKNOWN;
        }
        return mediaType;
    }

    public String getContent() {
        return content;
    }


}
