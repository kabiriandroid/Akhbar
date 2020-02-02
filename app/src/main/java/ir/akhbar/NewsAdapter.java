package ir.akhbar;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private NewsData[] newsArray;

    private NewsItemClickListener itemClickListener;

    public NewsAdapter(NewsData[] newsArray, NewsItemClickListener itemClickListener) {
        this.newsArray = newsArray;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_list, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(itemView, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsData data = newsArray[position];
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return newsArray.length;
    }
}
