package com.sam_chordas.android.stockhawk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Represents the home screen of the app which
 * //TODO: Add comments for other fragments
 * has a {@link ViewPager} with {@link NewsFragment} and {@link NewsFragment}
 */
public class StockDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = StockDetailActivity.class.getSimpleName();
    private String mSymbol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_detail_activity);

        /**
         * Link layout elements from XML and setup the toolbar
         */
        initializeScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: Create menu xml file for sharing stock data and inflate it
        //getMenuInflater().inflate(R.menu.);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Link layout elements from XML and setup the toolbar
     */
    public void initializeScreen() {

        // Retrieve the stock symbol sent via an intent
        String symbol = getIntent().getStringExtra(Constants.SYMBOL);
        if (symbol != null && symbol.length() != 0) {
            mSymbol = symbol;
        }

        // Initialize view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(3)
         **/
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());

        if (viewPager != null && tabLayout != null) {
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapter);

            /**
             * Setup the mTabLayout with view pager
             */
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:
                    fragment = NewsFragment.newInstance(mSymbol);
                    break;
                case 1:
                    fragment = NewsFragment.newInstance(mSymbol);
                    break;
                case 2:
                    fragment = NewsFragment.newInstance(mSymbol);
                default:
                    fragment = NewsFragment.newInstance(mSymbol);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        /**
         * Set string resources as titles for each fragment by it's position
         *
         * @param position
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pager_title_current);
                case 1:
                    return getString(R.string.pager_title_historical);
                case 2:
                default:
                    return getString(R.string.pager_title_news);
            }
        }
    }
}
