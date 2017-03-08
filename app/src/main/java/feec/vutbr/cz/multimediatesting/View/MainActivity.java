package feec.vutbr.cz.multimediatesting.View;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import feec.vutbr.cz.multimediatesting.Adapter.FragmentAdapter;
import feec.vutbr.cz.multimediatesting.Contract.FragmentActivityContract;
import feec.vutbr.cz.multimediatesting.Contract.MainActivityContract;
import feec.vutbr.cz.multimediatesting.Loader.PresenterLoader;
import feec.vutbr.cz.multimediatesting.Presenter.MainActivityPresenter;
import feec.vutbr.cz.multimediatesting.R;
import feec.vutbr.cz.multimediatesting.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        MainActivityContract.View, LoaderManager.LoaderCallbacks<MainActivityContract.Presenter>,
        FragmentActivityContract.Activity {

    private static final int LOADER_ID = 1;

    private ActivityMainBinding mBind;
    private MainActivityContract.Presenter mPresenter;
    private FragmentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBind.toolbar);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        mAdapter = new FragmentAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onAttachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onDetachView();
    }

    @Override
    public Loader<MainActivityContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new MainActivityPresenter.Factory());
    }

    @Override
    public void onLoadFinished(Loader<MainActivityContract.Presenter> loader, MainActivityContract.Presenter data) {
        mPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<MainActivityContract.Presenter> loader) {
        mPresenter = null;
    }


    @Override
    public void beginTransition(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mAdapter.getFragment(position), "MainActivityFragment").commit();
    }

    @Override
    public void requestTransition(int currentPosition) {
        mPresenter.onTransitionRequest(currentPosition);
    }
}
