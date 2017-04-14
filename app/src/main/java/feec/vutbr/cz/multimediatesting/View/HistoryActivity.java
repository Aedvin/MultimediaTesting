package feec.vutbr.cz.multimediatesting.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import feec.vutbr.cz.multimediatesting.Adapter.HistoryAdapater;
import feec.vutbr.cz.multimediatesting.Contract.HistoryActivityContract;
import feec.vutbr.cz.multimediatesting.Loader.PresenterLoader;
import feec.vutbr.cz.multimediatesting.Model.Database;
import feec.vutbr.cz.multimediatesting.Model.HistoryItem;
import feec.vutbr.cz.multimediatesting.Presenter.HistoryPresenter;
import feec.vutbr.cz.multimediatesting.R;
import feec.vutbr.cz.multimediatesting.databinding.ActivityHistoryBinding;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements HistoryActivityContract.View,
        LoaderManager.LoaderCallbacks<HistoryActivityContract.Presenter> {

    private static final int LOADER_ID = 1;

    private ActivityHistoryBinding mBind;

    private HistoryActivityContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBind = DataBindingUtil.setContentView(this, R.layout.activity_history);

        mBind.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(mBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onDetachView();
    }

    @Override
    public Loader<HistoryActivityContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new HistoryPresenter.Factory());
    }

    @Override
    public void onLoadFinished(Loader<HistoryActivityContract.Presenter> loader, HistoryActivityContract.Presenter data) {
        mPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<HistoryActivityContract.Presenter> loader) {
        mPresenter = null;
    }


    @Override
    public void closeView() {
        onBackPressed();
    }

    @Override
    public void setHistoryData(ArrayList<HistoryItem> list) {
        mBind.recyclerView.setAdapter(new HistoryAdapater(this, mPresenter, list));
    }
}
