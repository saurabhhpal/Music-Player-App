package com.saurabh.saura.MusicApp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.saurabh.saura.mymusicapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayListActivity extends ListActivity {

    public ArrayList<DataModel> songsList = new ArrayList<DataModel>();
    Context     context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = PlayListActivity.this;
        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
//        Context context = PlayListActivity.this;

        SongsManager plm = new SongsManager(context);
        // get all songs from sdcard
        this.songsList = plm.getPlayList();

        // looping through playlist

       /* for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            DataModel song = songsList.get(i);

            // adding HashList to ArrayList
            songsListData.add(song);
        }*/
        //        CustomAdapter adapter = new CustomAdapter(MainActivity.this , R.layout.list_view
//                ,songList);
//        listView.setAdapter(adapter);

        // Adding menuItems to ListView
        /*SimpleAdapter adapter = new SimpleAdapter(this, songsListData,
                R.layout.list_view, new String[] { "songTitle" }, new int[] {
                R.id.txt });
*/

//        CustomAdapter customAdapter = new CustomAdapter(context , R.layout.list_view , songsList);
//        ListView  lv  = getListView();
//        lv.setAdapter(customAdapter);

       /* lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                int songIndex = position;

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                       MediaPlayerActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Closing PlayListView
                finish();
            }
        });*/

    }
}
