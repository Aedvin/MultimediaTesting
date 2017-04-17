package feec.vutbr.cz.multimediatesting.View;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import feec.vutbr.cz.multimediatesting.Adapter.GraphPagerAdapter;
import feec.vutbr.cz.multimediatesting.Contract.GraphActivityContract;
import feec.vutbr.cz.multimediatesting.Loader.PresenterLoader;
import feec.vutbr.cz.multimediatesting.Model.Database;
import feec.vutbr.cz.multimediatesting.Presenter.GraphPresenter;
import feec.vutbr.cz.multimediatesting.R;
import feec.vutbr.cz.multimediatesting.databinding.ActivityGraphBinding;

import java.util.Map;

public class GraphActivity extends AppCompatActivity implements GraphActivityContract.View,
        LoaderManager.LoaderCallbacks<GraphActivityContract.Presenter> {

    private static final int LOADER_ID = 1;

    private ActivityGraphBinding mBind;
    private GraphActivityContract.Presenter mPresenter;
    private GraphPagerAdapter mAdapter;
    private boolean mFirstTime;

    private long mId;

    public GraphActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_graph);
        setSupportActionBar(mBind.toolbar);
        mBind.graphTabs.setupWithViewPager(mBind.graphPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirstTime = true;
        final String[] tabNames = {getResources().getString(R.string.info_graph_fragment_name),
                getResources().getString(R.string.delay_graph_fragment_name),
                getResources().getString(R.string.jitter_graph_fragment_name)};
        mAdapter = new GraphPagerAdapter(getSupportFragmentManager(), tabNames);
        mBind.graphPager.setAdapter(mAdapter);
        mBind.graphPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset == 0 && mFirstTime) {
                    mFirstTime = false;
                    onPageSelected(0);
                }
                return;
            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onPageChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                return;
            }
        });
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        final Intent i = getIntent();
        if (i == null) {
            closeView();
        } else {
            mId = i.getLongExtra("id", 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mPresenter.onHomeClick();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onAttachView(this);
        mPresenter.setDatabaseConnection(new Database(getApplicationContext()));
        mPresenter.setId(mId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onDetachView();
    }

    @Override
    public Loader<GraphActivityContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new GraphPresenter.Factory());
    }

    @Override
    public void onLoadFinished(Loader<GraphActivityContract.Presenter> loader, GraphActivityContract.Presenter data) {
        mPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<GraphActivityContract.Presenter> loader) {
        mPresenter = null;
    }

    @Override
    public void closeView() {
        onBackPressed();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInfo(Map<String, String> info) {
        Fragment f = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.graphPager + ":0");
        if (f != null) {
            ((GraphActivityContract.Info) f).setInfo(info);
        }
    }

    @Override
    public void setDelay(long[] delay) {
        Fragment f = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.graphPager + ":1");
        if (f != null) {
            ((GraphActivityContract.Delay) f).setDelay(delay);
        }
    }

    @Override
    public void setJitter(long[] jitter) {
        Fragment f = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.graphPager + ":2");
        if (f != null) {
            ((GraphActivityContract.Jitter) f).setJitter(jitter);
        }
    }


}
