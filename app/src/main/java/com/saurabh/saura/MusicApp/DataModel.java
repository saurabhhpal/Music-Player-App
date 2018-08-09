package com.saurabh.saura.MusicApp;

import android.media.Image;
import android.net.Uri;

public class DataModel {

    private long id;
    private String title;
    private String artist;
    private Uri  uri;
    private Uri path;
    private Uri songPath;
    private String albumName;
    private String albumSong;
    private String albumPath;
    private String songRealPath;


    public DataModel(){}

    public DataModel(String artist){
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumSong() {
        return albumSong;
    }

    public void setAlbumSong(String albumSong) {
        this.albumSong = albumSong;
    }

    public String getAlbumPath() {
        return albumPath;
    }

    public void setAlbumPath(String albumPath) {
        this.albumPath = albumPath;
    }

    public DataModel(String albumName, String albumSong, Uri albumPath) {
        this.albumName = albumName;
        this.albumSong = albumSong;
        songPath = albumPath;
    }

    public DataModel(long songID, String songTitle, String songArtist , Uri path, Uri uri) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        this.uri = uri;
        this.path= path;
    }


    public DataModel(String title, String songRealPath) {
        this.title = title;
        this.songRealPath = songRealPath;

    }

    // song list
    public DataModel
    (long songID, String songTitle, String songArtist, Uri path) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        songPath = path;
    }


    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getID(){return id;}

    public String getTitle(){return title;}

    public String getArtist(){return artist;}

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
