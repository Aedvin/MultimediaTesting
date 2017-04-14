package feec.vutbr.cz.multimediatesting.Adapter;

import android.support.v4.app.Fragment;
import feec.vutbr.cz.multimediatesting.View.ConnectionFragment;


public class FragmentAdapter {

    public int getCount() {
        return 1;
    }

    public Fragment getFragment(int position) {
        switch (position) {
            case 1:
                return new ConnectionFragment();
        }
        return null;
    }
}
