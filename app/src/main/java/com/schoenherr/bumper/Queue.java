package com.schoenherr.bumper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 3/14/2016.
 */
public class Queue {
    private static Queue mInstance = null;

    private String mString;

    private List<Song> mSongs = new ArrayList<>();

    private Queue() {
        mString = "Hello";
    }

    public static Queue getInstance() {
        if(mInstance == null) {
            mInstance = new Queue();
        }
        return mInstance;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

    public void addSong(Song song) {
        mSongs.add(song);
    }

    public void removeSong(Song song) {
        mSongs.remove(song);
    }

}
