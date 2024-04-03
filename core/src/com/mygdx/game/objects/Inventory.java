package com.mygdx.game.objects;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
    public List<Item> getItems() {
        return items;
    }

    // Additional methods like getItemCount(), getItemAtIndex(), etc.
}
