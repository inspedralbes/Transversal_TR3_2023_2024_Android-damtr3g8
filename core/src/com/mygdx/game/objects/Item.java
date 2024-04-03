package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String name;
    private String description;
    private Texture icon;
    private int quantity;

    public Item(String name, String description, Texture icon, int quantity) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Texture getIcon() {
        return icon;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
