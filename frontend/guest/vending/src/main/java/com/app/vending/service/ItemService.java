package com.app.vending.service;


import com.app.vending.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        items.add(new Item("Coca",     15_000, "/images/coca.png"));
        items.add(new Item("Pepsi",    12_000, "/images/pepsi.png"));
        items.add(new Item("7UP",      10_000, "/images/7up.png"));
        items.add(new Item("Sting",    13_000, "/images/sting.png"));
        items.add(new Item("Aquafina",  8_000, "/images/aquafina.png"));
        items.add(new Item("Fanta",    14_000, "/images/fanta.png"));

        return items;
    }
}
