package feec.vutbr.cz.multimediatesting.Model;

import feec.vutbr.cz.multimediatesting.View.HistoryActivity;

public class HistoryItem {

    private String mName;
    private long mId;

    public HistoryItem(String name, long id) {
        mName = name;
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public long getmId() {
        return mId;
    }
}
