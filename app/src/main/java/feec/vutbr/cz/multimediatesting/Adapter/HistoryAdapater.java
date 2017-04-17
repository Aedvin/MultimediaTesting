package feec.vutbr.cz.multimediatesting.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import feec.vutbr.cz.multimediatesting.Contract.HistoryActivityContract;
import feec.vutbr.cz.multimediatesting.Model.HistoryItem;
import feec.vutbr.cz.multimediatesting.R;

import java.util.ArrayList;


public class HistoryAdapater extends RecyclerView.Adapter<HistoryAdapater.HistoryViewHolder> {

    private static LayoutInflater mInflater;
    private ArrayList<HistoryItem> mItems;
    private HistoryActivityContract.Presenter mPresenter;
    private Activity mActivity;

    public HistoryAdapater(Activity activity, HistoryActivityContract.Presenter presenter, ArrayList<HistoryItem> items) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPresenter = presenter;
        mItems = items;
        mActivity = activity;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final HistoryItem item = mItems.get(position);
        holder.mName.setText(item.getName());

        final Animation animation = AnimationUtils.loadAnimation(mActivity.getApplicationContext(), R.anim.anim_aplha);

        holder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                mPresenter.onItemClick(item.getId());
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                mPresenter.onDeleteClick(item.getId());
            }
        });

        holder.mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                mPresenter.onSaveClick(item.getId());
            }
        });

        if (position % 2 == 0) {
            holder.mBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.buttonPressed));
        } else {
            holder.mBackground.setBackgroundColor(holder.itemView.getResources().getColor(R.color.buttonStrokePressed));
        }
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    protected class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public ImageView mDelete;
        public ImageView mSave;
        public LinearLayout mBackground;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.historyItemName);
            mDelete = (ImageView) itemView.findViewById(R.id.historyItemDelete);
            mSave = (ImageView) itemView.findViewById(R.id.historyItemSave);
            mBackground = (LinearLayout) itemView.findViewById(R.id.historyItemBackground);
        }
    }
}
