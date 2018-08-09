package com.saurabh.saura.MusicApp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.saurabh.saura.mymusicapp.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayAdapter<DataModel> dataSet;
    Context context;

    public CustomAdapter( Context context, int resource,  ArrayList<DataModel> objects) {
        super(context, R.layout.list_view, objects);
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View result = convertView;

        if (result == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            result = inflater.inflate(R.layout.list_view, parent, false);
        }
        DataModel dataModel  = getItem(position);

            TextView tv = result.findViewById(R.id.txt);
            tv.setText(dataModel.getTitle());

            TextView tv_artists = result.findViewById(R.id.txt_artist_name);
            tv_artists.setText(dataModel.getArtist());

            return result;




        }



    @Override
    public void onClick(View view) {
        int pos = (Integer) view.getTag();
        Object o = getItem(pos);
        DataModel dataModel = (DataModel)o;









    }
}
