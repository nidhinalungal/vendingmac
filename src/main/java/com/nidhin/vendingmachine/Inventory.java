package com.nidhin.vendingmachine;

import com.nidhin.vendingmachine.exception.ItemNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    Map<String, Item> items = new HashMap<>();

    public void addNewItem(Item item) throws Exception {
        if (items.containsKey(item.getId())) {
            throw new Exception("Item with same id already exists");
        }
        items.put(item.getId(), item);
    }

    public void addQuantity(Item item, int quantity) throws Exception {
        addQuantity(item.getId(), quantity);
    }

    public void addQuantity(String id, int quantity) throws Exception {
        if (!items.containsKey(id)) {
            throw new ItemNotFoundException(id);
        }
        items.get(id).addQuantity(quantity);
    }

    public boolean hasItem(String id) {
        return items.containsKey(id);
    }

    public void removeQuantity(Item item, int quantity) throws Exception {
        removeQuantity(item.getId(), quantity);
    }

    public void removeQuantity(String id, int quantity) throws Exception {
        if (!items.containsKey(id)) {
            throw new ItemNotFoundException(id);
        }
        items.get(id).removeQuantity(quantity);
    }

    public void removeItem(String id) throws Exception {
        if (!items.containsKey(id)) {
            throw new ItemNotFoundException(id);
        }
        items.remove(id);
    }


    public void clear() {
        items.clear();
    }

    public int getQuantity(String id) throws Exception {
        if (!items.containsKey(id)) {
            throw new ItemNotFoundException(id);
        }
        return items.get(id).getQuantity();
    }

    public Item getItem(String id) throws Exception {
        if (!items.containsKey(id)) {
            throw new ItemNotFoundException(id);
        }
        return items.get(id);
    }

    public int totalQuantity() {
        int ret = 0;
        for (Map.Entry<String, Item> e : items.entrySet()) {
            ret += e.getValue().getQuantity();
        }
        return ret;
    }
}
