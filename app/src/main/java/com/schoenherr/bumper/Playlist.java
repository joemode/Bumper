package com.schoenherr.bumper;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Joe on 3/14/2016.
 */
public class Playlist {

    private String mName;
    private String mID;

    public Playlist(Cursor cursor) {
        mID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
        mName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
    }

    /** Getters **/
    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }
}
