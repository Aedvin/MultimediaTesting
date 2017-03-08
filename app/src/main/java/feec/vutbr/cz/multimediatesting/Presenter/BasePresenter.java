package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.View.BaseView;

/**
 * Created by alda on 1.3.17.
 */
public interface BasePresenter<V extends BaseView> {
    void onAttachView(@NonNull V view);

    void onDetachView();

    void onDestroy();
}
