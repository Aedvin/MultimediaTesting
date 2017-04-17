package feec.vutbr.cz.multimediatesting.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import feec.vutbr.cz.multimediatesting.View.DelayGraphFragment;
import feec.vutbr.cz.multimediatesting.View.InfoGraphFragment;
import feec.vutbr.cz.multimediatesting.View.JitterGraphFragment;

public class GraphPagerAdapter extends FragmentPagerAdapter {

    private String[] mNames;

    public GraphPagerAdapter(FragmentManager fm, String[] names) {
        super(fm);
        mNames = names;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InfoGraphFragment();
            case 1:
                return new DelayGraphFragment();
            case 2:
                return new JitterGraphFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNames[position];
    }

}
