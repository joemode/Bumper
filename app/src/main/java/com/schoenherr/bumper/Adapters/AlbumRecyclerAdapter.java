package com.schoenherr.bumper.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.schoenherr.bumper.Album;
import com.schoenherr.bumper.MusicIO;
import com.schoenherr.bumper.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/14/2016.
 */
public class AlbumRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Album> albums = new ArrayList<>();
    private View view;
    private ProgressBar spinner;

    public AlbumRecyclerAdapter(View view, ProgressBar spinner) {
        this.view = view;
        this.spinner = spinner;

        update();
    }

    private void update() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                albums = new MusicIO(view.getContext()).buildAlbums(null);
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

        return new AlbumViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AlbumViewHolder vh = (AlbumViewHolder) holder;

        vh.vPrimary.setText(albums.get(position).getmName());

        if(albums.get(position).getmArtPath() != null) {
            Picasso.with(view.getContext()).load(new File(albums.get(position).getmArtPath())).resize(200, 200).into(vh.vArtwork);
        } else {
            Picasso.with(view.getContext()).load(R.drawable.ic_music).resize(200, 200).into(vh.vArtwork);
        }

        vh.vPosition = position;
        vh.vAlbums = albums;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        protected TextView vPrimary;
        protected ImageView vArtwork;
        protected ImageButton vPlus;
        protected int vPosition;
        protected List<Album> vAlbums;

        public AlbumViewHolder(final View view) {
            super(view);

            vPrimary = (TextView) view.findViewById(R.id.primary_text);
            vArtwork = (ImageView) view.findViewById(R.id.artwork);
            vPlus = (ImageButton) view.findViewById(R.id.plus_image);

            vPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "PLUS");
                    Toast.makeText(view.getContext(), "Add " + vAlbums.get(vPosition).getmName() + " to playlist", Toast.LENGTH_SHORT).show();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON", "SELECTED");
                    Toast.makeText(view.getContext(), "Open " + vAlbums.get(vPosition).getmName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
