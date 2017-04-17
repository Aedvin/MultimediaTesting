package feec.vutbr.cz.multimediatesting.View;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import feec.vutbr.cz.multimediatesting.Contract.GraphActivityContract;
import feec.vutbr.cz.multimediatesting.R;
import feec.vutbr.cz.multimediatesting.databinding.DelayGraphFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class DelayGraphFragment extends Fragment implements GraphActivityContract.Delay {

    private DelayGraphFragmentBinding mBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, R.layout.delay_graph_fragment, container, false);
        return mBind.getRoot();
    }


    @Override
    public void setDelay(long[] delay) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < delay.length; i++) {
            entries.add(new Entry(i, delay[i]));
        }


        LineData lineData = new LineData(setLineDatasetAndStyle(entries));
        mBind.delayGraph.setData(lineData);
        mBind.delayGraph.invalidate();

        setGraphStyle();
        setXAxisStyle();
        setYAxisStyle();

    }

    private LineDataSet setLineDatasetAndStyle(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Delays");
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(1.5f);
        dataSet.setColor(Color.RED);
        dataSet.setDrawCircles(false);
        return dataSet;
    }

    private void setGraphStyle() {
        mBind.delayGraph.getLegend().setEnabled(false);
        mBind.delayGraph.getDescription().setEnabled(false);
        mBind.delayGraph.setHighlightPerTapEnabled(false);
        mBind.delayGraph.setHighlightPerDragEnabled(false);
        mBind.delayGraph.setExtraOffsets(10, 0, 0, 20);
    }

    private void setXAxisStyle() {
        XAxis xAxis = mBind.delayGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setGridColor(Color.WHITE);
    }

    private void setYAxisStyle() {
        mBind.delayGraph.getAxisRight().setEnabled(false);

        YAxis yAxis = mBind.delayGraph.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(12);
        yAxis.setTypeface(Typeface.DEFAULT_BOLD);
        yAxis.setGridColor(Color.WHITE);

        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((long) value) + " ms";
            }
        });
    }
}
