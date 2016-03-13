package com.schoenherr.bumper.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.schoenherr.bumper.Adapters.SongListAdapter;
import com.schoenherr.bumper.R;

/**
 * Created by Joe on 3/13/2016.
 */
public class ArtistsFragment extends Fragment {

    public ArtistsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


}
