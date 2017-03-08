package feec.vutbr.cz.multimediatesting.Adapter;

import android.support.v4.app.Fragment;
import feec.vutbr.cz.multimediatesting.View.ConnectionFragment;

/**
 * Created by alda on 2.3.17.
 */
public class FragmentAdapter {

    public int getCount() {
        return 2;
    }

    public Fragment getFragment(int position) {
        switch (position) {
            case 1:
                return new ConnectionFragment();
            case 2:
                return new ConnectionFragment();
        }
        return null;
    }
}
