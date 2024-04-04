package com.mygdx.game.objects;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    public void addItem(Item newItem) {
        for (Item existingItem : items) {
            if (existingItem.getName().equals(newItem.getName())) {
                existingItem.incrementQuantity(newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }


    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean contains(Item item) {
        for (Item currentItem : items) {
            if (currentItem.equals(item)) {
                return true;
            }
        }
        return false;
    }

   /*public void incrementQuantity(Item item) {
        for (Item currentItem : items) {
            if (currentItem.equals(item)) {
                currentItem.incrementQuantity();
                return;
            }
        }
    }*/

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
}
