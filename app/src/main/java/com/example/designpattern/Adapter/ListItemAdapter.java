package com.example.designpattern.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.Bookmark;
import com.example.designpattern.Models.Favourite;
import com.example.designpattern.Models.Language;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.R;
import com.example.designpattern.Services.BookmarkService;
import com.example.designpattern.Services.FavouriteService;
import com.example.designpattern.Services.LanguageService;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    private List<Pattern> dataList;
    private Context context;

    public ListItemAdapter(Context context, List<Pattern> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pattern item = dataList.get(position);
        holder.textViewCatalog.setText(item.getCatalog());
        String language = context.getString(R.string.item_language) + " " + item.getLanguage();
        holder.textViewLanguage.setText(language);
        holder.textViewTitle.setText(item.getName());
        TextPaint paint = holder.textViewTitle.getPaint();
        float width = paint.measureText(holder.textViewTitle.getText().toString());
        Shader textShader = new LinearGradient(0, 0, width, holder.textViewTitle.getTextSize(),
                new int[]{
                        Color.parseColor("#A8EDEA"),
                        Color.parseColor("#FED6E3"),
                }, null, Shader.TileMode.CLAMP);
        holder.textViewTitle.getPaint().setShader(textShader);

        BookmarkService bookmarkService = new BookmarkService(context);
        FavouriteService favouriteService = new FavouriteService(context);

        if (isBookmarked(item.getId())) {
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_fill);
        } else {
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark);
        }

        if (isFavoured(item.getId())) {
            holder.btnStar.setImageResource(R.drawable.ic_star_fill);
        }
        else {
            holder.btnStar.setImageResource(R.drawable.ic_star);
        }

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBookmarked(item.getId())) {
                    bookmarkService.Add(new Bookmark(item.getId()));
                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                } else {
                    bookmarkService.DeleteByPatternId(Bookmark.class, item.getId());
                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark);
                }
            }
        });

        holder.btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavoured(item.getId())) {
                    favouriteService.Add(new Favourite(item.getId()));
                    holder.btnStar.setImageResource(R.drawable.ic_star_fill);
                }
                else {
                    favouriteService.DeleteByPatternId(Favourite.class, item.getId());
                    holder.btnStar.setImageResource(R.drawable.ic_star);
                }
            }
        });
    }

    private boolean isBookmarked(int itemId) {
        BookmarkService bookmarkService = new BookmarkService(context);
        Bookmark bookmark = bookmarkService.FindByPatternId(Bookmark.class, itemId);
        return bookmark != null;
    }

    private boolean isFavoured(int itemId) {
        FavouriteService favouriteService = new FavouriteService(context);
        Favourite favourite = favouriteService.FindByPatternId(Favourite.class, itemId);
        return favourite != null;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCatalog;
        TextView textViewLanguage;
        TextView textViewTitle;
        Button button;
        ImageButton btnStar, btnBookmark, btnShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCatalog = itemView.findViewById(R.id.textViewCatalog);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguage);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            button = itemView.findViewById(R.id.btnView);
            btnBookmark = itemView.findViewById(R.id.ibtnBookmark);
            btnStar = itemView.findViewById(R.id.ibtnStar);
            btnShare = itemView.findViewById(R.id.ibtnShare);
            TextPaint paint = button.getPaint();
            float width = paint.measureText(button.getText().toString());
            Shader textShader = new LinearGradient(0, 0, width, button.getTextSize(),
                    new int[]{
                            Color.parseColor("#A8EDEA"),
                            Color.parseColor("#FED6E3"),
                    }, null, Shader.TileMode.CLAMP);
            button.getPaint().setShader(textShader);
        }
    }
}
