package com.schoenherr.bumper.Adapters;

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
import com.schoenherr.bumper.Playlist;
import com.schoenherr.bumper.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/14/2016.
 */
public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Playlist> playlists = new ArrayList<>();
    private View view;
    private ProgressBar spinner;

    public PlaylistRecyclerAdapter(View view, ProgressBar spinner) {
        this.view = view;
        this.spinner = spinner;

        update();
    }

    private void update() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                playlists = new MusicIO(view.getContext()).buildPlaylists();
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
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);

        return new PlaylistViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlaylistViewHolder vh = (PlaylistViewHolder) holder;

        vh.vPrimary.setText(playlists.get(position).getmName());
        vh.vOverflow.setImageResource(R.drawable.ic_more_vert_black_24dp);

        Picasso.with(view.getContext()).load(R.drawable.ic_music).resize(200, 200).into(vh.vArtwork);

        vh.vPosition = position;
        vh.vPlaylists = playlists;
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {

        protected TextView vPrimary;
        protected TextView vSub;
        protected ImageView vArtwork;
        protected ImageButton vOverflow;
        protected int vPosition;
        protected List<Playlist> vPlaylists;

        public PlaylistViewHolder(final View view) {
            super(view);

            vPrimary = (TextView) view.findViewById(R.id.primary_text);
            vSub = (TextView) view.findViewById(R.id.sub_text);
            vArtwork = (ImageView) view.findViewById(R.id.artwork);
            vOverflow = (ImageButton) view.findViewById(R.id.plus_image);

            vOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "PLUS");

                    PopupMenu popupMenu = new PopupMenu(view.getContext(), vOverflow);
                    popupMenu.getMenuInflater().inflate(R.menu.playlist_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            Toast.makeText(view.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            //TODO: Actually add to the queue/playlist
                            return true;
                        }
                    });

                    popupMenu.show();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "SELECTED");
                    Toast.makeText(view.getContext(), "View " + vPlaylists.get(vPosition).getmName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
