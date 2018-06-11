package com.java.mju_come;

public class ShopItem {
    private String shop_name;
    private String shop_location;
    private String shop_number;


    public ShopItem(String shop_name,String shop_location, String shop_number ){
        this.shop_name = shop_name;
        this.shop_location = shop_location;
        this.shop_number = shop_number;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_location() {
        return shop_location;
}

    public String getShop_number() {
        return shop_number;
    }
}
