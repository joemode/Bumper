package com.schoenherr.bumper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joe on 3/13/2016.
 */
public class MusicIO {

    Context mContext;
    ContentResolver mContentResolver;

    public MusicIO(Context context) {
        this.mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    /**
     * Build a list of songs from external storage
     * @param albumID An id to check incase we are looking for songs from only that album
     * @return A list of songs
     */
    public List<Song> buildSongs(String albumID) {
        List<Song> ret = new ArrayList<>();
        HashMap<String, String> dictionary = buildArtworkDictionary();

        String[] projection = new String[] {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};

        Cursor c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.TITLE + " ASC");
        if(c != null && c.moveToFirst()) {

            do {
                Song song = new Song(c);
                //TODO: Add album artwork path to the song object from dictionary
                song.setmArtPath(dictionary.get(c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));

                if(albumID != null) {
                    //Filter songs to get if we are on a specific albums page
                    if(song.getmAlbumID().equals(albumID)) {
                        ret.add(song);
                    }
                } else {
                    //If the filtering album id is null we are looking for all songs
                    ret.add(song);
                }

            } while (c.moveToNext());
            c.close();
        }

        return ret;
    }

    /**
     * Build a list of albums from external storage
     * @param artistName A name to check in case we are looking for albums from only that artist
     * @return A list of albums
     */
    public List<Album> buildAlbums(String artistName) {
        List<Album> ret = new ArrayList<>();

        String[] projection = new String [] {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST};

        Cursor c = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.ALBUM + " ASC");
        if(c != null && c.moveToFirst()) {

            do {
                Album album = new Album(c);

                if(artistName != null) {
                    //Filter songs to get if we are on a specific artists page
                    if(album.getmArtist().equals(artistName)) {
                        ret.add(album);
                    }
                } else {
                    //If the filtering album id is null we are looking for all songs
                    ret.add(album);
                }

            } while (c.moveToNext());
            c.close();
        }

        return ret;
    }

    private HashMap<String, String> buildArtworkDictionary() {
        HashMap<String, String> hashMap = new HashMap<>();

        //TODO: Build an artwork dictionary so that artwork is retrievable by songs
        String[] projection = new String [] {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART};

        Cursor c = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.ALBUM + " ASC");

        if(c != null && c.moveToFirst()) {

            do {
                hashMap.put(c.getString(c.getColumnIndex(MediaStore.Audio.Albums._ID)), c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            } while (c.moveToNext());
            c.close();
        }
        
        return hashMap;
    }

}
