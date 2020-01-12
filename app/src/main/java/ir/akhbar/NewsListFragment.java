package ir.akhbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NewsListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView newsRecycler = (RecyclerView) view.findViewById(R.id.newsRecycler);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        NewsData[] newsArray = new NewsData[]{
                new NewsData("Akhbar 1", "Akhbar 1 desc Akhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 descAkhbar 1 desc", "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"),
                new NewsData("Akhbar 2", "Akhbar 2 desc", "https://raw.githubusercontent.com/PHELAT/Poolakey/master/asset/Poolakey.jpg")
        };
        NewsAdapter adapter = new NewsAdapter(newsArray, new NewsItemClickListener() {
            @Override
            public void onClick(NewsData data) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, new NewsDetailFragment())
                        .commit();
            }
        });
        newsRecycler.setAdapter(adapter);
    }
}
