package cse.underdog.org.underdog_client;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Stack;

public class TabActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Stack<Integer> pageStack;

    int prePageIndex;

    boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        pageStack = new Stack<Integer>();
        //Initializing the TabLayout;
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Timeline"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));
        tabLayout.addTab(tabLayout.newTab().setText("Memo"));
        tabLayout.addTab(tabLayout.newTab().setText("Etc"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //viewPager.setOffscreenPageLimit(2);

        //Creating adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!isBack){
                    pageStack.push(Integer.valueOf(prePageIndex));
                }
                prePageIndex = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }



        });


    }
    @Override
    public void onBackPressed() {

        if (pageStack.empty()) {

            super.onBackPressed();

        } else {

            isBack = true;

            int index = pageStack.pop().intValue();

            viewPager.setCurrentItem(index);

            isBack = false;

        }

    }
}
