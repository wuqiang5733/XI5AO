package org.xuxiaoxiao.xiao.infrastructure;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by WuQiang on 2017/4/14.
 */

public class Internet {
    private static final String TAG = "WQ";

    private static final String API_KEY = "REPLACE_ME_WITH_A_REAL_KEY";
    private static final String BmobApplicationId = "6ac2a7be596fe87a4e38c1f86be4e55d";
    private static final String BmobRESTAPIKey = "f7c989210c1d07b96fe3450157aeccdc";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Bmob-Application-Id", BmobApplicationId);
        connection.setRequestProperty("X-Bmob-REST-API-Key", BmobRESTAPIKey);
        connection.setRequestProperty("Content-Type", "application/json");//设定 请求格式 json


        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public void fetchItems() {
//    public List<GalleryItem> fetchItems() {

//        List<GalleryItem> items = new ArrayList<>();

        try {
            String jsonString = getUrlString("https://api.bmob.cn/1/classes/test");
            Log.i(TAG, "Received JSON: " + jsonString);
//            JSONObject jsonBody = new JSONObject(jsonString);
//            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
//        catch (JSONException je) {
//            Log.e(TAG, "Failed to parse JSON", je);
//        }

//        return items;
//        return null;
    }


}
