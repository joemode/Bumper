package com.schoenherr.bumper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joe on 3/13/2016.
 */
public class MusicIO {

    private Context mContext;
    private ContentResolver mContentResolver;

    private static final int ARTISTS_ID = 0;
    private static final int SONGS_ID = 1;

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
        HashMap<String, String> dictionary = buildArtworkDictionary(SONGS_ID);

        String[] projection = new String[] {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};

        Cursor c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, "upper(" + MediaStore.Audio.Media.TITLE + ") ASC");
        if(c != null && c.moveToFirst()) {

            do {
                Song song = new Song(c);
                // Add album artwork path to the song object from dictionary
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

        Cursor c = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, "upper(" + MediaStore.Audio.Media.ALBUM + ") ASC");
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

    /**
     * Build a list of artists from external storage
     * @return a list of artists
     */
    public List<Artist> buildArtists() {
        List<Artist> ret = new ArrayList<>();
        HashMap<String, String> dictionary = buildArtworkDictionary(ARTISTS_ID);

        String[] projection = new String[] {MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS};

        Cursor c = mContentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, projection, null, null, "upper(" + MediaStore.Audio.Artists.ARTIST + ") ASC");
        if(c != null && c.moveToFirst()) {

            do {
                Artist artist = new Artist(c);
                artist.setmArtPath(dictionary.get(c.getString(c.getColumnIndex(MediaStore.Audio.Artists.ARTIST))));

                ret.add(artist);
            } while (c.moveToNext());
            c.close();
        }
        return ret;
    }

    /**
     * Build a hash map so that artwork can be matched to songs or artists
     * @param id the id of which type of music object requires the artwork
     * @return a HashMap of either Album id or Artist name keys and Artwork path values
     */
    private HashMap<String, String> buildArtworkDictionary(int id) {
        HashMap<String, String> hashMap = new HashMap<>();

        String[] projection = new String [] {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST};

        Cursor c = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.ALBUM + " ASC");

        if(c != null && c.moveToFirst()) {

            do {
                if(id == SONGS_ID) {
                    hashMap.put(c.getString(c.getColumnIndex(MediaStore.Audio.Albums._ID)), c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                } else if (id == ARTISTS_ID) {
                    if(!hashMap.containsKey(c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ARTIST)))) {
                        hashMap.put(c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ARTIST)), c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    }
                }
            } while (c.moveToNext());
            c.close();
        }
        
        return hashMap;
    }

    

}
