package com.saurabh.saura.MusicApp;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FilenameFilter;
import java.util.zip.CheckedOutputStream;

public class SongsManager extends Application{

    final String MEDIA_PATH = new String("/sdcard/");
    public static ArrayList<DataModel> songsList = new ArrayList<DataModel>();
//    public static ArrayList<DataModel> songsList = new ArrayList<DataModel>();
    public static ArrayList<String> artistList = new ArrayList<>();
    public static  ArrayList<DataModel> songPath = new ArrayList<>();
//    ContextWrapper cw = new ContextWrapper( getApplicationC);
    // Constructor
//public static ArrayList<String> artistList = new ArrayList<>();

    Context context ;

    public SongsManager(Context contex) {
        this.context = contex;

    }


    public ArrayList<DataModel> getPlayList() {
//
        ContentResolver contentResolver =  context.getContentResolver();
        File home = new File(Environment.getExternalStorageDirectory() ,"" );

        Uri musicURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = contentResolver.query(musicURI, null, null, null, null);


        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int songImage = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
//            Uri fullPath = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA));



            //add songs to list
            do {

                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
//                String thisAlbumArt  = musicCursor.getString(songImage);
//                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , thisId);

                Long albumId = musicCursor.getLong(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);


                songsList.add(new DataModel(thisId, thisTitle, thisArtist,musicURI, albumArtUri));

                if(!artistList.contains(thisArtist)){
                    artistList.add(thisArtist);
                }

//                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }

        // return songs list array
        return songsList;


    }


    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }


    }
}