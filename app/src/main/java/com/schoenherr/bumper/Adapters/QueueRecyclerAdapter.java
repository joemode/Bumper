package com.schoenherr.bumper.Adapters;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.schoenherr.bumper.MusicIO;
import com.schoenherr.bumper.Queue;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;
import com.schoenherr.bumper.ui.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Joe on 3/14/2016.
 */
public class QueueRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Song> songs = new ArrayList<>();
    private View view;
    private ProgressBar spinner;

    public QueueRecyclerAdapter(View view, ProgressBar spinner) {
        this.view = view;
        this.spinner = spinner;

        update();
    }

    public void update() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                songs = Queue.getInstance().getSongs();
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

        return new QueueViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QueueViewHolder vh = (QueueViewHolder) holder;

        vh.vPrimary.setText(songs.get(position).getmName());
        vh.vSub.setText(songs.get(position).getmArtist());

        if(songs.get(position).getmArtPath() != null) {
            Picasso.with(view.getContext()).load(new File(songs.get(position).getmArtPath())).resize(200, 200).into(vh.vArtwork);
        } else {
            Picasso.with(view.getContext()).load(R.drawable.ic_music).resize(200, 200).into(vh.vArtwork);
        }

        vh.vPosition = position;
        vh.vSongs = songs;

        vh.vPlus.setImageResource(R.drawable.ic_remove_black_24dp);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class QueueViewHolder extends RecyclerView.ViewHolder {

        protected TextView vPrimary;
        protected TextView vSub;
        protected ImageView vArtwork;
        protected ImageButton vPlus;
        protected int vPosition;
        protected List<Song> vSongs;

        public QueueViewHolder(final View view) {
            super(view);

            vPrimary = (TextView) view.findViewById(R.id.primary_text);
            vSub = (TextView) view.findViewById(R.id.sub_text);
            vArtwork = (ImageView) view.findViewById(R.id.artwork);
            vPlus = (ImageButton) view.findViewById(R.id.plus_image);

            vPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "PLUS");
                    Snackbar.make(view, "Delete this song from the Queue?", Snackbar.LENGTH_SHORT).setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Queue.getInstance().removeSong(vSongs.get(vPosition));
                            MainActivity.getViewPager().getAdapter().notifyDataSetChanged();
                        }
                    }).setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary)).show();
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
