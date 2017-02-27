package com.death.samplephp;

/**
 * Created by rajora_sd on 2/27/2017.
 */

public class MobileModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getOffers() {
        return Offers;
    }

    public void setOffers(String offers) {
        Offers = offers;
    }

    public String getOprice() {
        return Oprice;
    }

    public void setOprice(String oprice) {
        Oprice = oprice;
    }

    String id, Brand, Description, Image, Model, Offers, Oprice;

}
