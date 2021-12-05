package com.nidhin.vendingmachine.exception;

public class ItemNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(String itemId) {
        super("Item not found in inventory" + itemId);
    }
}