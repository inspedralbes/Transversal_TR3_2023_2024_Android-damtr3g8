package com.mygdx.game.objects;

import com.mygdx.game.helpers.AssetManager;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    /*public void addItem(Item newItem) {
        for (Item existingItem : items) {
            if (existingItem.getName().equals(newItem.getName())) {
                existingItem.incrementQuantity(newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }*/

    public void addItem(Item item) {
        if (item.getName().equals("Pocion de cura lleno")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    if (existingItem.getQuantity() < 32) {
                        int spaceLeft = 32 - existingItem.getQuantity();
                        int quantityToAdd = Math.min(spaceLeft, item.getQuantity());
                        existingItem.incrementQuantity(quantityToAdd);
                        item.decreaseQuantity(quantityToAdd);
                    }
                }
            }
            // Si quedó algo de cantidad después de llenar los stacks existentes, agregamos nuevos stacks
            while (item.getQuantity() > 0) {
                int quantityToAdd = Math.min(32, item.getQuantity());
                Item newItem = new Item("Pocion de cura lleno", "Cura 100 de vida", AssetManager.fullredpotiontexture, quantityToAdd);
                items.add(newItem);
                item.decreaseQuantity(quantityToAdd);
            }
        } else if (item.getName().equals("Pocion de cura medio")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    if (existingItem.getQuantity() < 32) {
                        int spaceLeft = 32 - existingItem.getQuantity();
                        int quantityToAdd = Math.min(spaceLeft, item.getQuantity());
                        existingItem.incrementQuantity(quantityToAdd);
                        item.decreaseQuantity(quantityToAdd);
                    }
                }
            }
            // Si quedó algo de cantidad después de llenar los stacks existentes, agregamos nuevos stacks
            while (item.getQuantity() > 0) {
                int quantityToAdd = Math.min(32, item.getQuantity());
                Item newItem = new Item("Pocion de cura medio", "Cura 50 de vida", AssetManager.halfredpotiontexture, quantityToAdd);
                items.add(newItem);
                item.decreaseQuantity(quantityToAdd);
            }
        } else if (item.getName().equals("Pocion de cura cuarto")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    if (existingItem.getQuantity() < 32) {
                        int spaceLeft = 32 - existingItem.getQuantity();
                        int quantityToAdd = Math.min(spaceLeft, item.getQuantity());
                        existingItem.incrementQuantity(quantityToAdd);
                        item.decreaseQuantity(quantityToAdd);
                    }
                }
            }
            // Si quedó algo de cantidad después de llenar los stacks existentes, agregamos nuevos stacks
            while (item.getQuantity() > 0) {
                int quantityToAdd = Math.min(32, item.getQuantity());
                Item newItem = new Item("Pocion de cura cuarto", "Cura 25 de vida", AssetManager.quarterredpotiontexture, quantityToAdd);
                items.add(newItem);
                item.decreaseQuantity(quantityToAdd);
            }
        } else if (item.getName().equals("Pocion de fuerza")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    if (existingItem.getQuantity() < 32) {
                        int spaceLeft = 32 - existingItem.getQuantity();
                        int quantityToAdd = Math.min(spaceLeft, item.getQuantity());
                        existingItem.incrementQuantity(quantityToAdd);
                        item.decreaseQuantity(quantityToAdd);
                    }
                }
            }
            // Si quedó algo de cantidad después de llenar los stacks existentes, agregamos nuevos stacks
            while (item.getQuantity() > 0) {
                int quantityToAdd = Math.min(32, item.getQuantity());
                Item newItem = new Item("Pocion de fuerza", "Augmenta un 80% la fuerza durante 5 segundos", AssetManager.purplepotiontexture, quantityToAdd);
                items.add(newItem);
                item.decreaseQuantity(quantityToAdd);
            }
        } else if (item.getName().equals("Pocion de resistencia")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    if (existingItem.getQuantity() < 32) {
                        int spaceLeft = 32 - existingItem.getQuantity();
                        int quantityToAdd = Math.min(spaceLeft, item.getQuantity());
                        existingItem.incrementQuantity(quantityToAdd);
                        item.decreaseQuantity(quantityToAdd);
                    }
                }
            }
            // Si quedó algo de cantidad después de llenar los stacks existentes, agregamos nuevos stacks
            while (item.getQuantity() > 0) {
                int quantityToAdd = Math.min(32, item.getQuantity());
                Item newItem = new Item("Pocion de resistencia", "Augmenta un 65% la resistencia durante 10 segundos", AssetManager.greenpotiontexture, quantityToAdd);
                items.add(newItem);
                item.decreaseQuantity(quantityToAdd);
            }
        } else if (item.getName().equals("Pocion de velocidad")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    if (existingItem.getQuantity() < 32) {
                        int spaceLeft = 32 - existingItem.getQuantity();
                        int quantityToAdd = Math.min(spaceLeft, item.getQuantity());
                        existingItem.incrementQuantity(quantityToAdd);
                        item.decreaseQuantity(quantityToAdd);
                    }
                }
            }
            // Si quedó algo de cantidad después de llenar los stacks existentes, agregamos nuevos stacks
            while (item.getQuantity() > 0) {
                int quantityToAdd = Math.min(32, item.getQuantity());
                Item newItem = new Item("Pocion de velocidad", "Augmenta un 50% la velocidad durante 13 segundos", AssetManager.yellowpotiontexture, quantityToAdd);
                items.add(newItem);
                item.decreaseQuantity(quantityToAdd);
            }
        } else if (item.getName().equals("Moneda")) {
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    existingItem.incrementQuantity(item.getQuantity());
                    return;
                }
            }
            items.add(item);
        }
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
