package com.schoenherr.bumper.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.schoenherr.bumper.Adapters.SongListAdapter;
import com.schoenherr.bumper.MusicIO;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/10/2016.
 */
public class SongsFragment extends Fragment {

    private List<Song> mSongs = new ArrayList<>();


    public SongsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //populateSongs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_songs, container, false);

        ListView lv = (ListView) root.findViewById(R.id.song_list);
        ProgressBar spinner = (ProgressBar) root.findViewById(R.id.progress_song);

        SongListAdapter adapter = new SongListAdapter(lv, spinner);
        lv.setAdapter(adapter);

        return root;
    }

    /**
    private void populateSongs() {
        mSongs = new MusicIO(this.getContext()).buildSongs();
    }**/



}