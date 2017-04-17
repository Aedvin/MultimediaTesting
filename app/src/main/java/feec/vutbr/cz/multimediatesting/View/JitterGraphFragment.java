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
import feec.vutbr.cz.multimediatesting.databinding.JitterGraphFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class JitterGraphFragment extends Fragment implements GraphActivityContract.Jitter {

    private JitterGraphFragmentBinding mBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, R.layout.jitter_graph_fragment, container, false);
        return mBind.getRoot();
    }


    @Override
    public void setJitter(long[] jitter) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < jitter.length; i++) {
            entries.add(new Entry(i, jitter[i]));
        }


        LineData lineData = new LineData(setLineDatasetAndStyle(entries));
        mBind.jitterGraph.setData(lineData);
        mBind.jitterGraph.invalidate();

        setGraphStyle();
        setXAxisStyle();
        setYAxisStyle();

    }

    private LineDataSet setLineDatasetAndStyle(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Jitter");
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(1.5f);
        dataSet.setColor(Color.RED);
        dataSet.setDrawCircles(false);
        return dataSet;
    }

    private void setGraphStyle() {
        mBind.jitterGraph.getLegend().setEnabled(false);
        mBind.jitterGraph.getDescription().setEnabled(false);
        mBind.jitterGraph.setHighlightPerTapEnabled(false);
        mBind.jitterGraph.setHighlightPerDragEnabled(false);
        mBind.jitterGraph.setExtraOffsets(10, 0, 0, 20);
    }

    private void setXAxisStyle() {
        XAxis xAxis = mBind.jitterGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setGridColor(Color.WHITE);
    }

    private void setYAxisStyle() {
        mBind.jitterGraph.getAxisRight().setEnabled(false);

        YAxis yAxis = mBind.jitterGraph.getAxisLeft();
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
