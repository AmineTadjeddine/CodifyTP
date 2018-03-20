package dz.univ_saida.android.codifytp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Marwane Zizou on 3/13/2018.
 */

public class viewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    viewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return new CesarFragment();
            case 1:
                return new VegenereFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Cesar";
            case 1:
                return "Vegènere";
            default:
                return null;
        }
    }



}
