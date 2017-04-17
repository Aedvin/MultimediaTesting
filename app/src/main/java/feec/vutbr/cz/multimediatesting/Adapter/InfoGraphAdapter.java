package feec.vutbr.cz.multimediatesting.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import feec.vutbr.cz.multimediatesting.R;

import java.util.Map;

public class InfoGraphAdapter extends BaseAdapter {

    private Map<String, String> mItems;
    private String[] mKeys;
    private static LayoutInflater mLayoutInflater;

    public InfoGraphAdapter(Activity activity, Map<String, String> data) {
        mItems = data;
        mKeys = data.keySet().toArray(new String[data.size()]);
        mLayoutInflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(mKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.info_graph_item, null);
        TextView name = (TextView) v.findViewById(R.id.infoGraphName);
        TextView value = (TextView) v.findViewById(R.id.infoGraphValue);
        name.setText(mKeys[position]);
        value.setText(mItems.get(mKeys[position]));
        return v;
    }
}
