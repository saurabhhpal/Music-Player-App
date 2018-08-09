package com.saurabh.saura.MusicApp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.saurabh.saura.mymusicapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    ArrayList<DataModel> albumList;
    ArrayList<DataModel> albumSongList;
    ExpandableListView expandableAlbum;
    List<String> showSong ;
    String[] str = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
    private ArrayList<AlbumGroup> albumGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        albumList = new ArrayList<>();
        albumSongList = new ArrayList<>();

        expandableAlbum = findViewById(R.id.expandableAlbum);

        Uri albumUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] columns = { MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM };

        Cursor cursor = managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null, null, null);

        if (cursor.moveToFirst()) {
            do {

                String album =  cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                albumList.add(new DataModel(album));

            } while (cursor.moveToNext());
        }


//        Collections.sort(albumList, new Comparator<Song>(){
//            public int compare(Song a, Song b){ return a.getAlbumName().compareTo(b.getAlbumName());
//            }
//        });

        Log.e("ALBUM----->>>>>>>",albumList.size()+"");

        for(DataModel album_name: albumList){

            String[] column = { MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.MIME_TYPE, };

            String where = MediaStore.Audio.Media.ALBUM + "=?";

            String whereVal[] = { album_name.getAlbumName().toString() };

            String orderBy = MediaStore.Audio.Media.TITLE;

            cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    column, where,whereVal , orderBy);

            if (cursor.moveToFirst()) {
                do {

                    String albumSong =  cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    Log.e("ALBUM NAME----->>>>>",album_name.getAlbumName()+"...."+albumSong);
                    albumSongList.add(new DataModel(album_name.getAlbumName(), albumSong, albumUri));
                } while (cursor.moveToNext());
            }

        }

        albumGroup = SetStandardGroups(); // set group and child items

//        prepareListData();

        AlbumAdapter adapter = new AlbumAdapter(AlbumActivity.this, albumGroup);
        expandableAlbum.setAdapter(adapter);

       /* expandableAlbum.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {



                String name = albumSongList.get(groupPosition).getAlbumName();
//                String path = getRealPathFromURI(AlbumActivity.this,albumSongList.get(groupPosition).getSongPath(),childPosition);
                String path = listDataChild.get(groupPosition).get(childPosition);
                Log.e("ALBUM PATH>>>>>",name+"..."+path);
                return false;
            }
        });*/

    }

    public ArrayList<AlbumGroup> SetStandardGroups() {

        ArrayList<AlbumGroup> list = new ArrayList<AlbumGroup>();

        ArrayList<AlbumChild> ch_list;

        for (String group_name : str) {
            AlbumGroup gru = new AlbumGroup();
            gru.setName(group_name);

            ch_list = new ArrayList<AlbumChild>();

            for(int j=0; j<(albumSongList.size()); j++){
                AlbumChild ch = new AlbumChild();

                if( ((albumSongList.get(j).getAlbumName().toString().charAt(0)) >= 'a' && (albumSongList.get(j).getAlbumName().toString().charAt(0)) <= 'z') || ((albumSongList.get(j).getAlbumName().toString().charAt(0)) >= 'A' && (albumSongList.get(j).getAlbumName().toString().charAt(0)) <= 'Z')) {
                    if ((String.valueOf(albumSongList.get(j).getAlbumName().toString().charAt(0))).equals(group_name)) {
                        String data = albumSongList.get(j).getAlbumName().toString()+"\n"+albumSongList.get(j).getAlbumSong().toString();
//                        showSong.add(String.valueOf(data));
                        ch.setName(albumSongList.get(j).getAlbumName().toString());
                        ch.setAlbumSong(albumSongList.get(j).getAlbumSong().toString());
                    }
                }
            }
            gru.setItems(ch_list);
            list.add(gru);
        }

        return list;
    }


    public String getRealPathFromURI(Context context, Uri contentUri, int pos) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Audio.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//            cursor.moveToFirst();
            cursor.moveToPosition(pos);
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
