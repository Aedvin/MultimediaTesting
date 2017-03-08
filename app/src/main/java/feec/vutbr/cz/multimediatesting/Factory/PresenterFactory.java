package feec.vutbr.cz.multimediatesting.Factory;

import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;

/**
 * Created by alda on 1.3.17.
 */
public interface PresenterFactory<T extends BasePresenter> {
    T create();
}
