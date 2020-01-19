package ir.akhbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        ImageView newsHeaderImage = (ImageView) view.findViewById(R.id.newsHeaderImage);
        TextView newsTitleText = (TextView) view.findViewById(R.id.newsTitle);
        TextView newsDescriptionText = (TextView) view.findViewById(R.id.newsDescription);
        newsTitleText.setText(bundle.getString("newsTitle"));
        newsDescriptionText.setText(bundle.getString("newsDescription"));
        Glide.with(getContext())
                .load(bundle.getString("newsImage"))
                .into(newsHeaderImage);
    }
}
