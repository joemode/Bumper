package com.schoenherr.bumper.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.schoenherr.bumper.Album;
import com.schoenherr.bumper.MusicIO;
import com.schoenherr.bumper.R;
import com.schoenherr.bumper.Song;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/13/2016.
 */
public class AlbumListAdapter extends BaseAdapter {

    private List<Album> albums = new ArrayList<>();
    private View view;
    private ProgressBar spinner;

    public AlbumListAdapter(View view, ProgressBar spinner) {
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
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        }

        TextView primary = (TextView) convertView.findViewById(R.id.primary_text);
        ImageView artwork = (ImageView) convertView.findViewById(R.id.artwork);
        ImageButton plus = (ImageButton) convertView.findViewById(R.id.plus_image);

        primary.setText(albums.get(position).getmName());

        if(albums.get(position).getmArtPath() != null) {
            Picasso.with(parent.getContext()).load(new File(albums.get(position).getmArtPath())).resize(200, 200).into(artwork);
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Popup menu to select queue or playlist to add song to
                Toast.makeText(parent.getContext(), "Add this album to queue", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
