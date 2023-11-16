package edu.esiea.androidcourse.activities;

import android.os.Bundle;
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

        newsAdapter = new NewsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsAdapter);

        loadNewsData(null); // Chargez les actualités initiales sans mot-clé de recherche

        // Écouteur de recherche pour mettre à jour les actualités en fonction du mot-clé entré
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadNewsData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Si vous souhaitez réagir également aux changements de texte en temps réel, implémentez ici
                return false;
            }
        });
    }

    private void loadNewsData(String keyword) {
        newsApiRequest.getNewsList(keyword, new NewsApiRequest.NewsApiCallback() {
            @Override
            public void onSuccess(List<NewsModel> newsList) {
                // Mettez à jour l'adaptateur de RecyclerView avec les nouvelles actualités
                newsAdapter.setNewsList(newsList);
            }

            @Override
            public void onError(String errorMessage) {
                // Affichez un message d'erreur en cas d'échec de récupération des actualités
                showToast("Erreur lors de la récupération des actualités: " + errorMessage);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


