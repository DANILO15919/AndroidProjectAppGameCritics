package com.example.it322020.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it322020.Listeners.OnGameClickAListener;
import com.example.it322020.Models.SearchArrayObject;
import com.example.it322020.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    Context context;
    List<SearchArrayObject> list;
    OnGameClickAListener listener;

    public MainRecyclerAdapter(Context context, List<SearchArrayObject> list, OnGameClickAListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_game_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        holder.textView_game.setText(list.get(position).getName());
        Picasso.get().load(list.get(position).getBackground_image()).into(holder.imageView_game);

        holder.cardView_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGameClicked(list.get(position).getSlug());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView_game;
        TextView textView_game;
        CardView cardView_game;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_game = itemView.findViewById(R.id.image_poster);
            textView_game = itemView.findViewById(R.id.textView_game);
            cardView_game = itemView.findViewById(R.id.main_container);
        }
    }
}
