package com.schoenherr.bumper.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/10/2016.
 */
public class SongsFragment  extends Fragment{

    private List<Song> mSongs = new ArrayList<>();


    public SongsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateSongs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_songs, container, false);

        ListView lv = (ListView) root.findViewById(R.id.song_list);
        SongListAdapter adapter = new SongListAdapter(this.getContext());
        lv.setAdapter(adapter);

        return root;
    }

    private void populateSongs() {

        //TODO: Loop through and retrieve all songs
        for(int i = 0; i<12; i++) {
            Song song = new Song("Song " + Integer.toString(i));
            mSongs.add(song);
        }

    }


    private class SongListAdapter extends BaseAdapter {

        private Context context;

        public SongListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mSongs.size();
        }

        @Override
        public Object getItem(int position) {
            return mSongs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false);
            }

            Song current = (Song) getItem(position);

            TextView tv = (TextView) convertView.findViewById(R.id.song_name);
            tv.setText(current.getName());

            return convertView;
        }
    }
}