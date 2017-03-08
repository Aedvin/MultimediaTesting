package feec.vutbr.cz.multimediatesting.Factory;

import feec.vutbr.cz.multimediatesting.Model.BaseModel;

/**
 * Created by alda on 1.3.17.
 */
public interface ModelFactory<T extends BaseModel> {
    T create();
}
