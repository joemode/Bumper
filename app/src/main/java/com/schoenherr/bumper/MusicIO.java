package com.schoenherr.bumper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/13/2016.
 */
public class MusicIO {

    Context mContext;

    public MusicIO(Context context) {
        this.mContext = context;
    }

    public List<Song> buildSongs() {
        List<Song> ret = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();

        String[] projection = new String[] {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};

        Cursor c = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.TITLE + " ASC");
        if(c != null && c.moveToFirst()) {

            do {
                Song song = new Song(c);
                ret.add(song);
            } while (c.moveToNext());
            c.close();
        }

        return ret;
    }

}
