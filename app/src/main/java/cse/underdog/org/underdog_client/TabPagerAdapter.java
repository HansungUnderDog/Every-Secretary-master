package cse.underdog.org.underdog_client;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cse.underdog.org.underdog_client.etc.EtcFragment;
import cse.underdog.org.underdog_client.guide.Guide1Fragment;
import cse.underdog.org.underdog_client.guide.Guide2Fragment;
import cse.underdog.org.underdog_client.guide.Guide3Fragment;
import cse.underdog.org.underdog_client.guide.Guide4Fragment;
import cse.underdog.org.underdog_client.guide.GuideLastFragment;
import cse.underdog.org.underdog_client.schedule.ScheduleFragment;
import cse.underdog.org.underdog_client.timeline.TimelineFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        //Returning the current tabs
        switch (position){
            case 0:
                return new Guide1Fragment();
            case 1:
                return new Guide2Fragment();
            case 2:
                return new Guide3Fragment();
            case 3:
                return new Guide4Fragment();
            case 4:
                return new GuideLastFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }




}
