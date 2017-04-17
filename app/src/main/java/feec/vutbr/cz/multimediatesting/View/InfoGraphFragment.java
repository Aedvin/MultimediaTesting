package feec.vutbr.cz.multimediatesting.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import feec.vutbr.cz.multimediatesting.Adapter.InfoGraphAdapter;
import feec.vutbr.cz.multimediatesting.Contract.GraphActivityContract;
import feec.vutbr.cz.multimediatesting.R;
import feec.vutbr.cz.multimediatesting.databinding.InfoGraphFragmentBinding;

import java.util.Map;

public class InfoGraphFragment extends Fragment implements GraphActivityContract.Info {

    private InfoGraphFragmentBinding mBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, R.layout.info_graph_fragment, container, false);
        return mBind.getRoot();
    }


    @Override
    public void setInfo(Map<String, String> info) {
        mBind.infoGraphList.setAdapter(new InfoGraphAdapter(getActivity(), info));
    }
}
