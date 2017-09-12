package divakar.mad.ids.restaurant_finder;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ldivakar on 5/2/2017.
 */

public class FindRestaurants extends AsyncTask<String, Void, String> {
    public static String ZOMATO_KEY = "2cc16244298016c47012e16b5c0946ff";
    static String restaurantName = "";
    static String output = "";

    public static void restaurants() {
        String requestURL = "";

        if (MainActivity.flagForLocation) {
            requestURL = "https://developers.zomato.com/api/v2.1/geocode?lat=" + MainActivity.latitude + "&lon=" + MainActivity.longitude;
        } else
            requestURL = "https://developers.zomato.com/api/v2.1/geocode?lat=41.8705515&lon=-87.665754";
        String[] urls = new String[2];
        urls[0] = requestURL;
        urls[1] = "2";

        new FindRestaurants().execute(urls);


    }

    public static void searchRestaurants(String restaurantName) {
        try {
            FindRestaurants.restaurantName = restaurantName;
            restaurantName = restaurantName.replaceAll(" ", "%20");
            String requestURL = "";

            if (MainActivity.flagForLocation) {
                requestURL = "https://developers.zomato.com/api/v2.1/search?q=" + restaurantName + "&lat=" + MainActivity.latitude + "&lon=" + MainActivity.longitude + "&radius=10000";
            } else
                requestURL = "https://developers.zomato.com/api/v2.1/search?q=" + restaurantName + "&lat=41.8705515&lon=-87.665754&radius=100000";

            String[] urls = new String[2];
            urls[0] = requestURL;
            urls[1] = "1";
            AsyncTask builder = new FindRestaurants().execute(urls);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void reviews(String restaurantName) {
        try {
            FindRestaurants.restaurantName = restaurantName;
            restaurantName = restaurantName.replaceAll(" ", "%20");
            String requestURL = "";

            if (MainActivity.flagForLocation) {
                requestURL = "https://developers.zomato.com/api/v2.1/search?q=" + restaurantName + "&lat=" + MainActivity.latitude + "&lon=" + MainActivity.longitude + "&radius=10000";
            } else
                requestURL = "https://developers.zomato.com/api/v2.1/search?q=" + restaurantName + "&lat=41.8705515&lon=-87.665754&radius=100000";

            String[] urls = new String[2];
            urls[0] = requestURL;
            urls[1] = "3";
            AsyncTask builder = new FindRestaurants().execute(urls);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    protected String doInBackground(String[] strings) {

        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(strings[0]);
        try {
            httpGet.addHeader("user-key", ZOMATO_KEY);
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("JSON error", "Failed to get JSON object");
            }
            if (strings[1].equals("1"))
                doProcessForRestaturant(builder.toString());
            else if (strings[1].equals("2"))
                doProcessForAllRestaturant(builder.toString());
            else if (strings[1].equals("3"))
                doProcessForRev(builder.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private static void doProcessForRestaturant(String builder) {
        try {
            String s = new JSONObject(builder.toString()).getJSONArray("restaurants").get(0).toString();
            JSONObject jsonObject = new JSONObject(s);
            jsonObject = jsonObject.getJSONObject("restaurant");
            String name = jsonObject.get("name").toString();

            String address = jsonObject.getJSONObject("location").get("address").toString();
            String cuisines = jsonObject.get("cuisines").toString();
            int average_cost_for_two = jsonObject.getInt("average_cost_for_two");
            String userRating = jsonObject.getJSONObject("user_rating").get("aggregate_rating").toString();
            StringBuilder builder1 = new StringBuilder();
            builder1.append("Name : " + name + "\n");
            builder1.append("Address : " + address + "\n");
            builder1.append("cuisines : " + cuisines + "\n");
            builder1.append("average_cost_for_two : " + average_cost_for_two + "\n");
            builder1.append("userRating : " + userRating + "\n");
            output = builder1.toString();
            Main2Activity.output = output;
        } catch (Exception e) {
            Main2Activity.output = "No Restaurants Found in the surrounding 10kms, Please Input a valid name in the nearby locality";
        }

    }

    private static void doProcessForRev(String builder) throws Exception {
        try {
            String s = new JSONObject(builder.toString()).getJSONArray("restaurants").get(0).toString();
            JSONObject jsonObject = new JSONObject(s);
            jsonObject = jsonObject.getJSONObject("restaurant");
            String name = jsonObject.get("name").toString();

            String address = jsonObject.getJSONObject("location").get("address").toString();
            String cuisines = jsonObject.get("cuisines").toString();
            int average_cost_for_two = jsonObject.getInt("average_cost_for_two");

            String userRating = jsonObject.getJSONObject("user_rating").get("aggregate_rating").toString();
            String ratingText = jsonObject.getJSONObject("user_rating").get("rating_text").toString();
            String votes = jsonObject.getJSONObject("user_rating").get("votes").toString();

            StringBuilder builder1 = new StringBuilder();
            builder1.append("Name : " + name + "\n");
            builder1.append("Address : " + address + "\n");
            builder1.append("\n-------------------------\n");
            builder1.append("Review  : " + ratingText + "\n");
            builder1.append("userRating : " + userRating + "\n");
            builder1.append("Votes : " + votes + "\n");

            output =  "\n" + builder1.toString();
            Review.output = output;
        } catch (Exception e) {
            Review.output = "No Restaurants Found in the surrounding 10kms, Please Input a valid name in the nearby locality";
        }


    }

    private static void doProcessForAllRestaturant(String builder) throws Exception {
        StringBuilder builder1 = new StringBuilder();
        JSONArray j = new JSONObject(builder.toString()).getJSONArray("nearby_restaurants");
        output = "";
        for (int i = 0; i < j.length(); i++) {
            builder1 = new StringBuilder();
            String s = j.get(i).toString();
            JSONObject jsonObject = new JSONObject(s);
            jsonObject = jsonObject.getJSONObject("restaurant");
            String name = jsonObject.get("name").toString();

            String address = jsonObject.getJSONObject("location").get("address").toString();
            String cuisines = jsonObject.get("cuisines").toString();
            int average_cost_for_two = jsonObject.getInt("average_cost_for_two");
            String userRating = jsonObject.getJSONObject("user_rating").get("aggregate_rating").toString();
            builder1.append("Name : " + name + "\n");

            builder1.append("Address : " + address + "\n");
            builder1.append("cuisines : " + cuisines + "\n");
            builder1.append("average_cost_for_two : " + average_cost_for_two + "\n");
            builder1.append("userRating : " + userRating + "\n");
            builder1.append("\n-------------------------\n");

            output += restaurantName + "\n" + builder1.toString();
            if (i == 3)
                break;
        }


        allRest.output = output;

    }

}
