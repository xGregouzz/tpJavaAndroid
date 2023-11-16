package edu.esiea.androidcourse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import edu.esiea.androidcourse.R;
import edu.esiea.androidcourse.data.NewsAdapter;

public class NewsDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        TextView newsTitleTextView = findViewById(R.id.newsTitleTextView);
        TextView newsDescriptionTextView = findViewById(R.id.newsDescriptionTextView);
        ImageView newsImageView = findViewById(R.id.newsImageView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("newsTitle") && intent.hasExtra("newsDescription") && intent.hasExtra("newsImageUrl")) {
            String newsTitle = intent.getStringExtra("newsTitle");
            String newsDescription = intent.getStringExtra("newsDescription");
            String imageUrl = intent.getStringExtra("newsImageUrl");
            Glide.with(this)
                    .load(imageUrl)
                    .into(newsImageView);
            newsTitleTextView.setText(newsTitle);
            newsDescriptionTextView.setText(newsDescription);
        } else {
            showToast("Erreur lors de la récupération des détails de l'actualité");
            finish();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

