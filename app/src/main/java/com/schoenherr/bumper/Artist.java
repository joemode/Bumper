package com.schoenherr.bumper;

import android.database.Cursor;
import android.provider.MediaStore;

import java.util.List;

/**
 * Created by Joe on 3/14/2016.
 */
public class Artist {

    private String mID;
    private String mName;
    private String mCount;

    private List<Album> mAlbums;
    private String mArtPath;

    public Artist(Cursor cursor) {

        mID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
        mName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
        mCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
    }

    /** Getters **/
    public String getmCount() {
        return mCount;
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public List<Album> getmAlbums() {
        return mAlbums;
    }

    public String getmArtPath() {
        return mArtPath;
    }

    /** Setters **/
    public void setmArtPath(String artPath) {
        mArtPath = artPath;
    }
}
