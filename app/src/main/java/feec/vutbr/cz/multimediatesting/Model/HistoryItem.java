package feec.vutbr.cz.multimediatesting.Model;

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

    public long getId() {
        return mId;
    }
}
