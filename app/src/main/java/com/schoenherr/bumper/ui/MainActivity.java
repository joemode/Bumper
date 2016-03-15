package com.schoenherr.bumper.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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

import com.schoenherr.bumper.Bumper;
import com.schoenherr.bumper.Queue;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
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

    /** Other View Elements */
    private static ViewPagerAdapter mViewPagerAdapter;
    private RelativeLayout mController;

    /** Controller Members **/
    private static ImageView mArtwork;
    private static ImageView mShuffle;
    private static ImageView mSkipPrev;
    private static ImageView mPlay;
    private static ImageView mSkipNext;
    private static ImageView mRepeat;

    private static final String SHUFFLE = "SHUFFLE";
    private static final String SKIP_PREV = "SKIP_PREV";
    private static final String PLAY = "PLAY";
    private static final String SKIP_NEXT = "SKIP_NEXT";
    private static final String REPEAT = "REPEAT";

    /** Media Members */
    private static MediaPlayer mMediaPlayer;
    private static Song mCurrentSong = null;

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

        mController = (RelativeLayout) findViewById(R.id.controller);
        setupController(mController);
        mMediaPlayer =  new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

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

        mArtwork = (ImageView) view.findViewById(R.id.artwork);
        mShuffle = (ImageView) view.findViewById(R.id.shuffle);
        mSkipPrev = (ImageView) view.findViewById(R.id.skip_prev);
        mPlay = (ImageView) view.findViewById(R.id.play);
        mSkipNext = (ImageView) view.findViewById(R.id.skip_next);
        mRepeat = (ImageView) view.findViewById(R.id.repeat);

        updateArtwork(view.getContext());

        mShuffle.setOnClickListener(new ControllerOnClickListener(this, SHUFFLE));
        mSkipPrev.setOnClickListener(new ControllerOnClickListener(this, SKIP_PREV));
        mPlay.setOnClickListener(new ControllerOnClickListener(this, PLAY));
        mSkipNext.setOnClickListener(new ControllerOnClickListener(this, SKIP_NEXT));
        mRepeat.setOnClickListener(new ControllerOnClickListener(this, REPEAT));
    }

    private static void updateArtwork(Context context) {
        if(mCurrentSong != null) {
            if(mCurrentSong.getmArtPath() != null) {
                Picasso.with(context).load(new File(mCurrentSong.getmArtPath())).resize(200, 200).into(mArtwork);
            }
        } else {
            Picasso.with(context).load(R.drawable.ic_music).resize(200, 200).into(mArtwork);
        }
        mArtwork.invalidate();
    }


    public static void initializeMusicPlayerSingle(Song song) {
        //Prepare URI for song
        Uri uri = null;
        if(song != null) {
            String uriString = song.getmData();
            uri = Uri.parse(uriString);
            mCurrentSong = song;
        }

        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            switchPlayPause();
        }

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(Bumper.getAppContext(), uri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

            updateArtwork(Bumper.getAppContext());
            switchPlayPause();
        } catch (IOException ex) {
            Log.i("IOException", ex.getMessage());
        }
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
                    switchPlayPauseControl();
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

    private static void switchPlayPauseControl() {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        } else {
            mMediaPlayer.start();
            mPlay.setImageResource(R.drawable.ic_pause_white_48dp);
        }
        mPlay.invalidate();
    }

    private static void switchPlayPause() {
        if(mMediaPlayer.isPlaying()) {
            mPlay.setImageResource(R.drawable.ic_pause_white_48dp);
        } else {
            mPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        }
        mPlay.invalidate();
    }



}
