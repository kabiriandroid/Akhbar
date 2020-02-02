package ir.akhbar;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private ImageView newsImage;

    private NewsItemClickListener itemClickListener;

    public NewsViewHolder(View itemView, NewsItemClickListener itemClickListener) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.newsTitle);
        description = (TextView) itemView.findViewById(R.id.newsDescription);
        newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
        this.itemClickListener = itemClickListener;
    }

    public void bind(final NewsData newsData) {
        title.setText(newsData.getNewsTitle());
        description.setText(newsData.getNewsDescription());

        Glide.with(newsImage.getContext())
                .load(newsData.getNewsImage())
                .fitCenter()
                .into(newsImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(newsData);
            }
        });
    }
}
