package com.saurabh.saura.MusicApp;

import java.util.ArrayList;

public class ArtistGroup {

    public ArtistGroup() {
    }

    private String Name;
    private ArrayList<ArtistChild> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<ArtistChild> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ArtistChild> Items) {
        this.Items = Items;
    }



}
