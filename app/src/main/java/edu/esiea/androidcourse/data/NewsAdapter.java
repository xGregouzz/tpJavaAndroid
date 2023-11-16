package edu.esiea.androidcourse.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.esiea.androidcourse.R;
import edu.esiea.androidcourse.activities.NewsDetailsActivity;
import edu.esiea.androidcourse.models.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsModel> newsList;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setNewsList(List<NewsModel> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged(); // Notifiez l'adaptateur que les données ont changé
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel newsModel = newsList.get(position);

        // Remplissez les vues avec les données de l'actualité
        holder.titleTextView.setText(newsModel.getTitle());
        holder.publishedAtTextView.setText(newsModel.getPublishedAt());

        // Utilisez la bibliothèque Glide pour charger l'image depuis l'URL
        Glide.with(context)
                .load(newsModel.getImageUrl())
                .into(holder.newsImageView);

        // Ajoutez un écouteur de clic pour afficher les détails de l'actualité
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Passez aux détails de l'actualité
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("newsTitle", newsModel.getTitle());
                intent.putExtra("newsDescription", newsModel.getDescription());
                intent.putExtra("newsImageUrl", newsModel.getImageUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView publishedAtTextView;
        ImageView newsImageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
        }
    }
}
