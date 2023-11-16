package edu.esiea.androidcourse.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.esiea.androidcourse.models.NewsModel;

public class NewsApiRequest {

    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything";
    private static final String API_KEY = "e209794147f14ec7ba5fb0a5d5e9c96a\n"; // Remplacez par votre clé API

    private RequestQueue requestQueue;

    public NewsApiRequest(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public interface NewsApiCallback {
        void onSuccess(List<NewsModel> newsList);
        void onError(String errorMessage);
    }

    public void getNewsList(String keyword, final NewsApiCallback callback) {
        String url;
        if (keyword != null && !keyword.isEmpty()) {
            url = NEWS_API_URL + "?q=" + keyword + "&apiKey=" + API_KEY;
        } else {
            url = NEWS_API_URL + "?q=null&apiKey=" + API_KEY;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        List<NewsModel> newsList = parseJsonResponse(response);
                        callback.onSuccess(newsList);
                    } catch (JSONException e) {
                        callback.onError("Erreur de parsing JSON");
                    }
                },
                error -> {
                    callback.onError("Erreur de requête : " + error.getMessage());
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Android");
            return headers;
        }
        };

        requestQueue.add(request);
    }

    private List<NewsModel> parseJsonResponse(JSONObject response) throws JSONException {
        List<NewsModel> newsList = new ArrayList<>();
        JSONArray articlesArray = response.getJSONArray("articles");

        for (int i = 1; i < articlesArray.length(); i++) {
            JSONObject articleObject = articlesArray.getJSONObject(i);

            String title = articleObject.getString("title");
            String description = articleObject.getString("description");
            String imageUrl = articleObject.getString("urlToImage");
            String publishedAt = articleObject.getString("publishedAt");

            NewsModel newsModel = new NewsModel(title, description, imageUrl, publishedAt);
            newsList.add(newsModel);
        }

        return newsList;
    }
}

