package com.schoenherr.bumper;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Joe on 3/13/2016.
 */
public class Album {

    private String mID;
    private String mName;
    private String mArtist;
    //private String mCount;
    private String mArtPath;

    public Album(Cursor cursor) {

        mID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
        mName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
        mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
        //mCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._COUNT));
        mArtPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

    }

    /** Getters **/
    public String getmArtist() {
        return mArtist;
    }

    public String getmArtPath() {
        return mArtPath;
    }

//    public String getmCount() {
//        return mCount;
//    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }
}
