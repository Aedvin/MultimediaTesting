package feec.vutbr.cz.multimediatesting.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import feec.vutbr.cz.multimediatesting.Contract.SettingsActivityContract;
import feec.vutbr.cz.multimediatesting.Loader.PresenterLoader;
import feec.vutbr.cz.multimediatesting.Model.SavedSettings;
import feec.vutbr.cz.multimediatesting.Presenter.SettingsPresenter;
import feec.vutbr.cz.multimediatesting.R;
import feec.vutbr.cz.multimediatesting.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity implements SettingsActivityContract.View,
        LoaderManager.LoaderCallbacks<SettingsActivityContract.Presenter> {

    private static final int LOADER_ID = 1;

    private ActivitySettingsBinding mBind;
    private SettingsActivityContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBind = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        setSupportActionBar(mBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        mBind.editSettingsPacketSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.onPacketSizeChange(s.toString());
            }
        });

        mBind.editSettingsPacketCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.onPacketCountChange(s.toString());
            }
        });

        mBind.editSettingsServerAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

                mPresenter.onServerAddressChange(s.toString());
            }
        });


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
        mPresenter.setSavedSettings(new SavedSettings(getApplicationContext()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onDetachView();
    }


    @Override
    public Loader<SettingsActivityContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new SettingsPresenter.Factory());
    }

    @Override
    public void onLoadFinished(Loader<SettingsActivityContract.Presenter> loader, SettingsActivityContract.Presenter data) {
        mPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<SettingsActivityContract.Presenter> loader) {
        mPresenter = null;
    }

    @Override
    public void closeView() {
        onBackPressed();
    }

    @Override
    public void setPacketSize(String packetSize) {
        mBind.editSettingsPacketSize.setText(packetSize);
    }

    @Override
    public void setPacketCount(String packetCount) {
        mBind.editSettingsPacketCount.setText(packetCount);
    }

    @Override
    public void setServerAddress(String address) {
        mBind.editSettingsServerAddress.setText(address);
    }
}
