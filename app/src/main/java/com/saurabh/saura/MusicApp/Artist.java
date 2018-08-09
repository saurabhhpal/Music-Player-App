package com.saurabh.saura.MusicApp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.saurabh.saura.MusicApp.DataModel;
import com.saurabh.saura.MusicApp.SongsManager;
import com.saurabh.saura.mymusicapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

//public class ArtistActivity extends AppCompatActivity {

public class Artist extends AppCompatActivity {


    ExpandableListView expandableArtist;
    List<String> showArtistSong;
    private ArrayList<ArtistGroup> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        ActionBar actionBar = getSupportActionBar();
        Toolbar tb = findViewById(R.id.toolbar);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        showArtistSong = new ArrayList<String>();
        expandableArtist = findViewById(R.id.expandableListView);

        artistList = SetStandardGroups(); // set group and child items

        ArtistAdapter adapter = new ArtistAdapter(Artist.this, artistList);
        expandableArtist.setAdapter(adapter);

        expandableArtist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {

                String data = artistList.get(groupPos).getItems().get(childPos).getName();

                Log.e("CHILD SONG------>>>>>",data);

                String path = "";
                for(int i=0;i<(SongsManager.songPath.size());i++){

                    if (data.equalsIgnoreCase(SongsManager.songPath.get(i).getTitle())){

                        path = SongsManager.songPath.get(i).getSongRealPath();
                        Log.e("FILE PATH----********",data+"..."+SongsManager.songPath.get(i).getSongRealPath());
                        break;
                    }
                }

                return false;
            }
        });

    }

    public ArrayList<ArtistGroup> SetStandardGroups() {

        ArrayList<ArtistGroup> list = new ArrayList<ArtistGroup>();

        ArrayList<ArtistChild> ch_list;

        for (String group_name : SongsManager.artistList) {
            ArtistGroup gru = new ArtistGroup();
            gru.setName(group_name);

            ch_list = new ArrayList<ArtistChild>();

            for(int j=0; j<(MainActivity.songsList.size()); j++){
                ArtistChild ch = new ArtistChild();
                if(group_name.equals(MainActivity.songsList.get(j).getArtist())){
                    ch.setName(MainActivity.songsList.get(j).getTitle());
//                    String musicPath = getRealPathFromURI(ArtistActivity.this,MainActivity.songList.get(j).getSongPath(),j);
//                    ch.setFilePath(musicPath);
                    ch_list.add(ch);
                    Log.e("ARTIST DETAIL-->>>>>>",group_name+"....."+MainActivity.songsList.get(j).getTitle());
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
            cursor.moveToPosition(pos);
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
