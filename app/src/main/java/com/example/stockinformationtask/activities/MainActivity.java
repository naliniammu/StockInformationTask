package com.example.stockinformationtask.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.stockinformationtask.R;
import com.example.stockinformationtask.adapter.TabAdapter;
import com.example.stockinformationtask.adapter.ViewPagerAdapter;
import com.example.stockinformationtask.fragments.Forex_Fragment;
import com.example.stockinformationtask.fragments.Stock_Fragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewPager)
    ViewPager tab_viewPager;
    @BindView(R.id.SliderDots)
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ViewPagerAdapter viewPagerAdapter;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind the view using butterknife
        ButterKnife.bind(this);
        toolbar.setTitle(getApplicationContext().getString(R.string.information));
        //placing toolbar in place of actionbar
        slideShowWithDots();
        initiateFragments();

    }

   //placing dots in viewpager slider layout
    private void slideShowWithDots() {
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*
                * after swipping of images again going back to first image*/
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    PagerAdapter pagerAdapter = viewPager.getAdapter();
                    if (pagerAdapter != null) {
                        int itemCount = pagerAdapter.getCount();
                        int index = viewPager.getCurrentItem();
                        if (index == 0 ) {
                            viewPager.setCurrentItem(itemCount - 1, false);
                        } else if (index == itemCount - 1) {
                            viewPager.setCurrentItem(0, false);
                        }
                    }
                }


            }
        });


      /*  *//*After setting the adapter use the timer *//*
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Constants.images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
*/

    }

    // Intiating the frgaments
    public void initiateFragments() {
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new Stock_Fragment(), getApplicationContext().getString(R.string.stock));
        tabAdapter.addFragment(new Forex_Fragment(), getApplicationContext().getString(R.string.forex));
        tab_viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(tab_viewPager);
    }



}
