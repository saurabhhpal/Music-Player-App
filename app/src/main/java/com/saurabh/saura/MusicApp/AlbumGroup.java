package com.saurabh.saura.MusicApp;

import java.util.ArrayList;

public class AlbumGroup {

    public AlbumGroup() {
    }

    private String Name;
    private ArrayList<AlbumChild> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<AlbumChild> getItems() {
        return Items;
    }

    public void setItems(ArrayList<AlbumChild> Items) {
        this.Items = Items;
    }

}
