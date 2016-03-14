package com.schoenherr.bumper;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.List;

/**
 * Created by Joe on 3/13/2016.
 */
public class Album {

    private String mID;
    private String mName;
    private String mArtist;
    private String mArtPath;
    private List<Song> mSongs;

    public Album(Cursor cursor) {

        mID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
        mName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
        mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
        mArtPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

    }

    /** Getters **/
    public String getmArtist() {
        return mArtist;
    }

    public String getmArtPath() {
        return mArtPath;
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public List<Song> getmSongs() { return mSongs; }

    /** Setters **/
    public void setmSongs(List<Song> songs) { mSongs = songs; }
}
