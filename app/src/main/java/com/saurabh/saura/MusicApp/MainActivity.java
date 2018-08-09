package com.saurabh.saura.MusicApp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.saurabh.saura.mymusicapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button albums, artist_bt;
    private DrawerLayout mDrawerLayout;
    public static ArrayList<DataModel> songsList = new ArrayList<DataModel>();
//    public static ArrayList<String> artistList = new ArrayList<>();
//    public static  ArrayList<DataModel> songPath = new ArrayList<>();
    private MediaPlayer mp;
    Context context;
     String str = null;
     int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        artist_bt = findViewById(R.id.artists);
        ListView lv = findViewById(R.id.song_list);


        SongsManager plm = new SongsManager(context);
        // get all songs from sdcard
        this.songsList = plm.getPlayList();


        CustomAdapter customAdapter = new CustomAdapter(context , R.layout.list_view , songsList);
        lv.setAdapter(customAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                int songIndex = position;

                String path = getRealPathFromUri(MainActivity.this,songsList.get(position).getPath(),songIndex);

                Log.e("path----",songsList.get(position).getUri()+"");
                // Starting
                 Intent intent = new Intent(MainActivity.this, MediaPlayerActivity.class);

                // Sending songIndex to PlayerActivity
                intent.putExtra("songIndex", songIndex);
                intent.putExtra("title" , songsList.get(position).getTitle());
                intent.putExtra("artist" , songsList.get(position).getArtist());
                intent.putExtra("id" , position);
                intent.putExtra("uri" , songsList.get(position).getUri().toString());
                intent.putExtra("path" , path);
                startActivity(intent);
                mp.reset();
                // Closing PlayListView
                finish();

            }
        });



        artist_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artistIntent = new Intent(MainActivity.this , Artist.class);
                startActivity(artistIntent);
            }
        });

    }

    public static String getRealPathFromUri(Context context, Uri contentUri, int pos) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Audio.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
            cursor.moveToPosition(pos);
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
