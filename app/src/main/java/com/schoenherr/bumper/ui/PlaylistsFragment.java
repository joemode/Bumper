package com.schoenherr.bumper.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.schoenherr.bumper.Adapters.ArtistRecyclerAdapter;
import com.schoenherr.bumper.Adapters.PlaylistRecyclerAdapter;
import com.schoenherr.bumper.Playlist;
import com.schoenherr.bumper.R;

/**
 * Created by Joe on 3/14/2016.
 */
public class PlaylistsFragment extends Fragment {

    public PlaylistsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_music_list, container, false);

        ProgressBar spinner = (ProgressBar) root.findViewById(R.id.progress_song);
        RecyclerView rv = (RecyclerView) root.findViewById(R.id.song_list);

        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        PlaylistRecyclerAdapter rAdapter = new PlaylistRecyclerAdapter(rv, spinner);
        rv.setAdapter(rAdapter);

        return root;
    }
}
