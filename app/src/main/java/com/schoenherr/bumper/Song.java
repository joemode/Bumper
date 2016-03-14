package com.schoenherr.bumper;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Joe on 3/11/2016.
 */
public class Song {

    private String mID;
    private String mName;
    private String mArtist;
    private String mAlbumName;
    private String mAlbumID;
    private String mDuration;
    private String mArtPath = null;
    private String mData;


    //TODO: Temporary constructor
    public Song(Cursor cursor) {
        buildSong(cursor);
    }


    private void buildSong(Cursor cursor) {

        mID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        mName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        mAlbumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        mAlbumID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        mDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        mData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

        //mArtPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

    }

    /** Getters**/
    public String getmAlbumName() {
        return mAlbumName;
    }

    public String getmArtist() {
        return mArtist;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public String getmArtPath() {
        return  mArtPath;
    }

    public String getmData() {
        return mData;
    }

    public String getmAlbumID() {
        return mAlbumID;
    }


    /** Setters **/
    public void setmArtPath(String mArtPath) {
        this.mArtPath = mArtPath;
    }
}
