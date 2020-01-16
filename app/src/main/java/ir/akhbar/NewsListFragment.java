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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment {

    private ProgressBar progress;
    private RelativeLayout failureView;
    private RecyclerView newsRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Networking networking = new Networking();

        progress = (ProgressBar) view.findViewById(R.id.progress);
        failureView = (RelativeLayout) view.findViewById(R.id.failureView);
        newsRecycler = (RecyclerView) view.findViewById(R.id.newsRecycler);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Button retryButton = (Button) view.findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failureView.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                fetchData(networking);
            }
        });

        fetchData(networking);
    }

    private void fetchData(Networking networking) {
        networking.getServer()
                .getNewsList()
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        progress.setVisibility(View.GONE);
                        ServerResponse serverResponse = response.body();
                        NewsAdapter adapter = new NewsAdapter(serverResponse.getRoot(), new NewsItemClickListener() {
                            @Override
                            public void onClick(NewsData data) {
                                Bundle bundle = new Bundle();
                                bundle.putString("newsTitle", data.getNewsTitle());
                                bundle.putString("newsDescription", data.getNewsDescription());
                                bundle.putString("newsImage", data.getNewsImage());
                                NewsDetailFragment detailFragment = new NewsDetailFragment();
                                detailFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer, detailFragment)
                                        .commit();
                            }
                        });
                        newsRecycler.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        failureView.setVisibility(View.VISIBLE);
                    }
                });
    }
}
