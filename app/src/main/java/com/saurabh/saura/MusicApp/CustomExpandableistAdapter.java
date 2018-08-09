package com.saurabh.saura.MusicApp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.saurabh.saura.mymusicapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class CustomExpandableistAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> listHeader;
    private HashMap<String , ArrayList<String> > groupItem;
    public Activity activity;
    public LayoutInflater inflater;
    private  Context context;

    public CustomExpandableistAdapter(Context context , ArrayList<String> listHeader , HashMap<String , ArrayList<String> > groupItem) {
        this.groupItem = groupItem;
        this.context = context;
        this.listHeader= listHeader;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String listTitle = (String) getGroup(i);
        if(view == null ){

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTextView = view.findViewById(R.id.listTitle);
        listTextView.setText(listTitle);
        listTextView.setTypeface(null , Typeface.BOLD);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final String childText = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) view
                .findViewById(R.id.expandedListItem);

        txtListChild.setText(childText);
        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
