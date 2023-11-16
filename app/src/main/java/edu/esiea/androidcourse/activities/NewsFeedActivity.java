package edu.esiea.androidcourse.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.esiea.androidcourse.R;
import edu.esiea.androidcourse.data.NewsAdapter;
import edu.esiea.androidcourse.data.NewsApiRequest;
import edu.esiea.androidcourse.models.NewsModel;

public class NewsFeedActivity extends AppCompatActivity {

    private NewsApiRequest newsApiRequest;
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        newsApiRequest = new NewsApiRequest(this);

        recyclerView = findViewById(R.id.newsRecyclerView);
        searchView = findViewById(R.id.searchView);

        // Configure RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configure SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadNewsData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Implement if needed
                return false;
            }
        });

        // Initialize and set the adapter after configuring RecyclerView
        newsAdapter = new NewsAdapter(this);
        // Load initial news data
        loadNewsData(null);
        recyclerView.setAdapter(newsAdapter);
    }

    private void loadNewsData(String keyword) {
        newsApiRequest.getNewsList(keyword, new NewsApiRequest.NewsApiCallback() {
            @Override
            public void onSuccess(List<NewsModel> newsList) {
                Log.d("NewsFeedActivity", "Nombre d'actualités reçues : " + newsList.size());
                newsAdapter.setNewsList(newsList);
            }

            @Override
            public void onError(String errorMessage) {
                showToast("Erreur lors de la récupération des actualités : " + errorMessage);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
