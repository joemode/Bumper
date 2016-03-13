package com.schoenherr.bumper.Adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.schoenherr.bumper.MusicIO;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/13/2016.
 */
public class SongListAdapter extends BaseAdapter {

    private List<Song> songs = new ArrayList<>();
    private View view;
    private ProgressBar spinner;

    public SongListAdapter(View view, ProgressBar spinner) {
        this.view = view;
        this.spinner = spinner;

        update();
    }

    private void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                songs = new MusicIO(view.getContext()).buildSongs(null);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                        spinner.setVisibility(view.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        }


        TextView primary = (TextView) convertView.findViewById(R.id.primary_text);
        TextView sub = (TextView) convertView.findViewById(R.id.sub_text);
        ImageView artwork = (ImageView) convertView.findViewById(R.id.artwork);
        ImageButton plus = (ImageButton) convertView.findViewById(R.id.plus_image);

        primary.setText(songs.get(position).getmName());
        sub.setText(songs.get(position).getmArtist());

        if(songs.get(position).getmArtPath() != null) {
            Picasso.with(parent.getContext()).load(new File(songs.get(position).getmArtPath())).resize(200, 200).into(artwork);
        } else {
            Picasso.with(parent.getContext()).load(R.drawable.ic_music).resize(200, 200).into(artwork);
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Popup menu to select queue or playlist to add song to
                Toast.makeText(parent.getContext(), "Add this song to queue", Toast.LENGTH_SHORT).show();
            }
        });

        final String uriString = songs.get(position).getmData();
        final Uri uri = Uri.parse(uriString);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), uriString, Toast.LENGTH_SHORT).show();

                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(parent.getContext(), uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException ex) {
                    Log.i("IOException", ex.getMessage());
                }


            }
        });

        return convertView;
    }

    /**
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
     tv.setText(current.getmName());

     return convertView;
     }
     } **/
}
