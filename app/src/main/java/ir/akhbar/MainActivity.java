package ir.akhbar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NewsListFragment newsListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListFragment = new NewsListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, newsListFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!newsListFragment.canHandleBackPressed()) {
            super.onBackPressed();
        }
    }
}
