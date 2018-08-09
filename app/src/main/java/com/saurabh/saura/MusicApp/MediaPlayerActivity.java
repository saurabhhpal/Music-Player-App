package com.saurabh.saura.MusicApp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saurabh.saura.mymusicapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MediaPlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {


    TextView song_Title_tv , tv_Artists;
    private DrawerLayout mDrawerLayout;
//    private MediaPlayer mp ;
    ImageView bt_prev, bt_play_pause , bt_next ,  songImage_IV;
    SongsManager songsManager =new SongsManager(MediaPlayerActivity.this);
    ArrayList<DataModel> songList  = new ArrayList<>();
    public static  Integer id;
    String path="", title="",artist="" ;
    MediaPlayer mediaPlayer = new MediaPlayer();;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MediaPlayerActivity.this , MainActivity.class);
        startActivity(intent);

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);


        song_Title_tv = findViewById(R.id.songTitle);
        songImage_IV= findViewById(R.id.song_image);
        tv_Artists = findViewById(R.id.songArtists);
        bt_prev = findViewById(R.id.bt_prev);
        bt_play_pause = findViewById(R.id.bt_play_pause);
        bt_next = findViewById(R.id.bt_next);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        songList = songsManager.getPlayList();


        Bundle bundle =  getIntent().getExtras();
        id=  bundle.getInt("id");
        artist = bundle.getString("artist");
        title = bundle.getString("title");
        String uri = bundle.getString("uri");
         path = bundle.getString("path");

        Uri thumbnail = Uri.parse(uri);



        //setting title
        song_Title_tv.setText(title);

        //Setting artists

        tv_Artists.setText(artist);

        //setting Image for Song

        settingImageForSong(thumbnail);
        playSong();



         bt_play_pause.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 bt_play_pause.setBackgroundResource(0);
//                 bt_play_pause.setBackground(getResources().getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
                 if (mediaPlayer.isPlaying()) {
                     mediaPlayer.pause();
                     bt_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                 } else {
                     bt_play_pause.setBackgroundResource(0);
                     mediaPlayer.start();
                     bt_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
                 }
             }
         });

         bt_next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 id++;

                 if (id == songList.size() - 1) {
                     id = 0;
                 } else {
                     int currSongPos = id;
//                    currSongPos += 1;
                     path = getRealPathFromUri(MediaPlayerActivity.this, songList.get(currSongPos).getPath(), currSongPos);
                     Toast.makeText(MediaPlayerActivity.this, "BT_NEXT" + id + " PATH" + path, Toast.LENGTH_LONG).show();
                     changeSongInfo(id);
                     playSong();

                 }
             }
         });

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MediaPlayerActivity.this, "BT_PREV", Toast.LENGTH_LONG).show();
                if(id == 0 ){
                    id =songList.size() - 1;
                }else {

                    id--;
                    int currSongPos = id;
//                    currSongPos -= 1;
                    path = getRealPathFromUri(MediaPlayerActivity.this, songList.get(currSongPos).getPath(), currSongPos);
                    bt_play_pause.setBackgroundResource(0);
                    bt_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                    changeSongInfo(id);
                    playSong();
                }
            }
        });


    }

    private void settingImageForSong(Uri thumbnail) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), thumbnail);
//            bitmap = Bitmap.createScaledBitmap(bitmap, 30, 30, true);

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_wallpaper_black_24dp);
        } catch (IOException e) {

            e.printStackTrace();
        }
        Log.e("cover----",thumbnail+"..."+bitmap);

        songImage_IV.setImageBitmap(bitmap);
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



    public void  playSong() {
        // Play song
        try {

            File file = new File(path);

             if(mediaPlayer.isPlaying() ){
                 mediaPlayer.stop();
             }else{
             mediaPlayer.reset(); }
            try {
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                bt_play_pause.setBackgroundResource(0);
                bt_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
                Log.e("music----", file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();

        }
    }


    public void changeSongInfo(int songIndex){

//        Displaying Song title
        String songTitle = songList.get(songIndex).getTitle();
        song_Title_tv.setText(songTitle);

        String songArtista = songList.get(songIndex).getArtist();
        tv_Artists.setText(songArtista);

        Uri songImageUri = songList.get(songIndex).getUri();
        settingImageForSong(songImageUri);

        // Changing Button Image to pause image
//        bt_play_pause.setImageDrawable();
//        bt_play_pause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
    }
}
