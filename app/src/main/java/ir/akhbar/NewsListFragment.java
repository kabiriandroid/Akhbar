package ir.akhbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment {

    private ProgressBar progress;
    private ProgressBar searchProgress;
    private RelativeLayout failureView;
    private RecyclerView newsRecycler;
    private EditText searchInput;
    private TextView toolbarTitle;
    private ImageView searchAction;

    private Handler handler;
    private Runnable searchRunnable;

    private Networking networking;

    private String searchQuery = "Iran";

    private final String defaultQuery = "Iran";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networking = new Networking();
        handler = new Handler();
        searchRunnable = new Runnable() {
            @Override
            public void run() {
                searchProgress.setVisibility(View.VISIBLE);
                searchAction.setVisibility(View.GONE);
                fetchData(networking, searchQuery);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbarTitle = (TextView) view.findViewById(R.id.toolbarTitle);
        searchInput = (EditText) view.findViewById(R.id.searchInput);

        searchProgress = (ProgressBar) view.findViewById(R.id.searchProgress);

        searchAction = (ImageView) view.findViewById(R.id.actionSearch);
        searchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarTitle.setVisibility(View.GONE);
                searchInput.setVisibility(View.VISIBLE);
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.toString().isEmpty()) {
                    searchQuery = defaultQuery;
                } else {
                    searchQuery = s.toString();
                }
                handler.removeCallbacks(searchRunnable);
                handler.postDelayed(searchRunnable, 1000);
            }
        });

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
                fetchData(networking, defaultQuery);
            }
        });

        fetchData(networking, defaultQuery);
    }

    private void fetchData(Networking networking, String query) {
        networking.getServer()
                .getNewsList(query, "6fba2629782d465abd2dc5f427223cc0")
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        progress.setVisibility(View.GONE);
                        searchProgress.setVisibility(View.GONE);
                        searchAction.setVisibility(View.VISIBLE);
                        ServerResponse serverResponse = response.body();
                        NewsAdapter adapter = new NewsAdapter(serverResponse.getArticles(), new NewsItemClickListener() {
                            @Override
                            public void onClick(NewsData data) {
                                Bundle bundle = new Bundle();
                                bundle.putString("newsTitle", data.getNewsTitle());
                                bundle.putString("newsDescription", data.getNewsDescription());
                                bundle.putString("newsImage", data.getNewsImage());
                                bundle.putString("newsUrl", data.getUrl());
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

    public boolean canHandleBackPressed() {
        boolean canHandleBackPressed = false;
        if (searchInput.getVisibility() == View.VISIBLE) {
            searchInput.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.VISIBLE);
            searchProgress.setVisibility(View.VISIBLE);
            searchAction.setVisibility(View.GONE);
            fetchData(networking, defaultQuery);
            canHandleBackPressed = true;
        }
        return canHandleBackPressed;
    }

    @Override
    public void onDestroyView() {
        if (searchInput.getVisibility() == View.VISIBLE) {
            searchInput.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.VISIBLE);
        }
        super.onDestroyView();
    }
}
