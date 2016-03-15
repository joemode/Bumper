package com.schoenherr.bumper.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.schoenherr.bumper.Queue;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/9/2016.
 */
public class MainActivity extends AppCompatActivity {
    /** Drawer members */
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    /** Toolbar & Tab members */
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private static ViewPager mViewPager;

    private static ViewPagerAdapter mViewPagerAdapter;

    private static final String SHUFFLE = "SHUFFLE";
    private static final String SKIP_PREV = "SKIP_PREV";
    private static final String PLAY = "PLAY";
    private static final String SKIP_NEXT = "SKIP_NEXT";
    private static final String REPEAT = "REPEAT";


    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Set up the toolbar & tabs **/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        tab.select();

        /** Set up the navigation drawer **/
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            //Called when Drawer is closed
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            //Called when Drawer is opened
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        /** The navigation drawer? **/
        NavigationView nView = (NavigationView) findViewById(R.id.navigation_view);
        nView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);


                /**
                 * // allow some time after closing the drawer before performing real navigation
                 // so the user can see what is happening
                 drawerLayout.closeDrawer(GravityCompat.START);
                 mDrawerActionHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                navigate(menuItem.getItemId());
                }
                }, DRAWER_CLOSE_DELAY_MS);
                 drawerLayout.closeDrawers();
                 */
                return true;
            }
        });

        RelativeLayout controller = (RelativeLayout) findViewById(R.id.controller);
        setupController(controller);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new QueueFragment(), "Queue");
        mViewPagerAdapter.addFragment(new SongsFragment(), "Songs");
        mViewPagerAdapter.addFragment(new ArtistsFragment(), "Artists");
        mViewPagerAdapter.addFragment(new AlbumsFragment(), "Albums");
        mViewPagerAdapter.addFragment(new PlaylistsFragment(), "Playlists");
        viewPager.setAdapter(mViewPagerAdapter);
    }

    private void setupController(View view) {
        Song currentSong = null;

        ImageView artwork = (ImageView) view.findViewById(R.id.artwork);
        ImageView shuffle = (ImageView) view.findViewById(R.id.shuffle);
        ImageView skipPrev = (ImageView) view.findViewById(R.id.skip_prev);
        ImageView play = (ImageView) view.findViewById(R.id.play);
        ImageView skipNext = (ImageView) view.findViewById(R.id.skip_next);
        ImageView repeat = (ImageView) view.findViewById(R.id.repeat);

        if(Queue.getInstance().getSongs().size() > 0) {
            currentSong = Queue.getInstance().getSongs().get(0);
        }

        if(currentSong != null) {
            if(currentSong.getmArtPath() != null) {
                Picasso.with(view.getContext()).load(new File(currentSong.getmArtPath())).resize(200, 200).into(artwork);
            }
        } else {
            Picasso.with(view.getContext()).load(R.drawable.ic_music).resize(200, 200).into(artwork);
        }

        shuffle.setOnClickListener(new ControllerOnClickListener(this, SHUFFLE));
        skipPrev.setOnClickListener(new ControllerOnClickListener(this, SKIP_PREV));
        play.setOnClickListener(new ControllerOnClickListener(this, PLAY));
        skipNext.setOnClickListener(new ControllerOnClickListener(this, SKIP_NEXT));
        repeat.setOnClickListener(new ControllerOnClickListener(this, REPEAT));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            if(object instanceof QueueFragment) {
                QueueFragment fragment = (QueueFragment) object;
                fragment.update();
            }

            return super.getItemPosition(object);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


    }

    public static ViewPager getViewPager() {
        return mViewPager;
    }

    private class ControllerOnClickListener implements View.OnClickListener {

        private String message;
        private Context context;
        public ControllerOnClickListener(Context context, String message) {
            this.message = message;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            ImageView iv = (ImageView) v;

            switch (message) {
                case SHUFFLE:
                    Toast.makeText(context, SHUFFLE, Toast.LENGTH_SHORT).show();
                    break;
                case SKIP_PREV:
                    Toast.makeText(context, SKIP_PREV, Toast.LENGTH_SHORT).show();
                    break;
                case PLAY:
                    Toast.makeText(context, PLAY, Toast.LENGTH_SHORT).show();
                    break;
                case SKIP_NEXT:
                    Toast.makeText(context, SKIP_NEXT, Toast.LENGTH_SHORT).show();
                    break;
                case REPEAT:
                    Toast.makeText(context, REPEAT, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }



}
