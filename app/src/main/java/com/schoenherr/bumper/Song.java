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
    private String mDuration;
    private String mArtPath;
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
        mDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        mData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

        //mArtPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

    }

    /** Getters & Setters **/
    public String getmAlbumName() {
        return mAlbumName;
    }

    public void setmAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmDuration() {
        return mDuration;
    }

    public void setmDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmArtPath() {
        return  mArtPath;
    }

    public void setmArtPath(String mArtPath) {
        this.mArtPath = mArtPath;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
