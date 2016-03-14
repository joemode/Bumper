package com.schoenherr.bumper.Adapters;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.schoenherr.bumper.MusicIO;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/14/2016.
 */
public class SongRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Song> songs = new ArrayList<>();
    private View view;
    private ProgressBar spinner;

    public SongRecyclerAdapter(View view, ProgressBar spinner) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);

        return new SongViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SongViewHolder vh = (SongViewHolder) holder;

        vh.vPrimary.setText(songs.get(position).getmName());
        vh.vSub.setText(songs.get(position).getmArtist());

        if(songs.get(position).getmArtPath() != null) {
            Picasso.with(view.getContext()).load(new File(songs.get(position).getmArtPath())).resize(200, 200).into(vh.vArtwork);
        } else {
            Picasso.with(view.getContext()).load(R.drawable.ic_music).resize(200, 200).into(vh.vArtwork);
        }

        vh.vPosition = position;
        vh.vSongs = songs;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        protected TextView vPrimary;
        protected TextView vSub;
        protected ImageView vArtwork;
        protected ImageButton vPlus;
        protected int vPosition;
        protected List<Song> vSongs;

        public SongViewHolder(final View view) {
            super(view);

            vPrimary = (TextView) view.findViewById(R.id.primary_text);
            vSub = (TextView) view.findViewById(R.id.sub_text);
            vArtwork = (ImageView) view.findViewById(R.id.artwork);
            vPlus = (ImageButton) view.findViewById(R.id.plus_image);

            vPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "PLUS");
                    Toast.makeText(view.getContext(), "Add " + vSongs.get(vPosition).getmName() + " to playlist", Toast.LENGTH_SHORT).show();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "SELECTED");
                    Toast.makeText(view.getContext(), "Play " + vSongs.get(vPosition).getmName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
